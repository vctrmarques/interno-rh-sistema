package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Dependente;
import com.rhlinkcon.model.DependenteBeneficio;
import com.rhlinkcon.model.DependenteTipoDependenteEnum;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.dependente.DependenteRequest;
import com.rhlinkcon.payload.dependente.DependenteResponse;
import com.rhlinkcon.repository.DependenteBeneficioRepository;
import com.rhlinkcon.repository.DependenteRepository;
import com.rhlinkcon.repository.MunicipioRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class DependenteService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;

	@Autowired
	private MunicipioRepository municipioRepository;

	@Autowired
	private DependenteRepository dependenteRepository;

	@Autowired
	private DependenteBeneficioRepository dependenteBeneficioRepository;

	public DependenteResponse getDependenteById(Long dependenteId) {
		Dependente dependente = dependenteRepository.findById(dependenteId)
				.orElseThrow(() -> new ResourceNotFoundException("Dependente", "id", dependenteId));

		Usuario userCreated = usuarioRepository.findById(dependente.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", dependente.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(dependente.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", dependente.getUpdatedBy()));

		DependenteResponse dependenteResponse = new DependenteResponse(dependente, dependente.getCreatedAt(),
				userCreated.getNome(), dependente.getUpdatedAt(), userUpdated.getNome());

		return dependenteResponse;
	}

	public void createDependente(List<DependenteRequest> dependentesRequest) {

		for (DependenteRequest dependenteRequest : dependentesRequest) {
			Dependente dependente = new Dependente(dependenteRequest);

			setEntidades(dependenteRequest, dependente);

			dependenteRepository.save(dependente);
		}

	}

	public void updateDependente(List<DependenteRequest> dependentesRequest) {

		deleteDependente(dependentesRequest.get(0).getFuncionarioId());

		createDependente(dependentesRequest);

	}

	public void deleteDependente(Long funcionarioId) {
		Optional<List<Dependente>> dependentes = dependenteRepository.findByFuncionarioId(funcionarioId);

		if (dependentes.isPresent()) {
			for (Dependente dependente : dependentes.get())
				dependenteRepository.delete(dependente);
		}
	}

	public List<DependenteResponse> getAllDependentesByFuncionario(Long funcionarioId) {

		List<DependenteResponse> dependentesResponse = new ArrayList<DependenteResponse>();
		Optional<List<Dependente>> dependentes = dependenteRepository.findByFuncionarioId(funcionarioId);

		if (dependentes.isPresent()) {
			for (Dependente dependente : dependentes.get()) {
				List<DependenteBeneficio> dependenteBeneficios = dependenteBeneficioRepository
						.findByDependenteId(dependente.getId());
				DependenteResponse dependenteResponse = new DependenteResponse(dependente);
				dependenteResponse.setQntdBeneficios(dependenteBeneficios.size());
				dependentesResponse.add(dependenteResponse);
			}
		}

		return dependentesResponse;
	}

	private void setEntidades(DependenteRequest dependenteRequest, Dependente dependente) {

		if (Objects.nonNull(dependenteRequest.getFuncionarioId())) {
			Funcionario funcionario = funcionarioRepository.findById(dependenteRequest.getFuncionarioId()).orElseThrow(
					() -> new ResourceNotFoundException("Funcionario", "id", dependenteRequest.getFuncionarioId()));

			dependente.setFuncionario(funcionario);
		}

		if (Objects.nonNull(dependenteRequest.getMunicipioId())) {
			Municipio municipio = municipioRepository.findById(dependenteRequest.getMunicipioId()).orElseThrow(
					() -> new ResourceNotFoundException("Municipio", "id", dependenteRequest.getMunicipioId()));

			dependente.setMunicipio(municipio);
		}

		if (Objects.nonNull(dependenteRequest.getUnidadeFederativaId())) {
			UnidadeFederativa unidadeFederativa = unidadeFederativaRepository
					.findById(dependenteRequest.getUnidadeFederativaId())
					.orElseThrow(() -> new ResourceNotFoundException("Unidade Federativa", "id",
							dependenteRequest.getUnidadeFederativaId()));

			dependente.setUnidadeFederativa(unidadeFederativa);
		}

	}

	public void atualizarDependentes() {
		Optional<List<Funcionario>> funcListOpt = dependenteRepository.findFuncionarios();

		if (funcListOpt.isPresent()) {
			for (Funcionario funcionario : funcListOpt.get()) {

				atualizarDependente(funcionario);
				funcionarioRepository.save(funcionario);
			}
		}
	}

	public void atualizarDependente(Funcionario funcionario) {

		Optional<List<Dependente>> dependentesListOpt = dependenteRepository.findByFuncionarioId(funcionario.getId());

		funcionario.setNumeroDependentesImpostoRenda(0);
		funcionario.setNumeroDependentesSalarioFamilia(0);
		if (dependentesListOpt.isPresent()) {
			for (Dependente dependente : dependentesListOpt.get()) {
				processaDependente(dependente, funcionario);
			}
		}

		funcionarioRepository.save(funcionario);
	}

	private void processaDependente(Dependente dependente, Funcionario funcionario) {

		if (dependente.getDependenciaIr()) {

			Integer dependenteIrQtd = funcionario.getNumeroDependentesImpostoRenda();
			boolean dependenteIrValido = false;

			int idade = Utils.calculateAgeMonth(Date.from(dependente.getDataNascimento()));

			if (idade <= 21 && (dependente.getTipo().equals(DependenteTipoDependenteEnum.FILHO_AS_MENOR)
					|| dependente.getTipo().equals(DependenteTipoDependenteEnum.MENOR_TUTELADO)
					|| dependente.getTipo().equals(DependenteTipoDependenteEnum.ENTEADO_A_MENOR))) {
				dependenteIrValido = true;

			} else {
				if (idade <= 24 && dependente.getTipo().equals(DependenteTipoDependenteEnum.UNIVERSITARIO)) {
					dependenteIrValido = true;

				} else if (dependente.getTipo().equals(DependenteTipoDependenteEnum.FILHO_AS_INVALIDO)
						|| dependente.getTipo().equals(DependenteTipoDependenteEnum.CONJUGE)
						|| dependente.getTipo().equals(DependenteTipoDependenteEnum.COMPANHEIRO_A)
						|| dependente.getTipo().equals(DependenteTipoDependenteEnum.PAI_MAE)) {
					dependenteIrValido = true;

				}

			}

			if (dependenteIrValido)
				funcionario.setNumeroDependentesImpostoRenda(dependenteIrQtd + 1);

		}

		if (dependente.getDependenciaSf()) {

			Integer dependenteSlQtd = funcionario.getNumeroDependentesSalarioFamilia();
			boolean dependenteSlValido = false;

			int idade = Utils.calculateAgeMonth(Date.from(dependente.getDataNascimento()));

			if (idade <= 14 && (dependente.getTipo().equals(DependenteTipoDependenteEnum.FILHO_AS_MENOR)
					|| dependente.getTipo().equals(DependenteTipoDependenteEnum.MENOR_TUTELADO)
					|| dependente.getTipo().equals(DependenteTipoDependenteEnum.ENTEADO_A_MENOR))) {
				dependenteSlValido = true;
			} else if (dependente.getTipo().equals(DependenteTipoDependenteEnum.FILHO_AS_INVALIDO)
					|| dependente.getTipo().equals(DependenteTipoDependenteEnum.MENOR_TUTELADO_INVALIDO)
					|| dependente.getTipo().equals(DependenteTipoDependenteEnum.ENTEADO_A_INVALIDO)) {
				dependenteSlValido = true;
			}

			if (dependenteSlValido)
				funcionario.setNumeroDependentesSalarioFamilia(dependenteSlQtd + 1);

		}

	}

}

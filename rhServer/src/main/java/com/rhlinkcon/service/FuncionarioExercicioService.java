package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.ClassificacaoAto;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.FuncionarioExercicio;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.funcionarioExercicio.FuncionarioExercicioRequest;
import com.rhlinkcon.payload.funcionarioExercicio.FuncionarioExercicioResponse;
import com.rhlinkcon.payload.licencaPremio.LicencaPremioResponse;
import com.rhlinkcon.repository.ClassificacaoAtoRepository;
import com.rhlinkcon.repository.FuncionarioExercicioRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;

@Service
public class FuncionarioExercicioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
		
	@Autowired
	private FuncionarioExercicioRepository funcionarioExercicioRepository;
	
	@Autowired
	private ClassificacaoAtoRepository classificacaoAtoRepository;		
	
	@Autowired
	private LicencaPremioService licencaPremioService;
	

	public void createFuncionarioExercicio(FuncionarioExercicioRequest funcionarioExercicioRequest) {

		FuncionarioExercicio funcionarioExercicio = new FuncionarioExercicio(funcionarioExercicioRequest);
		
		setEntidades(funcionarioExercicioRequest, funcionarioExercicio);

		funcionarioExercicioRepository.save(funcionarioExercicio);

	}
	
	public void updateFuncionarioExercicio(FuncionarioExercicioRequest funcionarioExercicioRequest) {
		FuncionarioExercicio funcionarioExercicio = funcionarioExercicioRepository.findById(funcionarioExercicioRequest.getId())
																				  .orElseThrow(() -> new ResourceNotFoundException("Funcionario Exercicio", "id", funcionarioExercicioRequest.getId()));
		
		setEntidades(funcionarioExercicioRequest, funcionarioExercicio);
		
		funcionarioExercicio.setExercicio(funcionarioExercicioRequest.getExercicio());
		funcionarioExercicio.setDataInicio(funcionarioExercicioRequest.getDataInicio());
		funcionarioExercicio.setDataFim(funcionarioExercicioRequest.getDataFim());
		funcionarioExercicio.setProcesso(funcionarioExercicioRequest.getProcesso());
		funcionarioExercicio.setNumDiarioOficial(funcionarioExercicioRequest.getNumDiarioOficial());
		funcionarioExercicio.setDataDiarioOficial(funcionarioExercicioRequest.getDataDiarioOficial());
		funcionarioExercicio.setNumeroAto(funcionarioExercicioRequest.getNumeroAto());
		funcionarioExercicio.setAnoAto(funcionarioExercicioRequest.getAnoAto());
		funcionarioExercicio.setDataAto(funcionarioExercicioRequest.getDataAto());
		
		funcionarioExercicioRepository.save(funcionarioExercicio);
	
	}

	public List<FuncionarioExercicioResponse> getAllFuncionarioExerciciosByFuncionario(Long funcionarioId) {

		List<FuncionarioExercicioResponse> funcionarioExerciciosResponse = new ArrayList<FuncionarioExercicioResponse>();
		List<FuncionarioExercicio> funcionarioExercicios = funcionarioExercicioRepository.findByFuncionarioId(funcionarioId);
		
		
		if (Objects.nonNull(funcionarioExercicios)) {
			for(FuncionarioExercicio funcionarioExercicio : funcionarioExercicios) {
				List<LicencaPremioResponse> quantLicencaPremio = licencaPremioService.getAllLicencasPremioByFuncionarioExercicio(funcionarioExercicio.getId());
				FuncionarioExercicioResponse funcionarioExercicioResponse = new FuncionarioExercicioResponse(funcionarioExercicio);
				funcionarioExercicioResponse.setQuantLicencaPremio(quantLicencaPremio.size());
				funcionarioExerciciosResponse.add(funcionarioExercicioResponse);				
			}

		}

			return funcionarioExerciciosResponse;
	}
	
	public FuncionarioExercicioResponse getFuncionarioExercicioById(Long funcionarioExercicioId) {
		FuncionarioExercicio funcionarioExercicio = funcionarioExercicioRepository.findById(funcionarioExercicioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario Exercicio", "id", funcionarioExercicioId));
		

		Usuario userCreated = usuarioRepository.findById(funcionarioExercicio.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", funcionarioExercicio.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(funcionarioExercicio.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", funcionarioExercicio.getUpdatedBy()));

		FuncionarioExercicioResponse funcionarioExercicioResponse = new FuncionarioExercicioResponse(funcionarioExercicio, funcionarioExercicio.getCreatedAt(),
				userCreated.getNome(), funcionarioExercicio.getUpdatedAt(), userUpdated.getNome());

		funcionarioExercicioResponse.setClassificacaoAtoId(funcionarioExercicio.getClassificacaoAto().getId());
		funcionarioExercicioResponse.setFuncionarioId(funcionarioExercicio.getFuncionario().getId());

		return funcionarioExercicioResponse;
	}	

	
	private void setEntidades(FuncionarioExercicioRequest funcionarioExercicioRequest, FuncionarioExercicio funcionarioExercicio) {
		
		if(Objects.nonNull(funcionarioExercicioRequest.getFuncionarioId())) {
			Funcionario funcionario = funcionarioRepository.findById(funcionarioExercicioRequest.getFuncionarioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioExercicioRequest.getFuncionarioId()));
			
			funcionarioExercicio.setFuncionario(funcionario);
		}
		
		if(Objects.nonNull(funcionarioExercicioRequest.getClassificacaoAtoId())) {
			ClassificacaoAto classificacaoAto = classificacaoAtoRepository.findById(funcionarioExercicioRequest.getClassificacaoAtoId())
					.orElseThrow(() -> new ResourceNotFoundException("Classificação do Ato", "id", funcionarioExercicioRequest.getClassificacaoAtoId()));
			
			funcionarioExercicio.setClassificacaoAto(classificacaoAto);
		
		}
	}
	
	public void deleteFuncionarioExercicio(Long id) {
		FuncionarioExercicio funcionarioExercicio = funcionarioExercicioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario Exercicio", "id", id));
		funcionarioExercicioRepository.delete(funcionarioExercicio);
	}

}

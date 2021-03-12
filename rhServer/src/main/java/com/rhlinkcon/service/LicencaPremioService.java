package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Dependente;
import com.rhlinkcon.model.DependenteBeneficio;
import com.rhlinkcon.model.FuncionarioExercicio;
import com.rhlinkcon.model.LicencaPremio;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.payload.dependente.DependenteResponse;
import com.rhlinkcon.payload.licencaPremio.LicencaPremioRequest;
import com.rhlinkcon.payload.licencaPremio.LicencaPremioResponse;
import com.rhlinkcon.repository.FuncionarioExercicioRepository;
import com.rhlinkcon.repository.LicencaPremioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;

@Service
public class LicencaPremioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private LicencaPremioRepository licencaPremioRepository;	
	
	@Autowired
	private FuncionarioExercicioRepository funcionarioExercicioRepository;
	
	
//	public DependenteResponse getDependenteById(Long dependenteId) {
//		Dependente dependente = dependenteRepository.findById(dependenteId)
//				.orElseThrow(() -> new ResourceNotFoundException("Dependente", "id", dependenteId));
//		
//		
//
//		Usuario userCreated = usuarioRepository.findById(dependente.getCreatedBy())
//				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", dependente.getCreatedBy()));
//
//		Usuario userUpdated = usuarioRepository.findById(dependente.getUpdatedBy())
//				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", dependente.getUpdatedBy()));
//
//		DependenteResponse dependenteResponse = new DependenteResponse(dependente, dependente.getCreatedAt(), userCreated.getNome(),
//				dependente.getUpdatedAt(), userUpdated.getNome());
//
//		return dependenteResponse;
//	}	
	

	public void createLicencaPremio(List<LicencaPremioRequest> licencasPremioRequest) {

		for(LicencaPremioRequest licencaPremioRequest : licencasPremioRequest) {
			LicencaPremio licencaPremio = new LicencaPremio(licencaPremioRequest);
			
			setEntidades(licencaPremioRequest, licencaPremio);
	
			licencaPremioRepository.save(licencaPremio);
		}

	}

	public void updateLicencaPremio(List<LicencaPremioRequest> licencasPremioRequest) {
		
		if(licencasPremioRequest.size() > 0) {
			deleteLicencasPremio(licencasPremioRequest.get(0).getFuncionarioExercicioId());
		}	
		
		createLicencaPremio(licencasPremioRequest);

	}

	public void deleteLicencasPremio(Long funcionarioExercicioId) {
		List<LicencaPremio> licencasPremio = licencaPremioRepository.findByFuncionarioExercicioId(funcionarioExercicioId);

		for(LicencaPremio licencaPremio : licencasPremio)
			licencaPremioRepository.delete(licencaPremio);
	}

	public List<LicencaPremioResponse> getAllLicencasPremioByFuncionarioExercicio(Long funcionarioExercicioId) {

		List<LicencaPremioResponse> licencasPremioResponse = new ArrayList<LicencaPremioResponse>();
		List<LicencaPremio> licencasPremio = licencaPremioRepository.findByFuncionarioExercicioId(funcionarioExercicioId);
		
		
		if (Objects.nonNull(licencasPremio)) {
			for(LicencaPremio licencaPremio : licencasPremio) {
				
				LicencaPremioResponse licencaPremioResponse = new LicencaPremioResponse(licencaPremio);
				licencaPremioResponse.setFuncionarioExercicioId(funcionarioExercicioId);
				licencasPremioResponse.add(licencaPremioResponse);				
			}

		}

			return licencasPremioResponse;
	}

	
	private void setEntidades(LicencaPremioRequest licencaPremioRequest, LicencaPremio licencaPremio) {
		
		if(Objects.nonNull(licencaPremioRequest.getFuncionarioExercicioId())) {
			FuncionarioExercicio funcionarioExercicio = funcionarioExercicioRepository.findById(licencaPremioRequest.getFuncionarioExercicioId())
					.orElseThrow(() -> new ResourceNotFoundException("Funcionario Exercicio", "id", licencaPremioRequest.getFuncionarioExercicioId()));
			
			licencaPremio.setFuncionarioExercicio(funcionarioExercicio);
		}
		
	}
	
	public void deleteLicencaPremio(Long id) {
		LicencaPremio licencaPremio = licencaPremioRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Licença Prêmio", "id", id));
	
			licencaPremioRepository.delete(licencaPremio);	
	}

}

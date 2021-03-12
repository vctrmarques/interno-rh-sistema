package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Consignado;
import com.rhlinkcon.model.Dependente;
import com.rhlinkcon.model.DependenteBeneficio;
import com.rhlinkcon.payload.dependenteBeneficio.DependenteBeneficioRequest;
import com.rhlinkcon.payload.dependenteBeneficio.DependenteBeneficioResponse;
import com.rhlinkcon.repository.ConsignadoRepository;
import com.rhlinkcon.repository.DependenteBeneficioRepository;
import com.rhlinkcon.repository.DependenteRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;

@Service
public class DependenteBeneficioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private DependenteBeneficioRepository dependenteBeneficioRepository;
	
	@Autowired
	private DependenteRepository dependenteRepository;
	
	@Autowired
	private ConsignadoRepository consignadoRepository;

	public void createDependenteBeneficio(List<DependenteBeneficioRequest> dependenteBeneficiosRequest) {

		for(DependenteBeneficioRequest dependenteBeneficioRequest : dependenteBeneficiosRequest) {
			DependenteBeneficio dependenteBeneficio = new DependenteBeneficio(dependenteBeneficioRequest);
			
			setEntidades(dependenteBeneficioRequest, dependenteBeneficio);
	
			dependenteBeneficioRepository.save(dependenteBeneficio);
		}

	}

	public void updateDependenteBeneficio(List<DependenteBeneficioRequest> dependenteBeneficiosRequest) {
		
		deleteDependenteBeneficio(dependenteBeneficiosRequest.get(0).getDependenteId());
		
		createDependenteBeneficio(dependenteBeneficiosRequest);

	}

	public void deleteDependenteBeneficio(Long dependenteId) {
		List<DependenteBeneficio> dependenteBeneficios = dependenteBeneficioRepository.findByDependenteId(dependenteId);

		for(DependenteBeneficio dependenteBeneficio : dependenteBeneficios)
			dependenteBeneficioRepository.delete(dependenteBeneficio);
	}

	public List<DependenteBeneficioResponse> getAllBeneficiosByDependente(Long dependenteId) {

		List<DependenteBeneficioResponse> dependenteBeneficiosResponse = new ArrayList<DependenteBeneficioResponse>();
		List<DependenteBeneficio> dependenteBeneficios = dependenteBeneficioRepository.findByDependenteId(dependenteId);
		
		if (Objects.nonNull(dependenteBeneficios)) {
			for(DependenteBeneficio dependenteBeneficio : dependenteBeneficios)
				dependenteBeneficiosResponse.add(new DependenteBeneficioResponse(dependenteBeneficio));
		}

			return dependenteBeneficiosResponse;
	}

	
	private void setEntidades(DependenteBeneficioRequest dependenteBeneficioRequest, DependenteBeneficio dependenteBeneficio) {
		
		if(Objects.nonNull(dependenteBeneficioRequest.getConsignadoId())) {
			Consignado consignado = consignadoRepository.findById(dependenteBeneficioRequest.getConsignadoId())
					.orElseThrow(() -> new ResourceNotFoundException("Consignado", "id", dependenteBeneficioRequest.getConsignadoId()));
			
			dependenteBeneficio.setConsignado(consignado);
		}
		
		if(Objects.nonNull(dependenteBeneficioRequest.getDependenteId())) {
			Dependente dependente = dependenteRepository.findById(dependenteBeneficioRequest.getDependenteId())
					.orElseThrow(() -> new ResourceNotFoundException("Dependente", "id", dependenteBeneficioRequest.getDependenteId()));
			
			dependenteBeneficio.setDependente(dependente);
		}
		
	}

}

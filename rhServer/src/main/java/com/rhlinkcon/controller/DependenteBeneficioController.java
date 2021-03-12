package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.dependenteBeneficio.DependenteBeneficioRequest;
import com.rhlinkcon.payload.dependenteBeneficio.DependenteBeneficioResponse;
import com.rhlinkcon.repository.DependenteBeneficioRepository;
import com.rhlinkcon.service.DependenteBeneficioService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class DependenteBeneficioController {

	@Autowired
	private DependenteBeneficioRepository dependenteBeneficioRepository;

	@Autowired
	private DependenteBeneficioService dependenteBeneficioService;

	@GetMapping("/dependenteBeneficios/{dependenteId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<DependenteBeneficioResponse> getAllDependenteBeneficios(@PathVariable Long dependenteId) { 
		return dependenteBeneficioService.getAllBeneficiosByDependente(dependenteId);
	}


	@PostMapping("/dependenteBeneficio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createDependente(@Valid @RequestBody List<DependenteBeneficioRequest> dependenteBeneficiosRequest) {

		dependenteBeneficioService.createDependenteBeneficio(dependenteBeneficiosRequest);
		
		return Utils.created(true, "Beneficios do Dependente criado com sucesso.");
	}

	@PutMapping("/dependenteBeneficio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateDependenteBeneficio(@Valid @RequestBody List<DependenteBeneficioRequest> dependenteBeneficiosRequest) {

		dependenteBeneficioService.updateDependenteBeneficio(dependenteBeneficiosRequest);

		return Utils.created(true, "Beneficios do Dependente atualizado com sucesso.");
	}
	
	@DeleteMapping("/dependenteBeneficio/{dependenteId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public void deleteDependenteBeneficios(@PathVariable("dependenteId") Long dependenteId) {
		dependenteBeneficioService.deleteDependenteBeneficio(dependenteId);
	}	


}

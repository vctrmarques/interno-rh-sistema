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

import com.rhlinkcon.payload.dependente.DependenteRequest;
import com.rhlinkcon.payload.dependente.DependenteResponse;
import com.rhlinkcon.repository.DependenteRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.service.DependenteService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class DependenteController {

	@Autowired
	private DependenteRepository dependenteRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private DependenteService dependenteService;

	@GetMapping("/dependentes/{funcionarioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<DependenteResponse> getAllDependentes(@PathVariable Long funcionarioId) { 
		return dependenteService.getAllDependentesByFuncionario(funcionarioId);
	}
	
	
	@GetMapping("/dependente/{dependenteId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public DependenteResponse getDependenteById(@PathVariable Long dependenteId) { 
		return dependenteService.getDependenteById(dependenteId);
	}

	@PostMapping("/dependente")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createDependente(@Valid @RequestBody List<DependenteRequest> dependenteRequest) {

		dependenteService.createDependente(dependenteRequest);
		
		return Utils.created(true, "Dependente criado com sucesso.");
	}

	@PutMapping("/dependente")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateDependente(@Valid @RequestBody List<DependenteRequest> dependenteRequest) {

		dependenteService.updateDependente(dependenteRequest);

		return Utils.created(true, "Dependente atualizado com sucesso.");
	}

	@DeleteMapping("/dependente/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteDependente(@PathVariable("id") Long id) {
		dependenteService.deleteDependente(id);

		return Utils.deleted(true, "Dependente deletado com sucesso.");
	}
	

}

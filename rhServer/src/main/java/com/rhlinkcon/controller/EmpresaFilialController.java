package com.rhlinkcon.controller;

import java.util.List;
import java.util.Objects;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.empresaFilial.EmpresaFilialRequest;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.EmpresaFilialService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class EmpresaFilialController {

	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;
	
	@Autowired
	private EmpresaFilialService empresaFilialService;
	
	@GetMapping("/listaEmpresasMatrizes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<EmpresaFilialResponse> getAllEmpresasFiliaisMatrizes() {
		return empresaFilialService.getAllEmpresasFiliais(true);
	}
	
	@GetMapping("/listaEmpresaMatrizComFiliais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<EmpresaFilialResponse> getAllEmpresasMatrizWithFiliaisToDropDown(){
		return empresaFilialService.getAllEmpresasMatrizWithFiliaisToDropDown();
	}
	
	@GetMapping("/listaEmpresasNaoMatrizes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<EmpresaFilialResponse> getAllEmpresasFiliaisNaoMatrizes() {
		return empresaFilialService.getAllEmpresasFiliais(false);
	}

	@GetMapping("/empresasFiliais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<EmpresaFilialResponse> getAllEmpresasFiliais(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "siglaNome", defaultValue = AppConstants.DEFAULT_EMPTY) String siglaNome) {
		return empresaFilialService.getAllEmpresasFiliais(page, size, siglaNome, order);
	}
	
	@GetMapping("/empresasFiliaisNoEmpresaMatriz")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<EmpresaFilialResponse> getAllEmpresasFiliaisNoEmpresaMatriz(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "siglaNome", defaultValue = AppConstants.DEFAULT_EMPTY) String siglaNome) {
		return empresaFilialService.getAllEmpresasFiliaisNoEmpresaMatriz(page, size, siglaNome, order);
	}

	@GetMapping("/empresaFilial/{empresaFilialId}")
	public EmpresaFilialResponse getEmpresaFilialById(@PathVariable Long empresaFilialId) {
		return empresaFilialService.getEmpresaFilialById(empresaFilialId);
	}

	@PostMapping("/empresaFilial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createEmpresaFilial(@Valid @RequestBody EmpresaFilialRequest empresaFilialRequest) {
		if (empresaFilialRepository.existsByNomeFilial(empresaFilialRequest.getNomeFilial())) {
			return Utils.badRequest(false, "Já existe uma Empresa com este nome!");
		}
		
		if(Objects.nonNull(empresaFilialRequest.getEmpresaMatriz()) && empresaFilialRequest.getEmpresaMatriz())
			if (empresaFilialRepository.existsByEmpresaMatriz(empresaFilialRequest.getEmpresaMatriz()))
				return Utils.badRequest(false, "Já existe uma Empresa Matriz!");
			
		
		empresaFilialService.createEmpresaFilial(empresaFilialRequest);

		return Utils.created(true, "Empresa criada com sucesso.");
	}

	@PutMapping("/empresaFilial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateEmpresaFilial(@Valid @RequestBody EmpresaFilialRequest empresaFilialRequest) {
		if (empresaFilialRepository.existsByNomeFilialAndIdNot(empresaFilialRequest.getNomeFilial(), empresaFilialRequest.getId())) {
			return Utils.badRequest(false, "Já existe uma Empresa/Filial com este nome!");
		}
		
		if (empresaFilialRepository.existsByEmpresaMatrizAndIdNot(empresaFilialRequest.getEmpresaMatriz(), empresaFilialRequest.getId())) {
			return Utils.badRequest(false, "Já existe uma Empresa Matriz!");
		}

		empresaFilialService.updateEmpresaFilial(empresaFilialRequest);
		
		return Utils.created(true, "Empresa atualizada com sucesso.");
	}


	@DeleteMapping("/empresaFilial/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteEmpresaFilial(@PathVariable("id") Long id) {
		empresaFilialService.deleteEmpresaFilial(id);
		
		return Utils.deleted(true, "Empresa deletada com sucesso.");
	}
	
	@GetMapping("/listaEmpresasFiliais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<EmpresaFilialResponse> getListEmpresaFilial() {
		return empresaFilialService.getAllEmpresasFiliaisSemMatriz();
	}
	
	@GetMapping("/empresasFiliaisSemMatrizPaginado")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<EmpresaFilialResponse> getAllEmpresasFiliaisSemMatrizPaginado(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "siglaNome", defaultValue = AppConstants.DEFAULT_EMPTY) String siglaNome) {
		return empresaFilialService.getAllEmpresasFiliaisSemMatrizPaginado(page, size, order, siglaNome);
	}	

	@GetMapping("/listaFiliais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<EmpresaFilialResponse> getAllFiliais() {
		return empresaFilialService.getAllFiliais();
	}
	
	@GetMapping("/filiais/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<EmpresaFilialResponse> getAllEmpresaFilialByNome(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return empresaFilialService.getAllEmpresaFilialByNome(search);
	}
}

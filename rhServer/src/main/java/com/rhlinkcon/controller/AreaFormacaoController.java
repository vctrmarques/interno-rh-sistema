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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.areaFormacao.AreaFormacaoRequest;
import com.rhlinkcon.payload.areaFormacao.AreaFormacaoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.AreaFormacaoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.AreaFormacaoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class AreaFormacaoController {

	@Autowired
	private AreaFormacaoRepository areaFormacaoRepository;

	@Autowired
	private AreaFormacaoService areaFormacaoService;

	@GetMapping("/areasFormacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<AreaFormacaoResponse> getAreasFormacao(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "areaFormacao", defaultValue = AppConstants.DEFAULT_EMPTY) String areaFormacao) {
		return areaFormacaoService.getAllAreas(page, size, areaFormacao, order);
	}
	
	@GetMapping("/areaFormacao/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<AreaFormacaoResponse> searchByAreaFormacao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return areaFormacaoService.searchByAreaFormacao(search);
	}
	
	@GetMapping("/listaAreasFormacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<AreaFormacaoResponse> getListAreasFormacao() {
		return areaFormacaoService.getAllAreas();
	}

	@GetMapping("/areaFormacao/{areaId}")
	public AreaFormacaoResponse getAreaFormacaoById(@PathVariable Long areaId) {
		return areaFormacaoService.getAreaFormacaoById(areaId);
	}

	@PostMapping("/areaFormacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createArea(@Valid @RequestBody AreaFormacaoRequest areaFormacaoRequest) {
		if (areaFormacaoRepository.existsByAreaFormacao(areaFormacaoRequest.getAreaFormacao())) {
			return Utils.badRequest(false, "Esta área de Formação já existe!");
		}

		areaFormacaoService.createAreaFormacao(areaFormacaoRequest);

		return Utils.created(true, "Área de Formação criada com sucesso.");
	}

	@PutMapping("/areaFormacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateArea(@Valid @RequestBody AreaFormacaoRequest areaFormacaoRequest) {
		if (areaFormacaoRepository.existsByAreaFormacaoAndIdNot(areaFormacaoRequest.getAreaFormacao(), areaFormacaoRequest.getId())) {
			return Utils.badRequest(false, "Esta área de Formação já existe!");
		}

		areaFormacaoService.updateAreaFormacao(areaFormacaoRequest);

		return Utils.created(true, "Área de Formação atualizada com sucesso.");
	}


	@DeleteMapping("/areaFormacao/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteAreaFormacao(@PathVariable("id") Long id) {
		areaFormacaoService.deleteAreaFormacao(id);

		return Utils.deleted(true, "Área de Formação deletada com sucesso.");
	}

}

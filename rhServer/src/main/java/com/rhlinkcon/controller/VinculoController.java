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

import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.vinculo.VinculoRequest;
import com.rhlinkcon.payload.vinculo.VinculoResponse;
import com.rhlinkcon.service.VinculoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class VinculoController {

//	@Autowired
//	private VinculoRepository vinculoRepository;

	@Autowired
	private VinculoService vinculoService;

	@GetMapping("/vinculos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<VinculoResponse> getAllVinculos(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order) {
		return vinculoService.getAllVinculos(page, size, order);
	}

	@GetMapping("/listaVinculos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<VinculoResponse> getAllVinculos() {
		return vinculoService.getAllVinculos();
	}

	@GetMapping("/vinculo/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<VinculoResponse> searchAllByDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return vinculoService.searchAllByDescricao(search);
	}

	@GetMapping("/vinculo/{vinculoId}")
	public VinculoResponse getVinculoById(@PathVariable Long vinculoId) {
		return vinculoService.getVinculoById(vinculoId);
	}

	@PostMapping("/vinculo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createVinculo(@Valid @RequestBody VinculoRequest vinculoRequest) {
//		if (vinculoRepository.existsByNome(vinculoRequest.getNome())) {
//			return Utils.badRequest(false, "O nome já está em uso!");
//		}

		vinculoService.createVinculo(vinculoRequest);

		return Utils.created(true, "Vinculo criado com sucesso.");
	}

	@PutMapping("/vinculo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateVinculo(@Valid @RequestBody VinculoRequest vinculoRequest) {
//		if (vinculoRepository.existsByNomeAndIdNot(vinculoRequest.getNome(), vinculoRequest.getId())) {
//			return Utils.badRequest(false, "O nome já está em uso!");
//		}

		vinculoService.updateVinculo(vinculoRequest);

		return Utils.created(true, "Vinculo atualizado com sucesso.");
	}

	@DeleteMapping("/vinculo/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteVinculo(@PathVariable("id") Long id) {
		vinculoService.deleteVinculo(id);

		return Utils.deleted(true, "Vinculo deletado com sucesso.");
	}

}

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

import com.rhlinkcon.filtro.RegraAposentadoriaFiltro;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.regraAposentadoria.RegraAposentadoriaRequest;
import com.rhlinkcon.payload.regraAposentadoria.RegraAposentadoriaResponse;
import com.rhlinkcon.service.RegraAposentadoriaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class RegraAposentadoriaController {

//	@Autowired
//	private RegraAposentadoriaRepository regraAposentadoriaRepository;

	@Autowired
	private RegraAposentadoriaService regraAposentadoriaService;

	@GetMapping("/regraAposentadoria")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<RegraAposentadoriaResponse> getAllRegraAposentadorias(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "modalidade", defaultValue = AppConstants.DEFAULT_EMPTY) String modalidade,
			@RequestParam(value = "tipo", defaultValue = AppConstants.DEFAULT_EMPTY) String tipo,
			@RequestParam(value = "leibase", defaultValue = AppConstants.DEFAULT_EMPTY) String leibase) {
		return regraAposentadoriaService.getAllRegraAposentadorias(page, size, order,
				new RegraAposentadoriaFiltro(modalidade, tipo, leibase));
	}

	@GetMapping("/publico/regraAposentadoria/findAllModalidadesAposentadorias")
	public List<String> findAllModalidadesAposentadorias() {
		return regraAposentadoriaService.findAllModalidadesAposentadorias();
	}

	@GetMapping("/regraAposentadoria/{regraAposentadoriaId}")
	public RegraAposentadoriaResponse getRegraAposentadoriaById(@PathVariable Long regraAposentadoriaId) {
		return regraAposentadoriaService.getRegraAposentadoriaById(regraAposentadoriaId);
	}

	@PostMapping("/regraAposentadoria")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createRegraAposentadoria(
			@Valid @RequestBody RegraAposentadoriaRequest regraAposentadoriaRequest) {
//		if (regraAposentadoriaRepository.existsByNome(regraAposentadoriaRequest.getNome())) {
//			return Utils.badRequest(false, "O nome já está em uso!");
//		}
//
//		if (regraAposentadoriaRepository.existsByCodigo(regraAposentadoriaRequest.getCodigo())) {
//			return Utils.badRequest(false, "O código já está em uso!");
//		}

		regraAposentadoriaService.createRegraAposentadoria(regraAposentadoriaRequest);

		return Utils.created(true, "Regra de Aposentadoria criado com sucesso.");
	}

	@PutMapping("/regraAposentadoria")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateRegraAposentadoria(
			@Valid @RequestBody RegraAposentadoriaRequest regraAposentadoriaRequest) {
//		if (regraAposentadoriaRepository.existsByNomeAndIdNot(regraAposentadoriaRequest.getNome(),
//				regraAposentadoriaRequest.getId())) {
//			return Utils.badRequest(false, "O nome já está em uso!");
//		}
//
//		if (regraAposentadoriaRepository.existsByCodigoAndIdNot(regraAposentadoriaRequest.getCodigo(),
//				regraAposentadoriaRequest.getId())) {
//			return Utils.badRequest(false, "O código já está em uso!");
//		}

		regraAposentadoriaService.updateRegraAposentadoria(regraAposentadoriaRequest);

		return Utils.created(true, "Regra de Aposentadoria atualizado com sucesso.");
	}

	@DeleteMapping("/regraAposentadoria/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteRegraAposentadoria(@PathVariable("id") Long id) {
		regraAposentadoriaService.deleteRegraAposentadoria(id);

		return Utils.deleted(true, "Regra de Aposentadoria deletado com sucesso.");
	}

}

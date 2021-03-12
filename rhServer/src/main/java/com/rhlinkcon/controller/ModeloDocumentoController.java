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

import com.rhlinkcon.payload.modeloDocumento.ModeloDocumentoRequest;
import com.rhlinkcon.payload.modeloDocumento.ModeloDocumentoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ModeloConteudoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ModeloConteudoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ModeloDocumentoController {

	@Autowired
	private ModeloConteudoRepository modeloConteudoRepository;

	@Autowired
	private ModeloConteudoService monteudoService;



	@GetMapping("/modelosDocumentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ModeloDocumentoResponse> getAllModeloDocumentoPage(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return monteudoService.getAllModeloDocumentoPage(page, size, order, descricao);
	}

	@GetMapping("/modeloDocumento/{id}")
	public ModeloDocumentoResponse getModeloDocumentoById(@PathVariable Long id) {
		return monteudoService.getModeloDocumentoById(id);
	}

	@PostMapping("/modeloDocumento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createModeloDocumento(@Valid @RequestBody ModeloDocumentoRequest modeloDocumentoRequest) {
		if (modeloConteudoRepository.existsByDescricao(modeloDocumentoRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Modelo Documento já existe!");
		}

		monteudoService.createModeloDocumento(modeloDocumentoRequest);

		return Utils.created(true, "Modelo Documento criada com sucesso.");
	}

	@PutMapping("/modeloDocumento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateModeloDocumento(@Valid @RequestBody ModeloDocumentoRequest modeloDocumentoRequest) {
		if (modeloConteudoRepository.existsByDescricaoAndIdNot(modeloDocumentoRequest.getDescricao(),
				modeloDocumentoRequest.getId())) {
			return Utils.badRequest(false, "Este Modelo Documento já existe!");
		}

		monteudoService.updateModeloDocumento(modeloDocumentoRequest);

		return Utils.created(true, "Modelo Documento atualizado com sucesso.");
	}

	@DeleteMapping("/modeloDocumento/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteModeloDocumento(@PathVariable("id") Long id) {
		monteudoService.deleteModeloDocumento(id);

		return Utils.deleted(true, "Modelo Documento deletada com sucesso.");
	}

}

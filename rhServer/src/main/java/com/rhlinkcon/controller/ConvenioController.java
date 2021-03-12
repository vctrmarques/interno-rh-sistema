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

import com.rhlinkcon.payload.convenio.ConvenioRequest;
import com.rhlinkcon.payload.convenio.ConvenioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ConvenioRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ConvenioService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ConvenioController {

	@Autowired
	private ConvenioRepository convenioRepository;

	@Autowired
	private ConvenioService convenioService;

	@GetMapping("/convenio/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ConvenioResponse> searchByCodigoOrDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return convenioService.searchByCodigoOrDescricao(search);
	}

	@GetMapping("/convenio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ConvenioResponse> getAllConvenios(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return convenioService.getAllConvenios(page, size, descricao, order);
	}

	@GetMapping("/convenio/{convenioId}")
	public ConvenioResponse getConvenioById(@PathVariable Long convenioId) {
		return convenioService.getConvenioById(convenioId);
	}

	@PostMapping("/convenio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createConvenio(@Valid @RequestBody ConvenioRequest convenioRequest) {
		if (convenioRepository.existsByDescricao(convenioRequest.getDescricao())) {
			return Utils.badRequest(false, "Este Convênio já existe!");
		}

		convenioService.createConvenio(convenioRequest);

		return Utils.created(true, "Convênio criado com sucesso.");
	}

	@PutMapping("/convenio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateConvenio(@Valid @RequestBody ConvenioRequest convenioRequest) {
		if (convenioRepository.existsByDescricaoAndIdNot(convenioRequest.getDescricao(), convenioRequest.getId())) {
			return Utils.badRequest(false, "Este Convênio já existe!");
		}

		convenioService.updateConvenio(convenioRequest);

		return Utils.created(true, "Convênio atualizado com sucesso.");
	}

	@DeleteMapping("/convenio/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteConvenio(@PathVariable("id") Long id) {
		convenioService.deleteConvenio(id);

		return Utils.deleted(true, "Classificação de Convênio deletada com sucesso.");
	}

	@GetMapping("/listaConvenios")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ConvenioResponse> getListConvenio() {
		return convenioService.getAllConvenios();
	}

}

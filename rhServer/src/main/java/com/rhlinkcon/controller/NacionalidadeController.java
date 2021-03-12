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

import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.nacionalidade.NacionalidadeRequest;
import com.rhlinkcon.payload.nacionalidade.NacionalidadeResponse;
import com.rhlinkcon.repository.NacionalidadeRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.NacionalidadeService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class NacionalidadeController {

	@Autowired
	private NacionalidadeRepository nacionalidadeRepository;

	@Autowired
	private NacionalidadeService nacionalidadeService;

	@GetMapping("/nacionalidades")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<NacionalidadeResponse> getNacionalidades(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "sigla", defaultValue = AppConstants.DEFAULT_EMPTY) String sigla) {
		return nacionalidadeService.getAllNacionalidades(page, size, sigla, order);
	}

	@GetMapping("/nacionalidade/{nacionalidadeId}")
	public NacionalidadeResponse getNacionalidadeById(@PathVariable Long nacionalidadeId) {
		return nacionalidadeService.getNacionalidadeById(nacionalidadeId);
	}

	@PostMapping("/nacionalidade")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createNacionalidade(@Valid @RequestBody NacionalidadeRequest nacionalidadeRequest) {
		if (nacionalidadeRepository.existsBySigla(nacionalidadeRequest.getSigla())) {
			return Utils.badRequest(false, "Esta nacionalidade já existe!");
		}

		nacionalidadeService.createNacionalidade(nacionalidadeRequest);

		return Utils.created(true, "Nacionalidade criada com sucesso.");
	}

	@PutMapping("/nacionalidade")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateNacionalidade(@Valid @RequestBody NacionalidadeRequest nacionalidadeRequest) {
		if (nacionalidadeRepository.existsBySiglaAndIdNot(nacionalidadeRequest.getSigla(),
				nacionalidadeRequest.getId())) {
			return Utils.badRequest(false, "Esta nacionalidade já existe!");
		}

		nacionalidadeService.updateNacionalidade(nacionalidadeRequest);

		return Utils.created(true, "Nacionalidade atualizada com sucesso.");
	}

	@DeleteMapping("/nacionalidade/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteNacionalidade(@PathVariable("id") Long id) {
		nacionalidadeService.deleteNacionalidade(id);

		return Utils.deleted(true, "Nacionalidade deletada com sucesso.");
	}

	@GetMapping("/listaNacionalidades")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<NacionalidadeResponse> getListNacionalidade() {
		return nacionalidadeService.getAllNacionalidades();
	}

	@GetMapping("/nacionalidade/search")
	public List<DadoBasicoDto> getDadoBasicoList(@RequestParam(value = "search", required = false) String search) {
		return nacionalidadeService.getDadoBasicoList(search);
	}

}

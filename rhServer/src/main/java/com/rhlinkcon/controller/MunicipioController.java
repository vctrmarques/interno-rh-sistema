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

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.municipio.MunicipioRequest;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.repository.MunicipioRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.MunicipioService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class MunicipioController {

	@Autowired
	private MunicipioRepository municipioRepository;

	@Autowired
	private MunicipioService municipioService;

	@GetMapping("/municipios")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<MunicipioResponse> getNacionalidades(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return municipioService.getAllMunicipios(page, size, descricao, order);
	}

	@GetMapping("/municipio/{municipioId}")
	public MunicipioResponse getNacionalidadeById(@PathVariable Long municipioId) {
		return municipioService.getMunicipioById(municipioId);
	}

	@PostMapping("/municipio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createMunicipio(@Valid @RequestBody MunicipioRequest municipioRequest) {
		if (municipioRepository.existsByDescricao(municipioRequest.getDescricao())) {
			return Utils.badRequest(false, "Este município já existe!");
		}

		municipioService.createMunicipio(municipioRequest);

		return Utils.created(true, "Município criado com sucesso.");
	}

	@PutMapping("/municipio")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateMunicipio(@Valid @RequestBody MunicipioRequest municipioRequest) {
		if (municipioRepository.existsByDescricaoAndIdNot(municipioRequest.getDescricao(), municipioRequest.getId())) {
			return Utils.badRequest(false, "Este municipio já existe!");
		}

		municipioService.updateMunicipio(municipioRequest);

		return Utils.created(true, "Município atualizado com sucesso.");
	}

	@DeleteMapping("/municipio/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteMunicipio(@PathVariable("id") Long id) {
		municipioService.deleteMunicipio(id);

		return Utils.deleted(true, "Município deletado com sucesso.");
	}

	@GetMapping("/listaMunicipios")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<MunicipioResponse> getAllMunicipiosByUf(
			@RequestParam(value = "id", defaultValue = AppConstants.DEFAULT_EMPTY) String id) {

		if (Utils.checkStr(id)) {
//			UnidadeFederativa uf = ufRepository.findById(Long.parseLong(id))
//					.orElseThrow(() -> new ResourceNotFoundException("UnidadeFederativa", "id", id));

			return municipioService.getAllMunicipiosByUf(Long.parseLong(id));
		} else {
			return municipioService.getAllMunicipios();
		}

	}

	@GetMapping("/listaNaturalidades")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<MunicipioResponse> getAllMunicipiosByNaturalidade(
			@RequestParam(value = "id", defaultValue = AppConstants.DEFAULT_EMPTY) String id) {

		Municipio naturalidade = municipioRepository.findById(Long.parseLong(id))
				.orElseThrow(() -> new ResourceNotFoundException("Municipio", "id", id));

		return municipioService.getAllMunicipiosByNaturalidade(naturalidade.getNaturalidade());
	}

	@GetMapping("/municipio/search")
	public List<DadoBasicoDto> getDadoBasicoList(@RequestParam(value = "search", required = true) String search,
			@RequestParam(value = "ufId", required = false) String ufId) {
		return municipioService.getDadoBasicoList(search, ufId);
	}

}

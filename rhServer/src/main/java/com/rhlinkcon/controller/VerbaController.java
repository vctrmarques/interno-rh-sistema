package com.rhlinkcon.controller;

import java.util.List;

import javax.script.ScriptException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.rhlinkcon.payload.generico.ApiResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.verba.VerbaRequest;
import com.rhlinkcon.payload.verba.VerbaResponse;
import com.rhlinkcon.service.VerbaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class VerbaController {
	@Autowired
	private VerbaService verbaService;

	@GetMapping("/verba/pensaoAlimenticia/search")
	public List<DadoBasicoDto> verbaPensaoAlimenticiaSearch() {
		return verbaService.verbaPensaoAlimenticiaSearch();
	}

	@GetMapping("/listaVerbas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<VerbaResponse> getAllVerbas() {
		return verbaService.getAllVerbas();
	}

	@GetMapping("/verbas")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<VerbaResponse> getAllVerbas(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return verbaService.getAllVerba(page, size, order, search);
	}

	@GetMapping("/verba/search")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<VerbaResponse> search(@RequestParam(value = "search") String search) {
		return verbaService.search(search);
	}

	@GetMapping("/verbas/porCategoriaProfissional/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<VerbaResponse> getVerbasPorCategoriaProfissional(@PathVariable Long id) {
		return verbaService.getVerbasPorCategoriaProfissional(id);
	}

	@GetMapping("/verba/{id}")
	public VerbaResponse getVerbaById(@PathVariable Long id) {
		return verbaService.getVerbaById(id);
	}

	@PostMapping("/verba")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createVerba(@Valid @RequestBody VerbaRequest verbaRequest) {
		verbaService.createVerba(verbaRequest);
		return Utils.created(true, "Verba criada com sucesso.");
	}

	@PutMapping("/verba")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateVerba(@Valid @RequestBody VerbaRequest verbaRequest) {
		verbaService.updateVerba(verbaRequest);
		return Utils.created(true, "Verba atualizada com sucesso.");
	}

	@DeleteMapping("/verba/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteVerba(@PathVariable("id") Long id) {
		verbaService.deleteVerba(id);

		return Utils.deleted(true, "Verba deletada com sucesso.");
	}

	@PostMapping("/verba/validarFormula")
	public ResponseEntity<?> validarFormula(@Valid @RequestBody String formulaASerValidada) throws ScriptException {
		try {
			Object resultado = null;
			return ResponseEntity.ok().body(new ApiResponse(true, "Validado com Sucesso!", resultado));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, "Fórmula Inválida!"));
		}

	}

}

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

import com.rhlinkcon.filtro.BancoFiltro;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.banco.BancoRequest;
import com.rhlinkcon.payload.banco.BancoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.banco.BancoRepository;
import com.rhlinkcon.service.BancoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class BancoController {

	@Autowired
	private BancoRepository bancoRepository;

	@Autowired
	private BancoService bancoService;

	@GetMapping("/bancos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<BancoResponse> getAllBancos(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome,
			@RequestParam(value = "codigo", defaultValue = AppConstants.DEFAULT_EMPTY) String codigo,
			@RequestParam(value = "projecao", defaultValue = AppConstants.DEFAULT_EMPTY) String projecao) {
		return bancoService.getAllBancos(page, size, order, new BancoFiltro(nome, codigo), projecao);
	}

	@GetMapping("/listaBancos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<BancoResponse> getAllBancos() {
		return bancoService.getAllBancos();
	}

	@GetMapping("/banco/{bancoId}")
	public BancoResponse getBancoById(@PathVariable Long bancoId) {
		return bancoService.getBancoById(bancoId);
	}

	@PostMapping("/banco")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createBanco(@Valid @RequestBody BancoRequest bancoRequest) {
		if (bancoRepository.existsByNome(bancoRequest.getNome())) {
			return Utils.badRequest(false, "O nome já está em uso!");
		}

		if (bancoRepository.existsByCodigo(bancoRequest.getCodigo())) {
			return Utils.badRequest(false, "O código já está em uso!");
		}

		bancoService.createBanco(bancoRequest);

		return Utils.created(true, "Banco criado com sucesso.");
	}

	@PutMapping("/banco")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateBanco(@Valid @RequestBody BancoRequest bancoRequest) {
		if (bancoRepository.existsByNomeAndIdNot(bancoRequest.getNome(), bancoRequest.getId())) {
			return Utils.badRequest(false, "O nome já está em uso!");
		}

		if (bancoRepository.existsByCodigoAndIdNot(bancoRequest.getCodigo(), bancoRequest.getId())) {
			return Utils.badRequest(false, "O código já está em uso!");
		}

		bancoService.updateBanco(bancoRequest);

		return Utils.created(true, "Banco atualizado com sucesso.");
	}

	@DeleteMapping("/banco/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteBanco(@PathVariable("id") Long id) {
		bancoService.deleteBanco(id);

		return Utils.deleted(true, "Banco deletado com sucesso.");
	}

	@GetMapping("/banco/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<BancoResponse> searchByNomeOrCodigo(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return bancoService.findByNomeOrCodigo(search);
	}

	@GetMapping("/banco/search")
	public List<DadoBasicoDto> getDadoBasicoList() {
		return bancoService.getDadoBasicoList();
	}
	
	@GetMapping("/banco/searchCompleteDTO")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<DadoBasicoDto> getDadoBasicoList(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return bancoService.getDadoBasicoList(search);
	}

}

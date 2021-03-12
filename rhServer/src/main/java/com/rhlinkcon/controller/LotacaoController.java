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

import com.rhlinkcon.payload.generico.EnumDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.lotacao.LotacaoRequest;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.repository.LotacaoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.EmpresaFilialService;
import com.rhlinkcon.service.EnumService;
import com.rhlinkcon.service.LotacaoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class LotacaoController {

	@Autowired
	private LotacaoRepository lotacaoRepository;

	@Autowired
	private LotacaoService lotacaoService;
	
	@Autowired
	private EmpresaFilialService empresaFilialService;
	@Autowired
	private EnumService enumService;
	
	
	@GetMapping("/lotacoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<LotacaoResponse> getAllLotacoes(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao) {
		return lotacaoService.getAllLotacoes(page, size, descricao, order);
	}

	@GetMapping("/lotacao/{lotacaoId}")
	public LotacaoResponse getLotacaoById(@PathVariable Long lotacaoId) {
		return lotacaoService.getLotacaoById(lotacaoId);
	}
	
	@GetMapping("/listaLotacoes")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<LotacaoResponse> getAllLotacoes() {
		return lotacaoService.getAllLotacoes();
	}
	
	@GetMapping("/listaLotacoes/{empresaFilialId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<LotacaoResponse> getAllLotacoes(@PathVariable Long empresaFilialId) {
		return empresaFilialService.getLotacoesByEmpresaFilialId(empresaFilialId);
	}
	
	@GetMapping("/listaLotacoes/filiais")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<LotacaoResponse> getLotacoesByEmpresaFiliaisId(@RequestParam(value = "empresasFiliaisId") List<Long> empresasFiliaisId) {
		return empresaFilialService.getLotacoesByEmpresaFiliaisId(empresasFiliaisId);
	}

	@PostMapping("/lotacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createLotacao(@Valid @RequestBody LotacaoRequest lotacaoRequest) {
		if (lotacaoRepository.existsByDescricao(lotacaoRequest.getDescricao())) {
			return Utils.badRequest(false, "Esta Lotação já existe!");
		}

		lotacaoService.createLotacao(lotacaoRequest);

		return Utils.created(true, "Lotação criada com sucesso.");
	}

	@PutMapping("/lotacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateLotacao(@Valid @RequestBody LotacaoRequest lotacaoRequest) {
		if (lotacaoRepository.existsByDescricaoAndIdNot(lotacaoRequest.getDescricao(), lotacaoRequest.getId())) {
			return Utils.badRequest(false, "Esta Lotação já existe!");
		}

		lotacaoService.updateLotacao(lotacaoRequest);

		return Utils.created(true, "Lotação atualizada com sucesso.");
	}

	@DeleteMapping("/lotacao/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteLotacao(@PathVariable("id") Long id) {
		lotacaoService.deleteLotacao(id);

		return Utils.deleted(true, "Lotação deletada com sucesso.");
	}
	
	@GetMapping("/lotacao/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<LotacaoResponse> getAllLotacoesFindByDescricao(@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return lotacaoService.getAllLotacoesFindByDescricao(search);
	}

}

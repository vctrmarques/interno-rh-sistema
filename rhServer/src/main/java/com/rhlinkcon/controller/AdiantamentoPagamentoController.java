package com.rhlinkcon.controller;

import java.util.ArrayList;
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

import com.rhlinkcon.model.RecebimentoEnum;
import com.rhlinkcon.model.StatusAdiantamentoEnum;
import com.rhlinkcon.model.TipoPagamentoEnum;
import com.rhlinkcon.payload.adiantamentoPagamento.AdiantamentoPagamentoRequest;
import com.rhlinkcon.payload.adiantamentoPagamento.AdiantamentoPagamentoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.service.AdiantamentoPagamentoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class AdiantamentoPagamentoController {
	@Autowired private AdiantamentoPagamentoService adiantamentoPagamentoService;
	
	@GetMapping("/listAdiantamentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<AdiantamentoPagamentoResponse> getAll() {
		return adiantamentoPagamentoService.getAll();
	}
	
	@GetMapping("/adiantamentoPagamento/{adiantamentoPagamentoId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public AdiantamentoPagamentoResponse getAdiantamentoPagamentoById(@PathVariable Long adiantamentoPagamentoId) {
		return adiantamentoPagamentoService.getAdiantamentoPagamentoById(adiantamentoPagamentoId);
	}
	
	@GetMapping("/adiantamentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<AdiantamentoPagamentoResponse> getAllAdiantamentos(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "matricula", defaultValue = AppConstants.DEFAULT_EMPTY) String matricula,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome,
			@RequestParam(value = "status", defaultValue = AppConstants.DEFAULT_EMPTY) String status,
			@RequestParam(value = "filialId", defaultValue = AppConstants.DEFAULT_EMPTY) Long filialId, 
			@RequestParam(value = "lotacaoId", defaultValue = AppConstants.DEFAULT_EMPTY) Long lotacaoId,
			@RequestParam(value = "competencia", defaultValue = AppConstants.DEFAULT_EMPTY) String competencia) {
		return adiantamentoPagamentoService.getAllAdiantamentos(page, size, order, matricula, nome, status, filialId, lotacaoId, competencia);
	}
	
	@PostMapping("/adiantamentoPagamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createAdiantamentoPagamento(@Valid @RequestBody List<AdiantamentoPagamentoRequest> adiantamentoPagamentoRequest) {
		adiantamentoPagamentoService.createAdiantamentoPagamento(adiantamentoPagamentoRequest);

		return Utils.created(true, "Adiantamento criado com sucesso.");
	}
	
	@PutMapping("/adiantamentoPagamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateAdiantamentoPagamento(@Valid @RequestBody AdiantamentoPagamentoRequest adiantamentoPagamentoRequest) {
		adiantamentoPagamentoService.updateAdiantamentoPagamento(adiantamentoPagamentoRequest);

		return Utils.created(true, "Adiantamento atualizado com sucesso.");
	}
	
	@DeleteMapping("/adiantamentoPagamento/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteRecisaoContrato(@PathVariable("id") Long id) {
		adiantamentoPagamentoService.deleteAdiantamentoPagamento(id);

		return Utils.deleted(true, "Adiantamento deletado com sucesso.");
	}
	
	@GetMapping("/listRecebimentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<String> getAllRecebimentoEnum() {
		List<String> list = new ArrayList<>();
		for (RecebimentoEnum recebimentoEnum : RecebimentoEnum.values()) {
			list.add(recebimentoEnum.getLabel());
		}
		
		return list;
	}
	
	@GetMapping("/listTiposPagamentos")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<String> getAllTipoPagamentoEnum() {
		List<String> list = new ArrayList<>();
		for (TipoPagamentoEnum tipoPagamentoEnum : TipoPagamentoEnum.values()) {
			list.add(tipoPagamentoEnum.getLabel());
		}
		
		return list;
	}
	
	@GetMapping("/listStatusAdiantamento")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<String> getAllStatusAdiantamentoEnum() {
		List<String> list = new ArrayList<>();
		for (StatusAdiantamentoEnum statusAdiantamentoEnum : StatusAdiantamentoEnum.values()) {
			list.add(statusAdiantamentoEnum.getLabel());
		}
		
		return list;
	}
	
	@GetMapping("/listByFilialAndLotacao/{filialId}/{lotacaoId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<AdiantamentoPagamentoResponse> getByEmpresaFilialIdAndLotacaoId(@PathVariable Long filialId, @PathVariable Long lotacaoId) {
		return adiantamentoPagamentoService.getByEmpresaFilialIdAndLotacaoId(filialId, lotacaoId);
	}
	
	@GetMapping("/listByFuncionario/{funcionarioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<AdiantamentoPagamentoResponse> getByEmpresaFilialIdAndLotacaoId(@PathVariable Long funcionarioId) {
		return adiantamentoPagamentoService.getByFuncionarioId(funcionarioId);
	}
}

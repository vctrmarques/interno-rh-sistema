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
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento.RelatorioFinanceiroFolhaPagamentoRequest;
import com.rhlinkcon.payload.relatorioFinanceiroFolhaPagamento.RelatorioFinanceiroFolhaPagamentoResponse;
import com.rhlinkcon.service.RelatorioFinanceiroFolhaPagamentoService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class RelatorioFinanceiroFolhaPagamentoController {

	@Autowired
	private RelatorioFinanceiroFolhaPagamentoService relatorioFinanceiroFolhaPagamentoService;

	@GetMapping("/relatorio/financeiro/naoSalvo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<RelatorioFinanceiroFolhaPagamentoResponse> getRelatorioFinanceirosNaoSalvo() {
		return relatorioFinanceiroFolhaPagamentoService.getRelatorioFinanceirosNaoSalvo();
	}

	@GetMapping("/relatorio/financeiro/salvo/{competenciaId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<RelatorioFinanceiroFolhaPagamentoResponse> getRelatorioFinanceirosSalvo(
			@PathVariable Long competenciaId) {
		return relatorioFinanceiroFolhaPagamentoService.getRelatorioFinanceirosSalvo(competenciaId);
	}

	@GetMapping("/relatorio/financeiro/{relatorioFinanceiroId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public RelatorioFinanceiroFolhaPagamentoResponse getRelatorioFinanceiroById(
			@PathVariable Long relatorioFinanceiroId) {
		return relatorioFinanceiroFolhaPagamentoService.getById(relatorioFinanceiroId);
	}

	@PostMapping("/relatorio/financeiro/")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> create(
			@Valid @RequestBody RelatorioFinanceiroFolhaPagamentoRequest relatorioFinanceiroFolhaPagamentoRequest) {

		relatorioFinanceiroFolhaPagamentoService.create(relatorioFinanceiroFolhaPagamentoRequest);

		return Utils.created(true, "Relatório Financeiro criado com sucesso.");
	}

	@PutMapping("/relatorio/financeiro/{relatorioFinanceiroId}/alterar/{statusString}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> alterarSituacao(@PathVariable Long relatorioFinanceiroId,
			@PathVariable String statusString) {

		relatorioFinanceiroFolhaPagamentoService.alterarSituacao(relatorioFinanceiroId, statusString);

		return Utils.created(true, "Relatório Financeiro atualizado com sucesso.");
	}

	@PutMapping("/relatorio/financeiro/salvar/{relatorioFinanceiroId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> inserirSituacaoSalvo(@PathVariable Long relatorioFinanceiroId) {

		relatorioFinanceiroFolhaPagamentoService.inserirSituacaoSalvo(relatorioFinanceiroId);

		return Utils.created(true, "Relatório Financeiro salvo com sucesso.");
	}

	@DeleteMapping("/relatorio/financeiro/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		relatorioFinanceiroFolhaPagamentoService.delete(id);

		return Utils.deleted(true, "Relatório Financeiro deletado com sucesso.");
	}

}

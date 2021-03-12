package com.rhlinkcon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.contracheque.ContrachequeResponse;
import com.rhlinkcon.payload.lancamento.LancamentoManualRequest;
import com.rhlinkcon.service.LancamentoService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/lancamento")
public class LancamentoController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN','FOLHA_DE_PGT_GESTAO')";

	@Autowired
	private LancamentoService lancamentoService;

	@GetMapping("/showDetalheFuncionarioFuncionario/{contrachequeId}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> getFolhaByFuncionarioFolha(@PathVariable Long contrachequeId) {
		ContrachequeResponse response = lancamentoService.findContrachequeByContrachequeId(contrachequeId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/adicionarVerbaAoFuncionario")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> adicionarVerbaAoFuncionario(@Valid @RequestBody LancamentoManualRequest request) {
		lancamentoService.adicionarLancamentoVerbaManual(request);
		return Utils.created(true, "Folha de pagamento atualizada.");
	}

	// TODO REFACTORY
//	@PostMapping("/adicionarVerbaAoFuncionario")
//	public ResponseEntity<?> adicionarVerbaAoFuncionario(
//			@Valid @RequestBody AdicionarVerbaFolhaPagamentoRequest request) {
//		try {
//			lancamentoService.adicionarVerbaAFolhaPagamento(request);
//			return Utils.created(true, "Folha de pagamento atualizada.");
//		} catch (Exception e) {
//			return Utils.created(false, "Houve um problema ao atualizar a folha de pagamento.");
//		}
//
//	}

//	@PostMapping("/removerVerbaFuncionario")
//	public ResponseEntity<?> removerVerbaAoFuncionario(@Valid @RequestBody FuncionarioVerbaLancamentoRequest request) {
//		try {
//			lancamentoService.removerVerbaFolhaPagamento(request);
//			return Utils.created(true, "Folha pagamento do(a) funcionário atualizada.");
//		} catch (Exception e) {
//			return Utils.created(false, "Houve um problema ao atualizar a folha de pagamento do Funcionário.");
//		}
//
//	}

}

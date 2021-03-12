package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalCandidatoRequest;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalCandidatoResponse;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalReponse;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalRequest;
import com.rhlinkcon.service.RequisicaoPessoalService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class RequisicaoPessoalGestaoController {
	
	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";
	
	@Autowired
	private RequisicaoPessoalService requisicaoPessoalService;
	
	@GetMapping("/requisicoesDePessoalGestao")
	@PreAuthorize(AUTHORIZE)
	public Page<RequisicaoPessoalReponse> getAllRequisicoesGestao(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order){
		return requisicaoPessoalService.getAllRequisicoesGestao(page, size, order);
	}
	
	@PutMapping("/requisicaoPessoalGestao")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> updateRequisicao(@Valid @RequestBody RequisicaoPessoalRequest requisicaoPessoalRequest) {
		requisicaoPessoalService.updateRequisicao(requisicaoPessoalRequest);
		return Utils.created(true, "Requisição de Pessoal atualizada com sucesso.");
	}
	
	@PutMapping("/requisicaoPessoalGestao/alterar/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> enviarParaAprovacao(@PathVariable Long id,@RequestBody String situacao) {
		requisicaoPessoalService.alterarSituacao(id, situacao);
		return Utils.created(true, "Situação da Requisição de Pessoal atualizada.");
	}
	
	@GetMapping("/requisicaoPessoalGestao/{id}")
	@PreAuthorize(AUTHORIZE)
	public RequisicaoPessoalReponse getRequisicao(@PathVariable Long id) {
		return requisicaoPessoalService.getRequisicao(id);
	}
	
	@GetMapping("/requisicaoPessoalGestao/candidatos/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<List<RequisicaoPessoalCandidatoResponse>> getCandidatos(@PathVariable Long id) {
		return ResponseEntity.ok(requisicaoPessoalService.getCandidatos(id));
	}
	
	@PostMapping("/requisicaoPessoalGestao/candidato")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> createRequisicaoCandidato(@Valid @RequestBody RequisicaoPessoalCandidatoRequest requisicaoPessoalCandidatoRequest) {
		requisicaoPessoalService.createRequisicaoCandidato(requisicaoPessoalCandidatoRequest);
		return Utils.created(true, "Requisição de Candidato criada com sucesso.");
	}
	
	@DeleteMapping("/requisicaoPessoalGestao/candidato/delete/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> deleteCandidato(@PathVariable("id") Long id) {
		requisicaoPessoalService.deleteCandidatoRequisicao(id);
		return Utils.deleted(true, "Candidato removido com sucesso.");
	}

}

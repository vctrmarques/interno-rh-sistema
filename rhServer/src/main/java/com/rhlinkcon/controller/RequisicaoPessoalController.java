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

import com.rhlinkcon.model.RequisicaoPessoalAnaliseEnum;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalCandidatoRequest;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalCandidatoResponse;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalReponse;
import com.rhlinkcon.payload.requisicaoPessoal.RequisicaoPessoalRequest;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.RequisicaoPessoalService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class RequisicaoPessoalController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN')";

	@Autowired
	private RequisicaoPessoalService requisicaoPessoalService;

	@GetMapping("/requisicoesDePessoal")
	@PreAuthorize(AUTHORIZE)
	public Page<RequisicaoPessoalReponse> getAllRequisicoes(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "processoSituacao", defaultValue = AppConstants.DEFAULT_EMPTY) String processoSituacao,
			@RequestParam(value = "situacao", defaultValue = AppConstants.DEFAULT_EMPTY) String situacao, @CurrentUser UserPrincipal currentUser) {
		return requisicaoPessoalService.getAllRequisicoes(page, size, order, processoSituacao, situacao, currentUser);
	}

	@PostMapping("/requisicaoPessoal")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> createRequisicao(@Valid @RequestBody RequisicaoPessoalRequest requisicaoPessoalRequest) {
		requisicaoPessoalService.createRequisicao(requisicaoPessoalRequest);
		return Utils.created(true, "Requisição de Pessoal criada com sucesso.");
	}

	@PutMapping("/requisicaoPessoal")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> updateRequisicao(@Valid @RequestBody RequisicaoPessoalRequest requisicaoPessoalRequest) {
		requisicaoPessoalService.updateRequisicao(requisicaoPessoalRequest);
		return Utils.created(true, "Requisição de Pessoal atualizada com sucesso.");
	}

	@PutMapping("/requisicaoPessoal/alterar/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> enviarParaAprovacao(@PathVariable Long id, @RequestBody String situacao) {
		requisicaoPessoalService.alterarSituacao(id, situacao);
		return Utils.created(true, "Situação da Requisição de Pessoal atualizada.");
	}

	@GetMapping("/requisicaoPessoal/{id}")
	@PreAuthorize(AUTHORIZE)
	public RequisicaoPessoalReponse getRequisicao(@PathVariable Long id) {
		return requisicaoPessoalService.getRequisicao(id);
	}

	@DeleteMapping("/requisicaoPessoal/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> deleteRequisicao(@PathVariable("id") Long id) {
		requisicaoPessoalService.deleteRequisicao(id);
		return Utils.deleted(true, "Requisição de Pessoal deletada com sucesso.");
	}

	@GetMapping("/requisicaoPessoal/analiseCurriculo/candidatos/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<List<RequisicaoPessoalCandidatoResponse>> getCandidatos(@PathVariable Long id) {
		return ResponseEntity.ok(requisicaoPessoalService.getCandidatos(id));
	}

	@PutMapping("/requisicaoPessoal/analiseCurriculo/concluir")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> concluirAnalise(@RequestBody RequisicaoPessoalCandidatoRequest req) {
		requisicaoPessoalService.concluirAnalise(req);
		return Utils.created(true, "Situação do candidato atualizada com sucesso.");
	}
	
	@PutMapping("/requisicaoPessoal/analiseCurriculo/aprovar")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> aprovarCandidato(@RequestBody RequisicaoPessoalCandidatoRequest req) {
		requisicaoPessoalService.analiseCandidato(req, RequisicaoPessoalAnaliseEnum.APROVADO);
		return Utils.created(true, "Situação do candidato atualizada com sucesso.");
	}

	@PutMapping("/requisicaoPessoal/analiseCurriculo/reprovar")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> reprovarCandidato(@RequestBody RequisicaoPessoalCandidatoRequest req) {
		requisicaoPessoalService.analiseCandidato(req, RequisicaoPessoalAnaliseEnum.REPROVADO);
		return Utils.created(true, "Situação do candidato atualizada com sucesso.");
	}
}

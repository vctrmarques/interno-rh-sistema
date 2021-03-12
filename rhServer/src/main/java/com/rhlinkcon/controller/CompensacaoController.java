package com.rhlinkcon.controller;

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

import com.rhlinkcon.payload.compensacao.CompensacaoRequest;
import com.rhlinkcon.payload.compensacao.CompensacaoResponse;
import com.rhlinkcon.payload.tomadorServico.TomadorServicoRequest;
import com.rhlinkcon.repository.CompensacaoRepository;
import com.rhlinkcon.service.CompensacaoService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CompensacaoController {

	@Autowired
	private CompensacaoRepository compensacaoRepository;

	@Autowired
	private CompensacaoService compensacaoService;


	@GetMapping("/compensacao/{tomadorServicoId}")
	public CompensacaoResponse getCompensacaoByTomadorServico(@PathVariable Long tomadorServicoId) {
		return compensacaoService.getCompensacaoByTomadorServico(tomadorServicoId);
	}

	@PostMapping("/compensacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createCompensacao(@Valid @RequestBody CompensacaoRequest compensacaoRequest) {
		
		compensacaoService.createCompensacao(compensacaoRequest);

		return Utils.created(true, "Compensação criada com sucesso.");
	}

	@PutMapping("/compensacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateCompensacao(@Valid @RequestBody CompensacaoRequest compensacaoRequest) {

		compensacaoService.updateCompensacao(compensacaoRequest);
		
		return Utils.created(true, "Compensação atualizada com sucesso.");
	}

}

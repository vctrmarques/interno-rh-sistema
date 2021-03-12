package com.rhlinkcon.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.feriasProgramacao.FeriasProgramacaoCancelarRequest;
import com.rhlinkcon.payload.feriasProgramacao.FeriasProgramacaoRequest;
import com.rhlinkcon.payload.feriasProgramacao.FeriasProgramacaoResponse;
import com.rhlinkcon.service.FeriasProgramacaoService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class FeriasProgramacaoController {

	@Autowired
	private FeriasProgramacaoService feriasProgramacaoService;

	@GetMapping("/feriasProgramacao/{feriasProgramacaoId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public FeriasProgramacaoResponse getFeriasProgramacao(@PathVariable Long feriasProgramacaoId) { 
		return feriasProgramacaoService.getFeriasProgramacaoById(feriasProgramacaoId);
	}
	
	
	@GetMapping("/feriasProgramacaoExercicioInicialMax/{funcionarioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public Date getMaxExercicioInicialFeriasProgramacaoByFuncionarioId(@PathVariable Long funcionarioId) { 
		return feriasProgramacaoService.getMaxExercicioInicialFeriasProgramacaoByFuncionarioId(funcionarioId);
	}
	
	
	@GetMapping("/feriasProgramacoes/{funcionarioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FeriasProgramacaoResponse> getAllFeriasProgramacaoByFuncionarioId(@PathVariable Long funcionarioId) { 
		return feriasProgramacaoService.getAllFeriasProgramacaoByFuncionarioId(funcionarioId);
	}
	

	@PostMapping("/feriasProgramacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createFeriasProgramacao(@Valid @RequestBody FeriasProgramacaoRequest feriasProgramacaoRequest) {
		feriasProgramacaoService.createFeriasProgramacao(feriasProgramacaoRequest);
		
		return Utils.created(true, "Férias Programada com sucesso.");
	}

	@PutMapping("/feriasProgramacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateFeriasProgramacao(@Valid @RequestBody FeriasProgramacaoRequest feriasProgramacaoRequest) {

		feriasProgramacaoService.updateFeriasProgramacao(feriasProgramacaoRequest);

		return Utils.created(true, "Programação de Férias atualizada com sucesso.");
	}
	
	@PutMapping("/feriasProgramacao/atualizarStatusParaAgendado/")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateFeriasProgramacaoStatusParaAgendado(@RequestBody FeriasProgramacaoRequest feriasProgramacaoRequest) {

		feriasProgramacaoService.updateFeriasProgramacaoStatusParaAgendado(feriasProgramacaoRequest.getId());

		return Utils.created(true, "Status atualizado com sucesso.");
	}
	
	
	@PutMapping("/feriasProgramacao/atualizarStatusParaCancelado")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateFeriasProgramacaoStatusParaCancelado(@RequestBody FeriasProgramacaoCancelarRequest feriasProgramacaoCancelarRequest) {

		feriasProgramacaoService.updateFeriasProgramacaoStatusParaCancelado(feriasProgramacaoCancelarRequest);

		return Utils.created(true, "Férias canceladas com sucesso.");
	}
	

}

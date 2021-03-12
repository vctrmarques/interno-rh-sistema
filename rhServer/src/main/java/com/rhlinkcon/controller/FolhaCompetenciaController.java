package com.rhlinkcon.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.service.FolhaCompetenciaService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/competencia")
public class FolhaCompetenciaController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN','FOLHA_DE_PGT_GESTAO')";

	@Autowired
	private FolhaCompetenciaService competenciaService;

	@GetMapping
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> retornaCompetencia() {
		return ResponseEntity.ok(competenciaService.getCompetenciaAtual());
	}

	@GetMapping("/anos")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> competenciasAnos() {
		return ResponseEntity.ok(competenciaService.retornaAnosCompetenciasFechadas());
	}

	@GetMapping("/porAno/{ano}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> competenciasPorAno(@PathVariable Integer ano) {
		return ResponseEntity.ok(competenciaService.competenciasPorAno(ano));
	}

	@GetMapping("/porAno/folhaBloqueadaConcluida/{ano}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> competenciasPorAnoFolhaBloqueadaConcluida(@PathVariable Integer ano) {
		return ResponseEntity.ok(competenciaService.competenciasPorAnoFolhaBloqueadaConcluida(ano));
	}

	@PutMapping("/fechar/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> fecharCompetencia(@PathVariable Long id) {
		competenciaService.fecharFolhaCompetencia(id);
		return Utils.created(true, "Competência fechada com sucesso.");
	}

	@PutMapping("/programar/fechar/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> programarFecharCompetencia(@PathVariable Long id,
			@RequestBody LocalDate programacaoFechamento) {
		competenciaService.programarFecharFolhaCompetencia(id, programacaoFechamento);
		return Utils.created(true, "Programação de competência realizada com sucesso.");
	}

	@PutMapping("/cancelar/programar/fechar/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> cancelarProgramacaoFechamentoCompetecia(@PathVariable Long id) {
		if (competenciaService.cancelarProgramacaoFechamentoCompetecia(id)) {
			return Utils.deleted(true,
					"Cancelamento da programação do fechamento de competência realizada com sucesso.");
		} else {
			return Utils.forbidden(false,
					"Não foi possível cancelar a programação. Provavelmente a competência já está fechada.");
		}

	}

	@GetMapping("/atual/fechada")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> getCompetenciaAtualFechada() {
		return ResponseEntity.ok(competenciaService.getCompetenciaAtualFechada());
	}

	@GetMapping("/porAno")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> retornaCompetenciasPorAno(Integer ano) {
		return ResponseEntity.ok(competenciaService.retornaCompetenciasPorAno(ano));
	}

	@GetMapping("/existe")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> existeCompetenciaMes() {
		return ResponseEntity.ok(competenciaService.existeCompetenciaMes());
	}

	@GetMapping("/verificaSeExisteAlgumaCompetenciaBloqueada")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> verificaSeExisteAlgumaCompetencia() {
		return ResponseEntity.ok(competenciaService.verificaSeExisteAlgumaCompetenciaBloqueada());
	}
}

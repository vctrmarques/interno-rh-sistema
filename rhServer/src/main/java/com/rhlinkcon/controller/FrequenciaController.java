package com.rhlinkcon.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.frequencia.FrequenciaRequest;
import com.rhlinkcon.payload.frequencia.FrequenciaResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.service.FrequenciaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class FrequenciaController {
	@Autowired
	private FrequenciaService frequenciaService;

	@GetMapping("/frequencias")
	public PagedResponse<FuncionarioResponse> getAllFrequencias(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size, String nomeFuncionario, Integer ano) {
		if (Objects.isNull(ano))
			ano = Calendar.getInstance().get(Calendar.YEAR);
		return frequenciaService.getAllFrequencias(page, size, nomeFuncionario, ano);
	}

	@GetMapping("/frequencias/{funcionarioId}/{mes}/{ano}")
	public ResponseEntity<?> getFrequencias(@PathVariable Long funcionarioId, @PathVariable Integer mes, @PathVariable Integer ano) {
		return ResponseEntity.ok(frequenciaService.getFrequencias(funcionarioId, ano, mes));
	}

	@PostMapping("/frequencia")
	public ResponseEntity<?> create(@Valid @RequestBody FrequenciaRequest frequencia) {
		frequenciaService.create(frequencia);
		return Utils.created(true, "Frequencia registrada com sucesso.");
	}

	@PutMapping("/frequencia")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateFrequencia(@Valid @RequestBody FrequenciaRequest frequencia) {
		frequenciaService.create(frequencia);
		return Utils.created(true, "Frequencia atualizada com sucesso.");
	}

	@GetMapping("/frequencias/funcionario")
	public ResponseEntity<?> frequencias(Long funcionarioId) {
		List<FrequenciaResponse> frequencias = frequenciaService.frequenciaHoje(funcionarioId);
		return ResponseEntity.ok(frequencias);
	}

	@GetMapping("/frequencia/hoje")
	public ResponseEntity<?> frequenciaHoje(Long funcionarioId) {
		Boolean contem = frequenciaService.contemFrequenciaHoje(funcionarioId);
		return ResponseEntity.ok(contem);
	}

}

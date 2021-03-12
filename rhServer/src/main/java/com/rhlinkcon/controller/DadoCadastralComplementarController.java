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

import com.rhlinkcon.payload.dadoCadastralComplementar.DadoCadastralComplementarRequest;
import com.rhlinkcon.payload.dadoCadastralComplementar.DadoCadastralComplementarResponse;
import com.rhlinkcon.service.DadoCadastralComplementarService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class DadoCadastralComplementarController {

	@Autowired
	private DadoCadastralComplementarService dadoCadastralComplementarService;


	@GetMapping("/dadoCadastralCompl/{funcionarioId}")
	public DadoCadastralComplementarResponse getDadoCadastralComplementarByFuncionarioId(@PathVariable Long funcionarioId) {
		return dadoCadastralComplementarService.getDadoCadastralComplementarByFuncionarioId(funcionarioId);
	}

	@PostMapping("/dadoCadastralCompl")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createDadoCadastralComplementar(@Valid @RequestBody DadoCadastralComplementarRequest dadoCadastralComplementarRequest) {
		
		dadoCadastralComplementarService.createDadoCadastralComplementar(dadoCadastralComplementarRequest);

		return Utils.created(true, "Dado Cadrastral Complementar criado com sucesso.");
	}

	@PutMapping("/dadoCadastralCompl")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateDadoCadastralComplementar(@Valid @RequestBody DadoCadastralComplementarRequest dadoCadastralComplementarRequest) {

		dadoCadastralComplementarService.updateDadoCadastralComplementar(dadoCadastralComplementarRequest);
		
		return Utils.created(true, "Dado Cadrastral Complementar atualizado com sucesso.");
	}
	
	@DeleteMapping("/dadoCadastralCompl/{funcionarioId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteDadoCadastralComplementar(@PathVariable Long funcionarioId) {
		dadoCadastralComplementarService.deleteDadoCadastralComplementar(funcionarioId);
		
		return Utils.deleted(true, "Dado Cadastral Complementar deletado cm sucesso,");
	}

}

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

import com.rhlinkcon.payload.definicaoOrganico.DefinicaoOrganicoRequest;
import com.rhlinkcon.payload.definicaoOrganico.DefinicaoOrganicoResponse;
import com.rhlinkcon.repository.DefinicaoOrganicoRepository;
import com.rhlinkcon.service.DefinicaoOrganicoService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class DefinicaoOrganicoController {

	@Autowired
	private DefinicaoOrganicoRepository definicaoOrganicoRepository;

	@Autowired
	private DefinicaoOrganicoService definicaoOrganicoService;


	@GetMapping("/definicaoOrganico/{empresaFilialId}")
	public DefinicaoOrganicoResponse getFuncaoById(@PathVariable Long empresaFilialId) {
		return definicaoOrganicoService.getDefinicaoOrganicaByEmpresaId(empresaFilialId);
	}
	

	@PostMapping("/definicaoOrganico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createDefinicaoOrganico(@Valid @RequestBody DefinicaoOrganicoRequest definicaoOrganicoRequest) {
//		if (funcaoRepository.existsByNome(funcaoRequest.getNome())) {
//			return Utils.badRequest(false, "Esta Função já existe!");
//		}

		definicaoOrganicoService.createDefinicaoOrganico(definicaoOrganicoRequest);

		return Utils.created(true, "Definição Orgânica criada com sucesso.");
	}

	@PutMapping("/definicaoOrganico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateDefinicaoOrganico(@Valid @RequestBody DefinicaoOrganicoRequest definicaoOrganicoRequest) {
//		if (funcaoRepository.existsByNomeAndIdNot(funcaoRequest.getNome(),
//				funcaoRequest.getId())) {
//			return Utils.badRequest(false, "Esta Função já existe!");
//		}

		definicaoOrganicoService.updateDefinicaoOrganico(definicaoOrganicoRequest);
		
		return Utils.created(true, "Definição de Organico atualizada com sucesso.");
	}

	@DeleteMapping("/deletarDefinicaoOrganico/{empresaFilialId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteDefinicaoOrganico(@PathVariable("empresaFilialId") Long empresaFilialId) {
		definicaoOrganicoService.deleteDefinicaoOrganico(empresaFilialId);

		return Utils.deleted(true, "Função deletada com sucesso.");
	}
	

}

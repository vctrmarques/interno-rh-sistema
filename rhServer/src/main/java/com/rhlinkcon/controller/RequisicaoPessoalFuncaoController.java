package com.rhlinkcon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.requisicaoPessoalFuncao.RequisicaoPessoalFuncaoReponse;
import com.rhlinkcon.service.RequisicaoPessoalFuncaoService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class RequisicaoPessoalFuncaoController {
	@Autowired
	private RequisicaoPessoalFuncaoService requisicaoPessoalFuncaoService;
	
	
	@GetMapping("/requisicoesDePessoalFuncoes/{requisicaoPessoalId}")
	public List<RequisicaoPessoalFuncaoReponse> getAllFuncoes(@PathVariable Long requisicaoPessoalId) {
		return requisicaoPessoalFuncaoService.getAllFuncoes(requisicaoPessoalId);
	}
	

	@DeleteMapping("/requisicaoDePessoalFuncao/{id}")
	public ResponseEntity<?> deleteRequisicao(@PathVariable("id") Long id) {
		requisicaoPessoalFuncaoService.deleteFuncao(id);
		return Utils.deleted(true, "Função removida da Requisição de Pessoal com sucesso.");
	}
}

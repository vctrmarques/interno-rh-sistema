package com.rhlinkcon.controller;

import com.rhlinkcon.payload.avaliacaoDesempenho.AvaliacaoDesempenhoItemRequest;
import com.rhlinkcon.service.AvaliacaoDesempenhoItemService;
import com.rhlinkcon.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AvaliacaoDesempenhoItemController {

	@Autowired
	private AvaliacaoDesempenhoItemService avaliacaoDesempenhoItemService;

	@PostMapping("/avaliacaoDesempenho/item")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> create(@Valid @RequestBody AvaliacaoDesempenhoItemRequest itemRequest) {
		System.out.println("ID AAAAAAAAAAAAAAAA =>>>> " + itemRequest.getId());
		if (avaliacaoDesempenhoItemService.existsByNomeAndAvaliacaoDesempenhoNot(itemRequest.getNome(), itemRequest.getAvaliacaoDesempenho())) {
			return Utils.badRequest(false, "Já existe um item nesta avaliação com este nome.");
		}
		if (avaliacaoDesempenhoItemService.existsByCodItemAvaliacaoAndAvaliacaoDesempenhoNot(itemRequest.getCodItemAvaliacao(), itemRequest.getAvaliacaoDesempenho())) {
			return Utils.badRequest(false, "Já existe um item nesta avaliação com este código.");
		}
		avaliacaoDesempenhoItemService.create(itemRequest);
		return Utils.created(true, "Item adicionado com sucesso.");
	}
}
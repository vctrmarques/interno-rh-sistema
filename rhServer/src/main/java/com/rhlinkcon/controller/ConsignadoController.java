package com.rhlinkcon.controller;

import java.util.List;
import java.util.Objects;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Banco;
import com.rhlinkcon.payload.consignado.ConsignadoRequest;
import com.rhlinkcon.payload.consignado.ConsignadoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.repository.ConsignadoRepository;
import com.rhlinkcon.repository.agencia.AgenciaRepository;
import com.rhlinkcon.repository.banco.BancoRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ConsignadoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ConsignadoController {

	@Autowired
	private ConsignadoRepository consignadoRepository;

	@Autowired
	private BancoRepository bancoRepository;

	@Autowired
	private AgenciaRepository agenciaRepository;

	@Autowired
	private ConsignadoService consignadoService;

	@GetMapping("/consignados")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ConsignadoResponse> getConsignados(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "contrato", defaultValue = AppConstants.DEFAULT_EMPTY) String contrato) {
		return consignadoService.getAllConsignados(page, size, contrato, order);
	}

	@GetMapping("/consignado/{consignadoId}")
	public ConsignadoResponse getCategoriaEconomicaById(@PathVariable Long consignadoId) {
		return consignadoService.getConsignadoById(consignadoId);
	}

	@PostMapping("/consignado")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createConsignado(@Valid @RequestBody ConsignadoRequest consignadoRequest) {
		if (consignadoRepository.existsByDescricao(consignadoRequest.getDescricao())) {
			return Utils.badRequest(false, "Já existe um Consignado com esta descrição!");
		}
		
		if(Objects.nonNull(consignadoRequest.getBancoId())) {
			Banco banco = bancoRepository.findById(consignadoRequest.getBancoId())
					.orElseThrow(() -> new ResourceNotFoundException("Consignado", "id", consignadoRequest.getBancoId()));
			
			if (!agenciaRepository.existsByNumeroAndBanco(consignadoRequest.getAgencia(), banco)) {
				return Utils.badRequest(false, "Esta agência não pertence a este banco.");
			}
		}


		consignadoService.createConsignado(consignadoRequest);

		return Utils.created(true, "Consignado criado com sucesso.");
	}

	@PutMapping("/consignado")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateConsignado(@Valid @RequestBody ConsignadoRequest consignadoRequest) {
		if (consignadoRepository.existsByDescricaoAndIdNot(consignadoRequest.getDescricao(),
				consignadoRequest.getId())) {
			return Utils.badRequest(false, "Já existe um Consignado com esta descrição!!");
		}

		consignadoService.updateConsignado(consignadoRequest);

		return Utils.created(true, "Consignado atualizado com sucesso.");
	}

	@DeleteMapping("/consignado/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteConsignado(@PathVariable("id") Long id) {
		consignadoService.deleteConsignado(id);

		return Utils.deleted(true, "Consignado deletado com sucesso.");
	}

	@GetMapping("/consignado/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<ConsignadoResponse> getAllConsignadosFindByDescricao(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return consignadoService.getAllConsignadosFindByDescricao(search);
	}

}

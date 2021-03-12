package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.MesAdiantamentoEnum;
import com.rhlinkcon.model.PorcentagemAdiantamentoEnum;
import com.rhlinkcon.model.SituacaoSolAdiantamentoEnum;
import com.rhlinkcon.model.SolAdiantamento;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.SolAdiantamento.SolAdiantamentoRequest;
import com.rhlinkcon.payload.SolAdiantamento.SolAdiantamentoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.SolAdiantamentoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;
@Service
public class SolAdiantamentoService {
	
	@Autowired
	private SolAdiantamentoRepository solAdiantamentoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;


	public List<SolAdiantamentoResponse> getAllSolAdiantamentos() {
		List<SolAdiantamento> adiantamentos = solAdiantamentoRepository.findAll();

		List<SolAdiantamentoResponse> listAdiantamentosResponse = new ArrayList<>();
		for (SolAdiantamento adiantamento : adiantamentos) {
			listAdiantamentosResponse.add(new SolAdiantamentoResponse(adiantamento));
		}
		return listAdiantamentosResponse;
	}
	
	public void deleteSolAdiantamento(Long id) {
		SolAdiantamento adiantamento = solAdiantamentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("SolAdiantamento", "id", id));
		solAdiantamentoRepository.delete(adiantamento);
	}
	
	public void createSolAdiantamento(SolAdiantamentoRequest adiantamentoRequest) {

		SolAdiantamento adiantamento = new SolAdiantamento();
		Funcionario solicitante = funcionarioRepository.findById(adiantamentoRequest.getSolicitante()).orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", adiantamentoRequest.getSolicitante()));
		
		adiantamento.setSolicitante(solicitante);
		adiantamento.setPorcentagemAdiantamento(PorcentagemAdiantamentoEnum.valueOf(adiantamentoRequest.getPorcentagemAdiantamento()));
		adiantamento.setMesAdiantamento(MesAdiantamentoEnum.valueOf(adiantamentoRequest.getMesAdiantamento()));
		adiantamento.setSituacao(SituacaoSolAdiantamentoEnum.valueOf(adiantamentoRequest.getSituacao()));
		
		solAdiantamentoRepository.save(adiantamento);

	}
	
	public void updateSolAdiantamento(SolAdiantamentoRequest adiantamentoRequest) {

		SolAdiantamento adiantamento = solAdiantamentoRepository.findById(adiantamentoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("SolAdiantamento", "id", adiantamentoRequest.getId()));
		
		Funcionario solicitante = funcionarioRepository.findById(adiantamentoRequest.getSolicitante())
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", adiantamentoRequest.getSolicitante()));
		
		adiantamento.setSolicitante(solicitante);
		adiantamento.setPorcentagemAdiantamento(PorcentagemAdiantamentoEnum.valueOf(adiantamentoRequest.getPorcentagemAdiantamento()));
		adiantamento.setMesAdiantamento(MesAdiantamentoEnum.valueOf(adiantamentoRequest.getMesAdiantamento()));
		adiantamento.setSituacao(SituacaoSolAdiantamentoEnum.valueOf(adiantamentoRequest.getSituacao()));
		
		solAdiantamentoRepository.save(adiantamento);

	}
	
	
	public SolAdiantamentoResponse getSolAdiantamentoById(Long solAdiantamentoId) {
		SolAdiantamento adiantamento = solAdiantamentoRepository.findById(solAdiantamentoId)
				.orElseThrow(() -> new ResourceNotFoundException("SolAdiantamento", "id", solAdiantamentoId));

		SolAdiantamentoResponse solAdiantamentoResponse = new SolAdiantamentoResponse(adiantamento);
		
		Usuario criadoPor = usuarioRepository.findById(adiantamento.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", adiantamento.getCreatedBy()));
		solAdiantamentoResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(adiantamento.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", adiantamento.getUpdatedBy()));
		solAdiantamentoResponse.setAlteradoPor(alteradoPor.getNome());

		return solAdiantamentoResponse;
	}
	
	public PagedResponse<SolAdiantamentoResponse> getAllSolAdiantamentos(int page, int size, String order, String mesAdiantamento) {
		Utils.validatePageNumberAndSize(page, size);

		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<SolAdiantamento> adiantamentos = solAdiantamentoRepository.findAll(pageable);

		if (adiantamentos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), adiantamentos.getNumber(), adiantamentos.getSize(),
					adiantamentos.getTotalElements(), adiantamentos.getTotalPages(), adiantamentos.isLast());
		}

		List<SolAdiantamentoResponse> adiantamentosResponses = adiantamentos.map(adiantamento -> {
			return new SolAdiantamentoResponse(adiantamento);
		}).getContent();
		return new PagedResponse<>(adiantamentosResponses, adiantamentos.getNumber(), adiantamentos.getSize(), adiantamentos.getTotalElements(),
				adiantamentos.getTotalPages(), adiantamentos.isLast());

	}
}

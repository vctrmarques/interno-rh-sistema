package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.AdiantamentoPagamentoFiltro;
import com.rhlinkcon.model.AdiantamentoPagamento;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.RecebimentoEnum;
import com.rhlinkcon.model.RecisaoContrato;
import com.rhlinkcon.model.ReferenciaSalarial;
import com.rhlinkcon.model.StatusAdiantamentoEnum;
import com.rhlinkcon.model.TipoPagamentoEnum;
import com.rhlinkcon.payload.adiantamentoPagamento.AdiantamentoPagamentoRequest;
import com.rhlinkcon.payload.adiantamentoPagamento.AdiantamentoPagamentoResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.payload.referenciaSalarial.ReferenciaSalarialResponse;
import com.rhlinkcon.payload.vinculo.VinculoResponse;
import com.rhlinkcon.repository.ReferenciaSalarialRepository;
import com.rhlinkcon.repository.adiantamentoPagamento.AdiantamentoPagamentoRepository;
import com.rhlinkcon.util.Utils;

@Service
public class AdiantamentoPagamentoService {
	@Autowired private AdiantamentoPagamentoRepository adiantamentoPagamentoRepository;
	@Autowired private ReferenciaSalarialRepository referenciaSalarialRepository;
	
	public void createAdiantamentoPagamento(List<AdiantamentoPagamentoRequest> list) {
		for (AdiantamentoPagamentoRequest r : list) {
				for (Long funcionarioId : r.getFuncionariosIds()) {
					AdiantamentoPagamento adiantamentoPagamento = new AdiantamentoPagamento(r);
					adiantamentoPagamento.setStatus(StatusAdiantamentoEnum.ATIVO);
					setEntidades(adiantamentoPagamento, r, funcionarioId);
					adiantamentoPagamentoRepository.save(adiantamentoPagamento);
			}
		}
	}
	
	public void updateAdiantamentoPagamento(AdiantamentoPagamentoRequest adiantamentoPagamentoRequest) {
		AdiantamentoPagamento adiantamentoPagamento = adiantamentoPagamentoRepository.findById(adiantamentoPagamentoRequest.getId()).orElseThrow(() -> new ResourceNotFoundException("AdiantamentoPagamento", "id", adiantamentoPagamentoRequest.getId()));
		setEntidades(adiantamentoPagamento, adiantamentoPagamentoRequest, adiantamentoPagamentoRequest.getFuncionarioId());
		
		adiantamentoPagamento.setTipoAdiantamento(TipoPagamentoEnum.getEnumByString(adiantamentoPagamentoRequest.getTipoAdiantamento()));
		adiantamentoPagamento.setRecebimento(RecebimentoEnum.getEnumByString(adiantamentoPagamentoRequest.getRecebimento()));
		adiantamentoPagamento.setPercentualAdiantamento(adiantamentoPagamentoRequest.getPercentualAdiantamento());
		adiantamentoPagamento.setValorAdiantamento(adiantamentoPagamentoRequest.getValorAdiantamento());
		adiantamentoPagamento.setDataInicio(adiantamentoPagamentoRequest.getDataInicio());
		adiantamentoPagamento.setDataFim(adiantamentoPagamentoRequest.getDataFim());
		adiantamentoPagamento.setCompetencia(adiantamentoPagamentoRequest.getCompetencia());
		
		if(Objects.nonNull(adiantamentoPagamento.getDataFim())) {
			adiantamentoPagamento.setStatus(StatusAdiantamentoEnum.INATIVO);
		}
		
		adiantamentoPagamentoRepository.save(adiantamentoPagamento);
	}
	
	public void deleteAdiantamentoPagamento(Long id) {
		AdiantamentoPagamento adiantamentoPagamento = adiantamentoPagamentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AdiantamentoPagamento", "id", id));
		adiantamentoPagamentoRepository.delete(adiantamentoPagamento);
	}
	
	public List<AdiantamentoPagamentoResponse> getAll() {
		List<AdiantamentoPagamentoResponse> list = new ArrayList<AdiantamentoPagamentoResponse>();
		
		for (AdiantamentoPagamento adiantamentoPagamento : adiantamentoPagamentoRepository.findAll()) {
			list.add(new AdiantamentoPagamentoResponse(adiantamentoPagamento));
		}
	
		return list;
	}
	
	public PagedResponse<AdiantamentoPagamentoResponse> getAllAdiantamentos(int page, int size, String order,
			String matricula, String nome, String status, Long filialId, Long lotacaoId, String competencia) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		AdiantamentoPagamentoFiltro filtro = new AdiantamentoPagamentoFiltro();
		
		if(!matricula.isEmpty()) {
			filtro.setMatricula(matricula);
		}
		
		if(!nome.isEmpty()) {
			filtro.setNome(nome);
		}
		
		if(!status.isEmpty()) {
			filtro.setStatus(StatusAdiantamentoEnum.getEnumByString(status));
		}
		
		if(Objects.nonNull(filialId)) {
			filtro.setFilialId(filialId);
		}
		
		if(Objects.nonNull(lotacaoId)) {
			filtro.setLotacaoId(lotacaoId);
		}
		
		if(!competencia.isEmpty()) {
			filtro.setCompetencia(competencia);
		}
		
		Page<AdiantamentoPagamento> adiantamentosPagamentos = adiantamentoPagamentoRepository.filtro(filtro, pageable);

		if (adiantamentosPagamentos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), adiantamentosPagamentos.getNumber(), adiantamentosPagamentos.getSize(),
					adiantamentosPagamentos.getTotalElements(), adiantamentosPagamentos.getTotalPages(), adiantamentosPagamentos.isLast());
		}

		List<AdiantamentoPagamentoResponse> adiantamentosPagamentosResponses = new ArrayList<>();
		
		for (AdiantamentoPagamento adiantamentoPagamento : adiantamentosPagamentos) {
			AdiantamentoPagamentoResponse adiantamentoPagamentoResponse = new AdiantamentoPagamentoResponse(adiantamentoPagamento);
			
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse();
			funcionarioResponse.setId(adiantamentoPagamento.getFuncionario().getId());
			funcionarioResponse.setNome(adiantamentoPagamento.getFuncionario().getNome());
			funcionarioResponse.setMatricula(adiantamentoPagamento.getFuncionario().getMatricula());
			
			ReferenciaSalarialResponse referenciaSalarialResponse = new ReferenciaSalarialResponse();
			
			if(Objects.nonNull(adiantamentoPagamento.getFuncionario().getReferenciaSalarialCargo())) {				
				ReferenciaSalarial referenciaSalarial = referenciaSalarialRepository.findById(adiantamentoPagamento.getFuncionario().getReferenciaSalarialCargo().getId()).get();
				referenciaSalarialResponse.setValor(referenciaSalarial.getValor());
			}
			
			funcionarioResponse.setReferenciaSalarialResponse(referenciaSalarialResponse);

			adiantamentoPagamentoResponse.setFuncionarioResponse(funcionarioResponse);
			adiantamentosPagamentosResponses.add(adiantamentoPagamentoResponse);
		}
		
		return new PagedResponse<>(adiantamentosPagamentosResponses, adiantamentosPagamentos.getNumber(), adiantamentosPagamentos.getSize(), adiantamentosPagamentos.getTotalElements(),
				adiantamentosPagamentos.getTotalPages(), adiantamentosPagamentos.isLast());
	}
	
	public AdiantamentoPagamentoResponse getAdiantamentoPagamentoById(Long adiantamentoPagamentoId) {
		AdiantamentoPagamento adiantamentoPagamento = adiantamentoPagamentoRepository.findById(adiantamentoPagamentoId).orElseThrow(() -> new ResourceNotFoundException("AdiantamentoPagamento", "id", adiantamentoPagamentoId));
		AdiantamentoPagamentoResponse adiantamentoPagamentoResponse = new AdiantamentoPagamentoResponse(adiantamentoPagamento); 
		
		FuncionarioResponse funcionarioResponse = new FuncionarioResponse();
		funcionarioResponse.setId(adiantamentoPagamento.getFuncionario().getId());
		funcionarioResponse.setNome(adiantamentoPagamento.getFuncionario().getNome());
		
		adiantamentoPagamentoResponse.setFuncionarioResponse(funcionarioResponse);
		
		return adiantamentoPagamentoResponse;
	}
	
	public List<AdiantamentoPagamentoResponse> getByEmpresaFilialIdAndLotacaoId(Long filialId, Long lotacaoId) {
		List<AdiantamentoPagamentoResponse> adiantamentosPagamentosResponses = new ArrayList<>();
		
		for (AdiantamentoPagamento adiantamentoPagamento : adiantamentoPagamentoRepository.findByEmpresaFilialIdAndLotacaoId(filialId, lotacaoId)) {
			AdiantamentoPagamentoResponse adiantamentoPagamentoResponse = new AdiantamentoPagamentoResponse(adiantamentoPagamento);
			
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse();
			funcionarioResponse.setId(adiantamentoPagamento.getFuncionario().getId());
			funcionarioResponse.setNome(adiantamentoPagamento.getFuncionario().getNome());
			funcionarioResponse.setMatricula(adiantamentoPagamento.getFuncionario().getMatricula());
			
			VinculoResponse vinculoResponse = new VinculoResponse();
			vinculoResponse.setDescricao(adiantamentoPagamento.getFuncionario().getVinculo().getDescricao());
			
			funcionarioResponse.setVinculo(vinculoResponse);
			
			EmpresaFilialResponse empresaFilialResponse = new EmpresaFilialResponse();
			empresaFilialResponse.setNomeFilial(adiantamentoPagamento.getFuncionario().getFilial().getNomeFilial());
			
			funcionarioResponse.setFilial(empresaFilialResponse);
			
			LotacaoResponse lotacaoResponse = new LotacaoResponse();
			
			if(Objects.nonNull(adiantamentoPagamento.getFuncionario().getLotacao())) {
				lotacaoResponse.setDescricaoCompleta(adiantamentoPagamento.getFuncionario().getLotacao().getDescricaoCompleta());
			}
			
			funcionarioResponse.setLotacao(lotacaoResponse);
			
			ReferenciaSalarialResponse referenciaSalarialResponse = new ReferenciaSalarialResponse();
			if(Objects.nonNull(adiantamentoPagamento.getFuncionario().getReferenciaSalarialCargo())) {				
				ReferenciaSalarial referenciaSalarial = referenciaSalarialRepository.findById(adiantamentoPagamento.getFuncionario().getReferenciaSalarialCargo().getId()).get();
				referenciaSalarialResponse.setValor(referenciaSalarial.getValor());
			}
			
			funcionarioResponse.setReferenciaSalarialResponse(referenciaSalarialResponse);

			adiantamentoPagamentoResponse.setFuncionarioResponse(funcionarioResponse);
			adiantamentosPagamentosResponses.add(adiantamentoPagamentoResponse);
		}
		
		return adiantamentosPagamentosResponses;
	}
	
	public List<AdiantamentoPagamentoResponse> getByFuncionarioId(Long funcionarioId) {
		List<AdiantamentoPagamentoResponse> adiantamentosPagamentosResponses = new ArrayList<>();
		
		for (AdiantamentoPagamento adiantamentoPagamento : adiantamentoPagamentoRepository.findByFuncionarioId(funcionarioId)) {
			AdiantamentoPagamentoResponse adiantamentoPagamentoResponse = new AdiantamentoPagamentoResponse(adiantamentoPagamento);
			
			FuncionarioResponse funcionarioResponse = new FuncionarioResponse();
			funcionarioResponse.setId(adiantamentoPagamento.getFuncionario().getId());
			funcionarioResponse.setNome(adiantamentoPagamento.getFuncionario().getNome());
			funcionarioResponse.setMatricula(adiantamentoPagamento.getFuncionario().getMatricula());
			
			VinculoResponse vinculoResponse = new VinculoResponse();
			vinculoResponse.setDescricao(adiantamentoPagamento.getFuncionario().getVinculo().getDescricao());
			
			funcionarioResponse.setVinculo(vinculoResponse);
			
			EmpresaFilialResponse empresaFilialResponse = new EmpresaFilialResponse();
			empresaFilialResponse.setNomeFilial(adiantamentoPagamento.getFuncionario().getFilial().getNomeFilial());
			
			funcionarioResponse.setFilial(empresaFilialResponse);
			
			LotacaoResponse lotacaoResponse = new LotacaoResponse();
			
			if(Objects.nonNull(adiantamentoPagamento.getFuncionario().getLotacao())) {
				lotacaoResponse.setDescricaoCompleta(adiantamentoPagamento.getFuncionario().getLotacao().getDescricaoCompleta());
			}
			
			funcionarioResponse.setLotacao(lotacaoResponse);
			
			ReferenciaSalarialResponse referenciaSalarialResponse = new ReferenciaSalarialResponse();
			ReferenciaSalarial referenciaSalarial = referenciaSalarialRepository.findById(adiantamentoPagamento.getFuncionario().getReferenciaSalarialCargo().getId()).get();
			referenciaSalarialResponse.setValor(referenciaSalarial.getValor());
			
			funcionarioResponse.setReferenciaSalarialResponse(referenciaSalarialResponse);

			adiantamentoPagamentoResponse.setFuncionarioResponse(funcionarioResponse);
			adiantamentosPagamentosResponses.add(adiantamentoPagamentoResponse);
		}
		
		return adiantamentosPagamentosResponses;
	}
	
	private void setEntidades(AdiantamentoPagamento adiantamentoPagamento, AdiantamentoPagamentoRequest adiantamentoPagamentoRequest, Long funcionarioId) {
		adiantamentoPagamento.setFuncionario(new Funcionario(funcionarioId));
		adiantamentoPagamento.setEmpresaFilial(new EmpresaFilial(adiantamentoPagamentoRequest.getEmpresaFilialId()));
		adiantamentoPagamento.setLotacao(new Lotacao(adiantamentoPagamentoRequest.getLotacaoId()));
	}
}

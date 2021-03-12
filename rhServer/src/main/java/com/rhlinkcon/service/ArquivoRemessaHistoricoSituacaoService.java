package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.jrimum.utilix.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.ArquivoRemessaHistoricoSituacao;
import com.rhlinkcon.model.ArquivoRemessaPagamento;
import com.rhlinkcon.model.ArquivoRemessaPagamentoSituacaoEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.arquivoRemessaPagamento.ArquivoRemessaHistoricoSituacaoRequest;
import com.rhlinkcon.payload.arquivoRemessaPagamento.ArquivoRemessaHistoricoSituacaoResponse;
import com.rhlinkcon.repository.arquivoRemessaPagamento.ArquivoRemessaHistoricoSituacaoRepository;
import com.rhlinkcon.repository.arquivoRemessaPagamento.ArquivoRemessaPagamentoRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ArquivoRemessaHistoricoSituacaoService {

	@Autowired
	private ArquivoRemessaHistoricoSituacaoRepository repository;
	
	@Autowired
	private ArquivoRemessaPagamentoRepository arquivoRemessaRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	public void create(@Valid ArquivoRemessaHistoricoSituacaoRequest request) {
		ArquivoRemessaHistoricoSituacao obj = load(request);
		repository.save(obj);
	}

	private ArquivoRemessaHistoricoSituacao load(ArquivoRemessaHistoricoSituacaoRequest request) {
		ArquivoRemessaHistoricoSituacao obj = new ArquivoRemessaHistoricoSituacao();
		obj.setId(request.getId());
		
		ArquivoRemessaPagamento ar = arquivoRemessaRepository.findById(request.getArquivoRemessaId())
				.orElseThrow(() -> new ResourceNotFoundException("ArquivoRemessaPagamento", "id", request.getArquivoRemessaId()));
		
		obj.setArquivoRemessa(ar);
		obj.setSituacao(ArquivoRemessaPagamentoSituacaoEnum.getEnumByString(request.getSituacao()));
		
		return obj;
	}

	public void create(ArquivoRemessaHistoricoSituacao historico) {
		ArquivoRemessaHistoricoSituacao obj = repository.findTopByOrderByIdDesc();
		
		if (Objects.isNull(obj) || !obj.getSituacao().equals(historico.getSituacao())) {
			repository.save(historico);
		}
	}

	public List<ArquivoRemessaHistoricoSituacaoResponse> getAllbyArquivoRemessaId(Long arquivoRemessaId) {
		List<ArquivoRemessaHistoricoSituacao> lista = repository.findAllByArquivoRemessaIdOrderByIdDesc(arquivoRemessaId);
		
		List<ArquivoRemessaHistoricoSituacaoResponse> listaResponse = new ArrayList<>();
	
		if(Utils.checkList(lista)) {
			for(ArquivoRemessaHistoricoSituacao a : lista) {
				ArquivoRemessaHistoricoSituacaoResponse response = new ArquivoRemessaHistoricoSituacaoResponse(a);
				
				DadoCadastralResponse auditoria = usuarioService.preencheAuditoria(a);
				
				response.setAlteradoPor(auditoria.getAlteradoPor());
				response.setCriadoPor(auditoria.getCriadoPor());
				response.setCriadoEm(a.getCreatedAt());
				response.setAlteradoEm(a.getUpdatedAt());
				
				listaResponse.add(response);
			}
		}
		
	return listaResponse;
	}
}

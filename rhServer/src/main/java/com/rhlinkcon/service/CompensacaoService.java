package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Compensacao;
import com.rhlinkcon.model.TomadorServico;
import com.rhlinkcon.payload.compensacao.CompensacaoRequest;
import com.rhlinkcon.payload.compensacao.CompensacaoResponse;
import com.rhlinkcon.repository.CompensacaoRepository;
import com.rhlinkcon.repository.TomadorServicoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;

@Service
public class CompensacaoService {

	@Autowired
	private TomadorServicoRepository tomadorServicoRepository;

	@Autowired
	private CompensacaoRepository compensacaoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public void createCompensacao(CompensacaoRequest compensacaoRequest) {

		Compensacao compensacao = new Compensacao(compensacaoRequest);

		compensacao.setTomadorServico(tomadorServicoRepository.findById(compensacaoRequest.getTomadorServicoId())
				.orElseThrow(() -> new ResourceNotFoundException("TomadorServico", "id", compensacaoRequest.getTomadorServicoId())));

		compensacaoRepository.save(compensacao);
		
		TomadorServico tomadorServico = tomadorServicoRepository.findById(compensacaoRequest.getTomadorServicoId())
				.orElseThrow(() -> new ResourceNotFoundException("TomadorServico", "id", compensacaoRequest.getTomadorServicoId()));
		
		tomadorServico.setCompensacao(compensacao);
		
		tomadorServicoRepository.save(tomadorServico);
		
	}

	public void updateCompensacao(CompensacaoRequest compensacaoRequest) {

		// Updating user's account
		Compensacao compensacao = compensacaoRepository.findById(compensacaoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Compensacao", "id", compensacaoRequest.getId()));
		
		compensacao.setCompetencia(compensacaoRequest.getCompetencia());
		compensacao.setValorCompensacao(compensacaoRequest.getValorCompensacao());
		compensacao.setDataCompensacaoInicial(compensacaoRequest.getDataCompensacaoInicial());
		compensacao.setDataCompensacaoFinal(compensacaoRequest.getDataCompensacaoFinal());
		compensacao.setCampoSeisGpsAnterior(compensacaoRequest.getCampoSeisGpsAnterior());
		compensacao.setCampoNoveGpsAnterior(compensacaoRequest.getCampoNoveGpsAnterior());
		
		compensacaoRepository.save(compensacao);

	}

	public void deleteCompensacao(Long id) {
		
		TomadorServico tomadorServico = tomadorServicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("TomadorServico", "id", id));
		
		Compensacao compensacao = compensacaoRepository.findByTomadorServico(tomadorServico);

		compensacaoRepository.delete(compensacao);
	}


	public CompensacaoResponse getCompensacaoByTomadorServico(Long tomadorServicoId) {
		
		TomadorServico tomadorServico = tomadorServicoRepository.findById(tomadorServicoId)
				.orElseThrow(() -> new ResourceNotFoundException("TomadorServico", "id", tomadorServicoId));
		
		Compensacao compensacao = compensacaoRepository.findByTomadorServico(tomadorServico);

		CompensacaoResponse compensacaoResponse = new CompensacaoResponse(compensacao);

		return compensacaoResponse;
	}

}

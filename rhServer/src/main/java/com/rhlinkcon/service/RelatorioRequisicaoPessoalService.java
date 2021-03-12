package com.rhlinkcon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.payload.relatorio.relatorioRecrutamentoESelecao.RelatorioRecrutamentoESelecaoRequest;
import com.rhlinkcon.payload.relatorio.relatorioRecrutamentoESelecao.RelatorioRecrutamentoESelecaoResponse;
import com.rhlinkcon.repository.requisicaoPessoal.RequisicaoPessoalRepositoryCustom;

@Service
public class RelatorioRequisicaoPessoalService {

	@Autowired
	private RequisicaoPessoalRepositoryCustom repository;

	public RelatorioRecrutamentoESelecaoResponse getAllForReport(RelatorioRecrutamentoESelecaoRequest request, boolean grafico) {
		return repository.getAllForReport(request, grafico);
	}
}

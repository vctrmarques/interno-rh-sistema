package com.rhlinkcon.controller.relatorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.relatorio.relatorioRecrutamentoESelecao.RelatorioRecrutamentoESelecaoRequest;
import com.rhlinkcon.payload.relatorio.relatorioRecrutamentoESelecao.RelatorioRecrutamentoESelecaoResponse;
import com.rhlinkcon.service.RelatorioRequisicaoPessoalService;

@RestController
@RequestMapping("/api/relatorio")
public class RelatorioRequisicaoPessoalController {

	@Autowired
	private RelatorioRequisicaoPessoalService service;

	@PostMapping("/requisicaoPessoal")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public RelatorioRecrutamentoESelecaoResponse getDadosParaRelatorio(
			@RequestBody RelatorioRecrutamentoESelecaoRequest request) {
		return service.getAllForReport(request, false);
	}
	
	@PostMapping("/requisicaoPessoal/grafico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public RelatorioRecrutamentoESelecaoResponse getDadosParaGrafico(
			@RequestBody RelatorioRecrutamentoESelecaoRequest request) {
		return service.getAllForReport(request, true);
	}
}

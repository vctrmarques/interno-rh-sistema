package com.rhlinkcon.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.arquivoExportacaoSiprev.ArquivoExportacaoSiprevDto;
import com.rhlinkcon.service.ArquivoExportacaoSiprevService;
import com.rhlinkcon.service.SituacaoFuncionalService;

@RestController
@RequestMapping("/api/arquivoExportacaoSiprev")
public class ArquivoExportacaoSiprevController {

	@Autowired
	private SituacaoFuncionalService situacaoFuncionalService;

	@Autowired
	private ArquivoExportacaoSiprevService arquivoExportacaoSiprevService;

	@GetMapping("/xml")
	@PreAuthorize("hasAnyRole('ADMIN','ARQUIVO_EXPORTACAO_SIPREV_GESTAO')")
	public ResponseEntity<InputStreamResource> getArquivoExportacaoXml(ArquivoExportacaoSiprevDto filtro)
			throws IOException {
		try {
			ByteArrayInputStream in = arquivoExportacaoSiprevService.getArquivoExportacaoXml(filtro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=" + filtro.getNomeArquivo() + ".xml");

			return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/situacao/funcional/search")
	@PreAuthorize("hasAnyRole('ADMIN','ARQUIVO_EXPORTACAO_SIPREV_GESTAO')")
	public List<DadoBasicoDto> getSituacoesFuncionaisList(
			@RequestParam(value = "search", required = false) String search) {
		return situacaoFuncionalService.getSituacoesFuncionaisList(search, true);
	}

}

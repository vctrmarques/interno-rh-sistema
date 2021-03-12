package com.rhlinkcon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rhlinkcon.payload.generico.ApiResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.importadorLancamentoManual.ImportadorLancamentoManualResponse;
import com.rhlinkcon.payload.importadorLancamentoManual.ValidacaoArquivoImportacaoResponse;
import com.rhlinkcon.service.ImportadorLancamentoManualService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class ImportadorLancamentoManualController {

	@Autowired
	private ImportadorLancamentoManualService importadorLancamentoManualService;

	@PostMapping("/importadorLancamentoManual/validacao/arquivo/{folhaPagamentoId}")
	@PreAuthorize("hasAnyRole('ADMIN','FOLHA_DE_PGT_GESTAO')")
	public ResponseEntity<?> validarArquivo(@RequestParam("file") MultipartFile file,
			@PathVariable("folhaPagamentoId") Long folhaPagamentoId) {
		ValidacaoArquivoImportacaoResponse validacaoResult = null;
		try {
			validacaoResult = importadorLancamentoManualService.validarArquivo(file, folhaPagamentoId);
			if (validacaoResult != null && !validacaoResult.getErroList().isEmpty())
				return ResponseEntity
						.ok(new ApiResponse(false, "A validação acusou erros na planilha.", validacaoResult));
			else
				return ResponseEntity.ok(new ApiResponse(true, "Validação efetuada com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ApiResponse(false, "Foram encontrados erros na planilha.", validacaoResult));
		}

	}

	@PostMapping("/importadorConsignados")
	@PreAuthorize("hasAnyRole('ADMIN','FOLHA_DE_PGT_GESTAO')")
	public ResponseEntity<?> importadorConsignados(@RequestParam("file") MultipartFile file) {
		ValidacaoArquivoImportacaoResponse validacaoResult = null;
		try {
			validacaoResult = importadorLancamentoManualService.importadorConsignados(file);
			if (validacaoResult != null && !validacaoResult.getErroList().isEmpty())
				return ResponseEntity
						.ok(new ApiResponse(false, "A validação acusou erros na planilha.", validacaoResult));
			else
				return ResponseEntity.ok(new ApiResponse(true, "Validação efetuada com sucesso."));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok(new ApiResponse(false, "Foram encontrados erros na planilha.", validacaoResult));
		}

	}

	@GetMapping("/importadorLancamentoManuais")
	@PreAuthorize("hasAnyRole('ADMIN','FOLHA_DE_PGT_GESTAO')")
	public PagedResponse<ImportadorLancamentoManualResponse> getAll(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "folhaPagamentoId", required = true) Long folhaPagamentoId) {
		return importadorLancamentoManualService.getAll(page, size, order, folhaPagamentoId);
	}

	@GetMapping("/importadorLancamentoManual/{importadorLancamentoManualId}")
	@PreAuthorize("hasAnyRole('ADMIN','FOLHA_DE_PGT_GESTAO')")
	public ImportadorLancamentoManualResponse getImportadorLancamentoManualById(
			@PathVariable Long importadorLancamentoManualId) {
		return importadorLancamentoManualService.getImportadorLancamentoManualById(importadorLancamentoManualId);
	}

	@DeleteMapping("/importadorLancamentoManual/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','FOLHA_DE_PGT_GESTAO')")
	public ResponseEntity<?> deleteImportadorLancamentoManual(@PathVariable("id") Long id) {
		importadorLancamentoManualService.deleteImportadorLancamentoManual(id);

		return Utils.deleted(true, "Importação de lançamento manual excluída com sucesso.");
	}

}

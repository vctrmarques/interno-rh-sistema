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
import com.rhlinkcon.payload.importadorLancamentoManual.ValidacaoArquivoImportacaoResponse;
import com.rhlinkcon.payload.importadorVerbasFuncionario.ImportadorVerbasFuncionarioResponse;
import com.rhlinkcon.service.ImportadorVerbasFuncionarioService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

/**
 * @author Eduardo Costa
 * 
 */
@RestController
@RequestMapping("/api")
public class ImportadorVerbasFuncionarioController {

	@Autowired
	private ImportadorVerbasFuncionarioService importadorVerbasFuncionarioService;

	@PostMapping("/importadorVerbasFuncionario/validacao/arquivo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> validarArquivo(@RequestParam("file") MultipartFile file) {
		ValidacaoArquivoImportacaoResponse validacaoResult = null;
		try {
			validacaoResult = importadorVerbasFuncionarioService.validarArquivo(file);
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

	@GetMapping("/importadorVerbaFuncionarios")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<ImportadorVerbasFuncionarioResponse> getAll(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order) {
		return importadorVerbasFuncionarioService.getAll(page, size, order);
	}

	@DeleteMapping("/importadorVerbasFuncionario/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteImportadorVerbasFuncionario(@PathVariable("id") Long id) {
		importadorVerbasFuncionarioService.deleteImportadorVerbasFuncionario(id);

		return Utils.deleted(true, "Importação de verba funcionário excluída com sucesso.");
	}

}

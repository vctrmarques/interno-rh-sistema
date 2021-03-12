package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
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

import com.rhlinkcon.payload.BasicRequest;
import com.rhlinkcon.payload.certidaoExSegurado.CertidaoExSeguradoRequest;
import com.rhlinkcon.payload.certidaoExSegurado.CertidaoExSeguradoResponse;
import com.rhlinkcon.payload.certidaoExSegurado.HistoricoCertidaoExSeguradoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.historicoSituacaoFuncional.HistoricoSituacaoFuncionalResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.CertidaoExSeguradoService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class CertidaoExSeguradoController {

	@Autowired
	private CertidaoExSeguradoService certidaoExSeguradoService;

	@GetMapping("/certidoesExSegurado")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_EX_SERVIDOR_VISUALIZAR','CTC_EX_SERVIDOR_CADASTRAR','CTC_EX_SERVIDOR_ATUALIZAR','CTC_EX_SERVIDOR_EXCLUIR')")
	public PagedResponse<CertidaoExSeguradoResponse> getAll(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "descricao", defaultValue = AppConstants.DEFAULT_EMPTY) String descricao,
			@RequestParam(value = "status", defaultValue = AppConstants.DEFAULT_EMPTY) String status) {

		return certidaoExSeguradoService.getAll(page, size, descricao, status, order);
	}

	@GetMapping("/certidaoExSegurado/{certidaoId}")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_EX_SERVIDOR_VISUALIZAR','CTC_EX_SERVIDOR_CADASTRAR','CTC_EX_SERVIDOR_ATUALIZAR','CTC_EX_SERVIDOR_EXCLUIR')")
	public CertidaoExSeguradoResponse getById(@PathVariable Long certidaoId) {
		return certidaoExSeguradoService.getCertidaoExSeguradoById(certidaoId);
	}

	@GetMapping("/certidaoExSegurado/relatorio/{certidaoId}")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_EX_SERVIDOR_VISUALIZAR','CTC_EX_SERVIDOR_CADASTRAR','CTC_EX_SERVIDOR_ATUALIZAR','CTC_EX_SERVIDOR_EXCLUIR')")
	public CertidaoExSeguradoResponse getRelatorioById(@PathVariable Long certidaoId) {
		return certidaoExSeguradoService.getCertidaoExSeguradoByIdToRelatorio(certidaoId);
	}

	@GetMapping("/certidaoExServidor/funcionarioHistorico/{funcionarioId}")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_EX_SERVIDOR_VISUALIZAR','CTC_EX_SERVIDOR_CADASTRAR','CTC_EX_SERVIDOR_ATUALIZAR','CTC_EX_SERVIDOR_EXCLUIR')")
	public HistoricoSituacaoFuncionalResponse getHistoricoSituacaoFuncionalByFuncionarioId(
			@PathVariable Long funcionarioId) {
		return certidaoExSeguradoService.getHistoricoSituacaoFuncionalByFuncionarioId(funcionarioId);
	}

	@GetMapping("/certidaoExSegurado/historico/{certidaoId}")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_EX_SERVIDOR_VISUALIZAR','CTC_EX_SERVIDOR_CADASTRAR','CTC_EX_SERVIDOR_ATUALIZAR','CTC_EX_SERVIDOR_EXCLUIR')")
	public List<HistoricoCertidaoExSeguradoResponse> getHistoricoById(@PathVariable Long certidaoId) {
		return certidaoExSeguradoService.getHistoricoCertidaoExSeguradoById(certidaoId);
	}

	@PostMapping("/certidaoExSegurado")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_EX_SERVIDOR_CADASTRAR')")
	public ResponseEntity<?> create(@Valid @RequestBody CertidaoExSeguradoRequest certidaoExSeguradoRequest) {
		try {
			Long certidaoId = certidaoExSeguradoService.create(certidaoExSeguradoRequest);
			return Utils.created(true, "Criado com sucesso.", certidaoId);
		} catch (ServiceException e) {
			return Utils.badRequest(false, e.getMessage());
		}
	}

	@PostMapping("/certidaoExSegurado/retificar")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_EX_SERVIDOR_CADASTRAR','CTC_EX_SERVIDOR_ATUALIZAR')")
	public ResponseEntity<?> retificar(@Valid @RequestBody BasicRequest basicRequest) {
		try {
			Long certidaoId = certidaoExSeguradoService.retificar(basicRequest.getId());
			return Utils.created(true, "Retificado com sucesso.", certidaoId);
		} catch (ServiceException e) {
			return Utils.badRequest(false, e.getMessage());
		}
	}

	@PutMapping("/certidaoExSegurado")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_EX_SERVIDOR_ATUALIZAR')")
	public ResponseEntity<?> update(@Valid @RequestBody CertidaoExSeguradoRequest certidaoExSeguradoRequest) {
		Long certidaoId = certidaoExSeguradoService.create(certidaoExSeguradoRequest);
		return Utils.created(true, "Editado com sucesso.", certidaoId);
	}

	@DeleteMapping("/certidaoExSegurado/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_EX_SERVIDOR_EXCLUIR')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		certidaoExSeguradoService.delete(id);
		return Utils.deleted(true, "deletado com sucesso.");
	}

	@DeleteMapping("/certidaoExSegurado/remover-anexo/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CTC_EX_SERVIDOR_ATUALIZAR')")
	public ResponseEntity<?> deleteAnexo(@PathVariable("id") Long id) {
		certidaoExSeguradoService.deleteAnexo(id);
		return Utils.deleted(true, "Anexo deletado com sucesso.");
	}

}
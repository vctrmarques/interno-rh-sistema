package com.rhlinkcon.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.rhlinkcon.filtro.FuncionarioFiltro;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoRequest;
import com.rhlinkcon.payload.folhaPagamento.FolhaPagamentoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.payload.tipoProcessamento.TipoProcessamentoResponse;
import com.rhlinkcon.payload.verba.VerbaResponse;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.EmpresaFilialService;
import com.rhlinkcon.service.FolhaPagamentoService;
import com.rhlinkcon.service.FuncionarioService;
import com.rhlinkcon.service.TipoProcessamentoService;
import com.rhlinkcon.service.VerbaService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/folhaPagamento")
public class FolhaPagamentoController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN','FOLHA_DE_PGT_GESTAO')";

	@Autowired
	FolhaPagamentoService folhaPagamentoService;

	@Autowired
	private TipoProcessamentoService tipoProcessamentoService;

	@Autowired
	private VerbaService verbaService;

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaFilialService empresaFilialService;

	@GetMapping("/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> retornaFolhaPagamento(@PathVariable Long id) {
		return ResponseEntity.ok(folhaPagamentoService.retornaFolhaPagamento(id));
	}

	@PostMapping
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> createFolhaPagamento(@Valid @RequestBody FolhaPagamentoRequest folhaPagamentoRequest) {
		folhaPagamentoService.createFolhaPagamento(folhaPagamentoRequest);
		return Utils.created(true, "Folha de Pagamento criada com sucesso.");
	}

	@PutMapping
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> updateFolhaPagamento(@Valid @RequestBody FolhaPagamentoRequest folhaPagamentoRequest) {
		folhaPagamentoService.updateFolhaPagamento(folhaPagamentoRequest);
		return Utils.created(true, "Folha de Pagamento atualizada com sucesso.");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> deleteFolhaPagamento(@PathVariable Long id) {
		folhaPagamentoService.deleteFolha(id);
		return Utils.ok(true, "Folha deletada com sucesso.");
	}

	@PutMapping("/reprocessar/{folhaPagamentoId}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> reprocessarFolha(@PathVariable Long folhaPagamentoId) {
		try {
			folhaPagamentoService.reprocessarFolha(folhaPagamentoId);
			return Utils.ok(true, "Recalculo iniciado com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			return Utils.badRequest(false, e.getMessage());
		}

	}

	@GetMapping("/concluidos/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> status(@PathVariable Long id) {
		return ResponseEntity.ok(folhaPagamentoService.getFolhaPagamentoSituacao(id));
	}

	@GetMapping("/porCompetencia")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> listFolhasPorCompetencia(Long competenciaId) {
		List<FolhaPagamentoResponse> response = folhaPagamentoService.listFolhasPorCompetencia(competenciaId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/filter")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> filter(Long filialId, Long lotacaoId, Long competenciaId) {
		List<FolhaPagamentoResponse> response = folhaPagamentoService.getLotacoesFilter(filialId, lotacaoId,
				competenciaId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/listaTiposProcessamentos")
	@PreAuthorize(AUTHORIZE)
	public List<TipoProcessamentoResponse> getAllTiposProcessamentos() {
		return tipoProcessamentoService.getAllTiposProcessamentos();
	}

	@GetMapping("/verbas")
	@PreAuthorize(AUTHORIZE)
	public PagedResponse<VerbaResponse> getAllVerbas(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return verbaService.getAllVerba(page, size, order, search);
	}

	@GetMapping("/funcionarios")
	@PreAuthorize(AUTHORIZE)
	public PagedResponse<FuncionarioResponse> getAllFuncionarios(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			FuncionarioFiltro funcionarioFiltro) {
		return funcionarioService.getAllFuncionarios(page, size, funcionarioFiltro, order);
	}

	@GetMapping("/listaEmpresaMatrizComFiliais")
	@PreAuthorize(AUTHORIZE)
	public List<EmpresaFilialResponse> getAllEmpresasMatrizWithFiliaisToDropDown() {
		return empresaFilialService.getAllEmpresasMatrizWithFiliaisToDropDown();
	}

	@GetMapping("/listaLotacoes/{empresaFilialId}")
	@PreAuthorize(AUTHORIZE)
	public List<LotacaoResponse> getAllLotacoes(@PathVariable Long empresaFilialId) {
		return empresaFilialService.getLotacoesByEmpresaFilialId(empresaFilialId);
	}
}

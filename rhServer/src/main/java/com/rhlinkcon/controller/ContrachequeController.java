package com.rhlinkcon.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.filtro.ContrachequeFiltro;
import com.rhlinkcon.filtro.FichaFinanceiraFiltro;
import com.rhlinkcon.payload.contracheque.ContrachequeRecalcularDto;
import com.rhlinkcon.payload.contracheque.ContrachequeRequest;
import com.rhlinkcon.payload.contracheque.ContrachequeResponse;
import com.rhlinkcon.payload.fichaFinanceira.FichaFinanceiraResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.report.service.ContrachequePdfReportService;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.ContrachequeService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/contracheque")
public class ContrachequeController {

	private static final String AUTHORIZE = "hasAnyRole('ADMIN','FOLHA_DE_PGT_GESTAO')";

	@Autowired
	ContrachequeService contrachequeService;

	@Autowired
	private ContrachequePdfReportService reportPdfService;

	@GetMapping("/porFolha")
	@PreAuthorize(AUTHORIZE)
	public PagedResponse<ContrachequeResponse> getPagedList(PagedRequest pagedRequest,
			ContrachequeFiltro requestFilter) {
		return contrachequeService.getPagedList(pagedRequest, requestFilter);
	}

	@GetMapping("/listaSituacoesFuncionais/porFolha/{folhaId}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> getSituacoesFuncionaisEntraFolha(@PathVariable Long folhaId) {
		return Utils.ok(true, "Valores retornados com Sucesso",
				contrachequeService.findDistinctTipoSituacaoFuncionalByFolhaPagamentoId(folhaId));
	}

	@GetMapping("/funcionario/no/leaf/")
	@PreAuthorize(AUTHORIZE)
	public PagedResponse<FuncionarioResponse> retornoFuncionarioSemFolha(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "folhaPagamentoId", defaultValue = AppConstants.DEFAULT_EMPTY) Long folhaPagamentoId,
			@RequestParam(value = "filialId", defaultValue = AppConstants.DEFAULT_EMPTY) Long filialId,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome,
			@RequestParam(value = "lotacaoId", defaultValue = AppConstants.DEFAULT_EMPTY) Long lotacaoId,
			@RequestParam(value = "dataAdmissao", defaultValue = AppConstants.DEFAULT_EMPTY) String dataAdmissao,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order)
			throws ParseException {

		return contrachequeService.retornoFuncionarioForaDaFolha(page, size, folhaPagamentoId, filialId, nome,
				dataAdmissao, lotacaoId, order);
	}

	@PostMapping("/recalcular")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> recalcularContracheque(@Valid @RequestBody ContrachequeRecalcularDto request) {
		try {

			contrachequeService.recalcularContracheque(request);

			return Utils.ok(true, "Recalculo iniciado com sucesso.");
		} catch (Exception e) {
			e.printStackTrace();
			return Utils.badRequest(false, e.getMessage());
		}

	}

	@GetMapping("/{idFuncionario}/periodo")
	@PreAuthorize(AUTHORIZE)
	public FichaFinanceiraResponse listaFolhaPagamentoPorFuncionarioIdEPeriodo(@PathVariable Long idFuncionario,
			FichaFinanceiraFiltro fichaFinanceiraFiltro) {
		return contrachequeService.listaFolhaPagamentoPorFuncionarioIdEPeriodo(idFuncionario, fichaFinanceiraFiltro);
	}

	@GetMapping("/{idFuncionario}/competencia/{folhaCompetenciaId}")
	@PreAuthorize(AUTHORIZE)
	public FichaFinanceiraResponse listaFolhaPagamentoPorFuncionarioIdEFolhaCompetenciaId(
			@PathVariable Long idFuncionario, @PathVariable Long folhaCompetenciaId) {
		return contrachequeService.listaFolhaPagamentoPorFuncionarioIdEFolhaCompetenciaId(idFuncionario,
				folhaCompetenciaId);
	}

	@GetMapping("/downloadFile")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<InputStreamResource> downloadPDF(ContrachequeFiltro filtro) throws IOException {
		try {
			ByteArrayInputStream bis = reportPdfService.gerarPdf(filtro);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(bis));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> deleteContracheque(@PathVariable Long id) {
		contrachequeService.deleteContracheque(id);
		return Utils.deleted(true, "Contracheque removido da folha de pagamento com sucesso.");
	}

	@PostMapping("/adicionarFuncionario")
	@PreAuthorize(AUTHORIZE)
	public ResponseEntity<?> adicionarFuncionario(@Valid @RequestBody ContrachequeRequest request) {
		contrachequeService.adicionarFuncionario(request);
		return Utils.created(true, "Folha de  de pagamento atualizada.");
	}
}

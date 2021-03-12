package com.rhlinkcon.controller;

import java.text.ParseException;
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
import com.rhlinkcon.payload.certidaoExSegurado.FuncionarioCertidaoRequest;
import com.rhlinkcon.payload.funcionario.FuncionarioAnexoRequest;
import com.rhlinkcon.payload.funcionario.FuncionarioRequest;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.security.CurrentUser;
import com.rhlinkcon.security.UserPrincipal;
import com.rhlinkcon.service.FuncionarioService;
import com.rhlinkcon.util.AppConstants;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class FuncionarioController {
	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private FuncionarioService funcionarioService;

	@GetMapping("/funcionarios")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<FuncionarioResponse> getAllFuncionarios(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			FuncionarioFiltro funcionarioFiltro) {
		return funcionarioService.getAllFuncionarios(page, size, funcionarioFiltro, order);
	}

	@GetMapping("/funcionariosTempoServico")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<FuncionarioResponse> getAllFuncionariosTempoServico(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome) {
		return funcionarioService.getAllFuncionariosTempoServico(page, size, order, nome);
	}

	@GetMapping("/funcionarios/acidentes/trabalho")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<FuncionarioResponse> getAllFuncionariosComAcidentesDeTrabalho(
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "matricula", defaultValue = AppConstants.DEFAULT_EMPTY) Integer matricula,
			@RequestParam(value = "nomeOrMatricula", defaultValue = AppConstants.DEFAULT_EMPTY) String nomeOrMatricula) {
		return funcionarioService.getAllFuncionariosComAcidentesDeTrabalho(page, size, matricula, nomeOrMatricula,
				order);
	}

	@GetMapping("/funcionario/{funcionarioId}")
	public FuncionarioResponse getFuncionarioById(@PathVariable Long funcionarioId) {
		return funcionarioService.getFuncionarioById(funcionarioId);
	}

	@GetMapping("/funcionario/{funcionarioId}/anexos")
	public FuncionarioResponse listAnexosByFuncionarioId(@PathVariable Long funcionarioId) {
		return funcionarioService.listAnexosByFuncionarioId(funcionarioId);
	}

	@PostMapping("/funcionario")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> createFuncionario(@Valid @RequestBody FuncionarioRequest funcionarioRequest) {

		if (funcionarioRepository.existsByMatricula(funcionarioRequest.getMatricula())) {
			return Utils.badRequest(false, "Já existe um funcionário com esta matrícula.");
		}

		funcionarioService.createFuncionario(funcionarioRequest);

		return Utils.created(true, "Funcionário criado com sucesso.");
	}

	@PutMapping("/funcionario")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateFuncionario(@Valid @RequestBody FuncionarioRequest funcionarioRequest) {

		if (funcionarioRepository.existsByMatriculaAndIdNot(funcionarioRequest.getMatricula(),
				funcionarioRequest.getId())) {
			return Utils.badRequest(false, "Já existe um funcionário com esta matrícula.");
		}

		funcionarioService.updateFuncionario(funcionarioRequest);

		return Utils.created(true, "Funcionário atualizado com sucesso.");
	}

	@DeleteMapping("/funcionario/{id}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteFuncionario(@PathVariable("id") Long id) {
		funcionarioService.deleteFuncionario(id);

		return Utils.deleted(true, "Funcionário deletado com sucesso.");
	}

	@GetMapping("/funcionario/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FuncionarioResponse> getAllFuncionariosFindByNome(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search,
			@RequestParam(value = "essencial", defaultValue = AppConstants.DEFAULT_EMPTY) String essencial) {
		return funcionarioService.getAllFuncionariosFindByNome(search, essencial);
	}

	@PutMapping("/funcionarioAnexo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateFuncionarioAnexo(
			@Valid @RequestBody FuncionarioAnexoRequest funcionarioAnexoRequest) {

		funcionarioService.updateFuncionarioAnexo(funcionarioAnexoRequest);

		return Utils.created(true, "Anexo atualizado com sucesso.");
	}

	@DeleteMapping("/funcionario/{funcionarioId}/anexo/{anexoId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> deleteFuncionarioAnexos(@PathVariable("funcionarioId") Long funcionarioId,
			@PathVariable("anexoId") Long anexoId) {
		funcionarioService.deleteFuncionarioAnexo(funcionarioId, anexoId);

		return Utils.deleted(true, "Anexo removido com sucesso.");
	}

	@GetMapping("/funcionario/searchNomeOrMatricula")
	public List<FuncionarioResponse> getAllFuncionarioFindByNomeOrMatricula(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return funcionarioService.getAllFuncionarioFindByNomeOrMatricula(search);
	}

	@GetMapping("/funcionarios/feriasProgramacao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public PagedResponse<FuncionarioResponse> getAllFuncionariosToFeriasProgramacao(
			@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return funcionarioService.getAllFuncionariosToFeriasProgramacao(page, size, search, order);
	}

	@GetMapping("/funcionario/verbas/{funcionarioId}")
	public FuncionarioResponse getVerbasEFuncionarioById(@PathVariable Long funcionarioId) {
		return funcionarioService.getVerbasEFuncionarioById(funcionarioId);
	}

	@GetMapping("/funcionario/searchNomeOrMatriculaOrExercicioInicial")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FuncionarioResponse> getAllFuncionariosToFeriasProgramacaoEmAberto(
			@RequestParam(value = "exercicioInicial", defaultValue = AppConstants.DEFAULT_EMPTY) String exercicioInicial,
			@RequestParam(value = "nome", defaultValue = AppConstants.DEFAULT_EMPTY) String nome,
			@RequestParam(value = "matricula", defaultValue = AppConstants.DEFAULT_EMPTY) Integer matricula) {
		return funcionarioService.getAllFuncionariosToFeriasProgramacaoEmAberto(exercicioInicial, matricula, nome);
	}

	@GetMapping("/funcionarioSimplificado/{funcionarioId}")
	public FuncionarioResponse getFuncionarioByIdSimplificado(@PathVariable Long funcionarioId) {
		return funcionarioService.getFuncionarioByIdSimplificado(funcionarioId);
	}

	@GetMapping("/funcionario/semRecisaoEfetivada")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FuncionarioResponse> getAllSemRecisaoEfetivada(
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search) {
		return funcionarioService.getAllSemRecisaoEfetivada(search);
	}

	@GetMapping("/listFuncionariosByfilial")
	public PagedResponse<FuncionarioResponse> getAllByFilial(@CurrentUser UserPrincipal currentUser,
			@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
			@RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
			@RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_EMPTY) String order,
			@RequestParam(value = "search", defaultValue = AppConstants.DEFAULT_EMPTY) String search,
			@RequestParam(value = "filialId", defaultValue = AppConstants.DEFAULT_EMPTY) Long filialId,
			@RequestParam(value = "lotacaoId", defaultValue = AppConstants.DEFAULT_EMPTY) Long lotacaoId) {
		return funcionarioService.getAllByFilial(page, size, search, filialId, order, lotacaoId);
	}

	@GetMapping("/funcionarioCountByFilial/{filialId}")
	public Long getCountByFilial(@PathVariable Long filialId) {
		return funcionarioService.getCountNumberByFilial(filialId);
	}

	@GetMapping("/funcionarioCountByFilialAndLotacao/{filialId}/{lotacaoId}")
	public Long getCountByFilialAndLotacao(@PathVariable Long filialId, @PathVariable Long lotacaoId) {
		return funcionarioService.getCountNumberByFilialAndLotacao(filialId, lotacaoId);
	}

	@GetMapping("/funcionario/searchDadosPessoais")
	public List<FuncionarioResponse> getAllByDadosPessoais(
			@RequestParam(value = "searchFuncionario", defaultValue = AppConstants.DEFAULT_EMPTY) String searchFuncionario,
			@RequestParam(value = "searchMatricula", defaultValue = AppConstants.DEFAULT_EMPTY) Integer searchMatricula,
			@RequestParam(value = "searchCPF", defaultValue = AppConstants.DEFAULT_EMPTY) String searchCPF,
			@RequestParam(value = "searchPis", defaultValue = AppConstants.DEFAULT_EMPTY) String searchPis) {

		return funcionarioService.getAllFuncionariosByDadosPessoais(searchFuncionario, searchMatricula, searchCPF,
				searchPis);

	}

	/*
	 * Consulta utilizada em Certidão Compensação
	 */
	@GetMapping("/funcionarioAdmissao/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FuncionarioResponse> searchFuncionarioTempoAdmissaoByFiltros(
			@RequestParam(value = "searchFuncionario", defaultValue = AppConstants.DEFAULT_EMPTY) String searchFuncionario,
			@RequestParam(value = "searchMatricula", defaultValue = AppConstants.DEFAULT_EMPTY) Integer searchMatricula,
			@RequestParam(value = "searchCPF", defaultValue = AppConstants.DEFAULT_EMPTY) String searchCPF,
			@RequestParam(value = "searchPis", defaultValue = AppConstants.DEFAULT_EMPTY) String searchPis)
			throws ParseException {
		return funcionarioService.findFuncionarioTempoAdmissaoByFiltros(searchFuncionario, searchMatricula, searchCPF,
				searchPis);
	}

	/*
	 * Consulta utilizada em Declaração Aposentadoria
	 */
	@GetMapping("/funcionarioAposentado/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FuncionarioResponse> searchExSeguradoAposentadoByNome(
			@RequestParam(value = "searchFuncionario", defaultValue = AppConstants.DEFAULT_EMPTY) String searchFuncionario,
			@RequestParam(value = "searchMatricula", defaultValue = AppConstants.DEFAULT_EMPTY) Integer searchMatricula,
			@RequestParam(value = "searchCPF", defaultValue = AppConstants.DEFAULT_EMPTY) String searchCPF,
			@RequestParam(value = "searchPis", defaultValue = AppConstants.DEFAULT_EMPTY) String searchPis)
			throws ParseException {
		return funcionarioService.findFuncionarioAposentadoByFiltros(searchFuncionario, searchMatricula, searchCPF,
				searchPis);
	}

	/*
	 * Consulta utilizada em Certidão Ex-Servidor
	 */
	@GetMapping("/funcionarioExonerado/searchComplete")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public List<FuncionarioResponse> searchExServidorExoneradoByFiltros(
			@RequestParam(value = "searchFuncionario", defaultValue = AppConstants.DEFAULT_EMPTY) String searchFuncionario,
			@RequestParam(value = "searchMatricula", defaultValue = AppConstants.DEFAULT_EMPTY) Integer searchMatricula,
			@RequestParam(value = "searchCPF", defaultValue = AppConstants.DEFAULT_EMPTY) String searchCPF,
			@RequestParam(value = "searchPis", defaultValue = AppConstants.DEFAULT_EMPTY) String searchPis) {

		return funcionarioService.findFuncionarioExoneradoByFiltros(searchFuncionario, searchMatricula, searchCPF,
				searchPis);
	}

	/*
	 * Para atualizar funcionário da certidão
	 */
	@PutMapping("/funcionarioCertidao")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateFuncionarioCertidao(
			@Valid @RequestBody FuncionarioCertidaoRequest funcionarioCertidaoRequest) {
		try {
			funcionarioService.updateFuncionarioCertidao(funcionarioCertidaoRequest);
			return Utils.created(true, "Funcionário atualizado com sucesso.");
		} catch (Exception e) {
			return Utils.badRequest(false, "Não foi possível atualizar o funcionário. Procure o setor responsável");
		}

	}

	@GetMapping("/funcionarioCertidao/{funcionarioId}")
	public FuncionarioResponse getFuncionarioByIdCertidao(@PathVariable Long funcionarioId) {
		return funcionarioService.getFuncionarioByIdCertidaoExServidor(funcionarioId);
	}
}

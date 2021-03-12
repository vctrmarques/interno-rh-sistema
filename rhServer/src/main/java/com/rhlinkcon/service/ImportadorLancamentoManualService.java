package com.rhlinkcon.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Agencia;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.AnexoPastaEnum;
import com.rhlinkcon.model.Banco;
import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.FolhaPagamento;
import com.rhlinkcon.model.Funcao;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.FuncionarioVerba;
import com.rhlinkcon.model.ImportadorLancamentoManual;
import com.rhlinkcon.model.ItemFormula;
import com.rhlinkcon.model.ItemFormulaTipoEnum;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.Nacionalidade;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.PensionistaVerba;
import com.rhlinkcon.model.RecorrenciaEnum;
import com.rhlinkcon.model.StatusFolhaEnum;
import com.rhlinkcon.model.TipoContaResponsavelEnum;
import com.rhlinkcon.model.TipoValorEnum;
import com.rhlinkcon.model.TipoVerba;
import com.rhlinkcon.model.Turno;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.model.Vinculo;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioRequest;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.importadorLancamentoManual.ErroImportData;
import com.rhlinkcon.payload.importadorLancamentoManual.ImportadorLancamentoManualResponse;
import com.rhlinkcon.payload.importadorLancamentoManual.PlanilhaDados;
import com.rhlinkcon.payload.importadorLancamentoManual.ValidacaoArquivoImportacaoResponse;
import com.rhlinkcon.payload.lancamento.LancamentoRequest;
import com.rhlinkcon.payload.lancamento.LancamentoVerbaManualRequest;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.FuncaoRepository;
import com.rhlinkcon.repository.FuncionarioVerbaRepository;
import com.rhlinkcon.repository.ItemFormulaRepository;
import com.rhlinkcon.repository.MunicipioRepository;
import com.rhlinkcon.repository.NacionalidadeRepository;
import com.rhlinkcon.repository.PensionistaVerbaRepository;
import com.rhlinkcon.repository.TurnoRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.agencia.AgenciaRepository;
import com.rhlinkcon.repository.banco.BancoRepository;
import com.rhlinkcon.repository.folhaPagamento.FolhaPagamentoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.importadorLancamentoManual.ImportadorLancamentoManualRepository;
import com.rhlinkcon.repository.pensionista.PensionistaRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.repository.vinculo.VinculoRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ImportadorLancamentoManualService {

	@Autowired
	private ImportadorLancamentoManualRepository importadorLancamentoManualRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private VerbaRepository verbaRepository;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private PensionistaRepository pensionistaRepository;
	@Autowired
	private FuncionarioVerbaRepository funcionarioVerbaRepository;
	@Autowired
	private PensionistaVerbaRepository pensionistaVerbaRepository;
	@Autowired
	private AnexoService anexoService;
	@Autowired
	private FolhaPagamentoRepository fPagamentoRepository;
	@Autowired
	private ItemFormulaRepository itemFormulaRepository;
	@Autowired
	private FuncionarioService funcionarioService;
	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;
	@Autowired
	private NacionalidadeRepository nacionalidadeRepository;
	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;
	@Autowired
	private MunicipioRepository municipioRepository;
	@Autowired
	private VinculoRepository vinculoRepository;
	@Autowired
	private FuncaoRepository funcaoRepository;
	@Autowired
	private BancoRepository bancoRepository;
	@Autowired
	private AgenciaRepository agenciaRepository;
	@Autowired
	private TurnoRepository turnoRepository;

	public ValidacaoArquivoImportacaoResponse importadorFuncionarios(MultipartFile file)
			throws IOException, InvalidFormatException {

		ValidacaoArquivoImportacaoResponse result = new ValidacaoArquivoImportacaoResponse();
		List<FuncionarioRequest> funcionarioList = new ArrayList<FuncionarioRequest>();

		Workbook workbook = WorkbookFactory.create(file.getInputStream());

		if (workbook.getNumberOfSheets() == 0)
			throw new BadRequestException("A planilha não possui aba para ser validada.");

		Sheet sheet = workbook.getSheetAt(0);

		DataFormatter dataFormatter = new DataFormatter();

		EmpresaFilial empMatriz = empresaFilialRepository.findFirstByEmpresaMatriz(true);
		Nacionalidade nacioBr = nacionalidadeRepository.findBySiglaIgnoreCaseContaining("Brasil");
		Vinculo vinculo = vinculoRepository.findByDescricao("APOSENTADO");
		Funcao funcao = funcaoRepository.findByNomeIgnoreCaseContaining("NÃO ATRIBUIDA").get(0);
		Banco banco = bancoRepository.findByCodigo("104");
		Agencia agencia = agenciaRepository.findFirstByNumeroAndBancoId(Integer.valueOf(2510), banco.getId());
		Turno turno = turnoRepository.findAll().get(0);
		UnidadeFederativa ufDefault = unidadeFederativaRepository.findAll().get(0);
		Nacionalidade nacionDefault = nacionalidadeRepository.findAll().get(0);

		Map<String, UnidadeFederativa> unidadeFederativaMap = new HashMap<>();
		Map<String, Municipio> municipioMap = new HashMap<>();

		municipioRepository.findAll().forEach(municipio -> {
			municipioMap.put(municipio.getDescricao() + municipio.getUf().getId(), municipio);
		});

		unidadeFederativaRepository.findAll().forEach(uf -> {
			unidadeFederativaMap.put(uf.getSigla(), uf);
		});

		Map<Integer, Integer> funcionarioMatriculaMap = new HashMap<>();

		boolean firstLoop = true;
		int process = 1;
		for (Row row : sheet) {
			if (firstLoop) {
				firstLoop = false;
				continue;
			}

			if (isRowEmpty(row))
				break;

			FuncionarioRequest funcionarioRequest = new FuncionarioRequest();

			// Empresa/Filial
			funcionarioRequest.setEmpresaId(empMatriz.getId());
			funcionarioRequest.setFilialId(empMatriz.getId());
			funcionarioRequest.setGrauInstrucao("NAO_INFORMADO");
			funcionarioRequest.setVinculoId(vinculo.getId());
			funcionarioRequest.setFuncaoId(funcao.getId());
			funcionarioRequest.setBancoId(banco.getId());
			funcionarioRequest.setAgenciaBancariaId(agencia.getId());
			funcionarioRequest.setAgencia(agencia.getNome());
			funcionarioRequest.setProbatorio(false);
			funcionarioRequest.setJornadaTrabalhoId(turno.getId());
			funcionarioRequest.setTipoConta(TipoContaResponsavelEnum.CONTA_CORRENTE.name());

			for (Cell cell : row) {

				String cellValue = dataFormatter.formatCellValue(cell);

				// Validação Básica
				if (Objects.isNull(cellValue) || cell.getCellTypeEnum() == CellType.BLANK
						|| cellValue.trim().isEmpty()) {

					if (cell.getColumnIndex() != 14 && cell.getColumnIndex() != 3 && cell.getColumnIndex() != 20
							&& cell.getColumnIndex() != 8 && cell.getColumnIndex() != 22 && cell.getColumnIndex() != 23
							&& cell.getColumnIndex() != 5 && cell.getColumnIndex() != 13
							&& cell.getColumnIndex() != 24) {
						result.getErroList().add(new ErroImportData(row.getRowNum() + 1, "Coluna não preenchida."));
						break;
					}

					if (cell.getColumnIndex() == 22)
						funcionarioRequest.setNumero("SN");

					if (cell.getColumnIndex() == 13)
						funcionarioRequest.setUfOrgaoExpeditorId(ufDefault.getId());

					if (cell.getColumnIndex() == 24)
						funcionarioRequest.setBairro("Não Informado");

				} else {

					if (cell.getColumnIndex() == 0) {
						funcionarioRequest.setRowNum(row.getRowNum() + 1);
						funcionarioRequest.setMatricula(Integer.parseInt(cellValue.trim()));
					} else if (cell.getColumnIndex() == 1) {
						funcionarioRequest.setNome(cellValue.trim());
					} else if (cell.getColumnIndex() == 2) {
						String ano = cellValue.trim().substring(0, 4);
						String mes = cellValue.trim().substring(4, 6);
						String dia = cellValue.trim().substring(6, 8);
						LocalDate niver = LocalDate.of(Integer.parseInt(ano), Integer.parseInt(mes),
								Integer.parseInt(dia));
						funcionarioRequest.setDataNascimento(niver.atStartOfDay(ZoneOffset.UTC).toInstant());
					} else if (cell.getColumnIndex() == 3) {
						if (cellValue.trim().equals("F"))
							funcionarioRequest.setSexo(cellValue.trim().charAt(0));
						else if (cellValue.trim().equals("M"))
							funcionarioRequest.setSexo(cellValue.trim().charAt(0));
						else
							funcionarioRequest.setSexo(String.valueOf("N").charAt(0));
					} else if (cell.getColumnIndex() == 4) {
						if (cellValue.trim().equals("D"))
							funcionarioRequest.setEstadoCivil("DIVORCIADO");
						else if (cellValue.trim().equals("S"))
							funcionarioRequest.setEstadoCivil("SOLTEIRO");
						else if (cellValue.trim().equals("C"))
							funcionarioRequest.setEstadoCivil("CASADO");
						else
							funcionarioRequest.setEstadoCivil("OUTROS");
					} else if (cell.getColumnIndex() == 5) {
						if (cellValue.trim().equals("BR"))
							funcionarioRequest.setNacionalidade(new DadoBasicoDto(nacioBr));
						else
							funcionarioRequest.setNacionalidade(new DadoBasicoDto(nacionDefault));
					} else if (cell.getColumnIndex() == 6) {
						if (unidadeFederativaMap.containsKey(cellValue.trim())) {
							funcionarioRequest.setNaturalidadeUf(unidadeFederativaMap.get(cellValue.trim()).getId());
						}
					} else if (cell.getColumnIndex() == 7) {
						if (funcionarioRequest.getNaturalidadeUf() != null) {
							if (municipioMap.containsKey(cellValue.trim() + funcionarioRequest.getNaturalidadeUf())) {
								funcionarioRequest.setNaturalidade(new DadoBasicoDto(
										municipioMap.get(cellValue.trim() + funcionarioRequest.getNaturalidadeUf())));
							}
						}
					} else if (cell.getColumnIndex() == 8) {
						funcionarioRequest.setTipoSanguineo(cellValue.trim());
					} else if (cell.getColumnIndex() == 9) {
						funcionarioRequest.setNomePai(cellValue.trim());
					} else if (cell.getColumnIndex() == 10) {
						funcionarioRequest.setNomeMae(cellValue.trim());
					} else if (cell.getColumnIndex() == 11) {
						funcionarioRequest.setIdentidade(cellValue.trim());
					} else if (cell.getColumnIndex() == 12) {
						funcionarioRequest.setOrgaoExpeditor(cellValue.trim());
					} else if (cell.getColumnIndex() == 13) {
						if (unidadeFederativaMap.containsKey(cellValue.trim())) {
							funcionarioRequest
									.setUfOrgaoExpeditorId(unidadeFederativaMap.get(cellValue.trim()).getId());
						} else {
							funcionarioRequest.setUfOrgaoExpeditorId(ufDefault.getId());
						}
					} else if (cell.getColumnIndex() == 14) {
						funcionarioRequest.setNumeroCtps(cellValue.trim());
					} else if (cell.getColumnIndex() == 15) {
						funcionarioRequest.setCpf(cellValue.trim().length() == 11 ? cellValue.trim() : "99999999999");
					} else if (cell.getColumnIndex() == 16) {
						funcionarioRequest.setPisPasep(cellValue.trim());
					} else if (cell.getColumnIndex() == 17) {
						funcionarioRequest.setTituloEleitor(cellValue.trim());
					} else if (cell.getColumnIndex() == 18) {
						funcionarioRequest.setZona(Integer.valueOf(cellValue.trim()));
					} else if (cell.getColumnIndex() == 19) {
						funcionarioRequest.setSecao(Integer.valueOf(cellValue.trim()));
					} else if (cell.getColumnIndex() == 20) {
						if (unidadeFederativaMap.containsKey(cellValue.trim())) {
							funcionarioRequest.setUfTituloEleitorId(unidadeFederativaMap.get(cellValue.trim()).getId());
						} else {
							funcionarioRequest.setUfTituloEleitorId(ufDefault.getId());
						}
					} else if (cell.getColumnIndex() == 21) {
						funcionarioRequest.setLogradouro(cellValue.trim());
					} else if (cell.getColumnIndex() == 22) {
						if (funcionarioRequest.getNumero() == null)
							funcionarioRequest.setNumero(cellValue.trim().isEmpty() ? "SN" : cellValue.trim());
					} else if (cell.getColumnIndex() == 23) {
						funcionarioRequest.setComplemento(cellValue.trim());
					} else if (cell.getColumnIndex() == 24) {
						if (funcionarioRequest.getBairro() == null)
							funcionarioRequest
									.setBairro(cellValue.trim().isEmpty() ? "Não Informado" : cellValue.trim());
					} else if (cell.getColumnIndex() == 25) {
						if (unidadeFederativaMap.containsKey(cellValue.trim())) {
							funcionarioRequest.setUfId(unidadeFederativaMap.get(cellValue.trim()).getId());
						}
					} else if (cell.getColumnIndex() == 26) {
						if (funcionarioRequest.getUfId() != null) {
							if (municipioMap.containsKey(cellValue.trim() + funcionarioRequest.getUfId())) {
								funcionarioRequest.setMunicipioId(
										municipioMap.get(cellValue.trim() + funcionarioRequest.getUfId()).getId());
							}
						} else {
							funcionarioRequest.setUfId(ufDefault.getId());
						}
					} else if (cell.getColumnIndex() == 27) {
						funcionarioRequest.setCep(cellValue.trim().length() == 8 ? cellValue.trim() : "99999999");
					} else if (cell.getColumnIndex() == 28) {
						funcionarioRequest.setTelefonePrincipal(cellValue.trim());
					} else if (cell.getColumnIndex() == 29) {
						String ano = cellValue.trim().substring(0, 4);
						String mes = cellValue.trim().substring(4, 6);
						String dia = cellValue.trim().substring(6, 8);
						LocalDate niver;
						try {
							niver = LocalDate.of(Integer.parseInt(ano), Integer.parseInt(mes), Integer.parseInt(dia));
						} catch (Exception e) {
							niver = LocalDate.now();
						}

						funcionarioRequest.setDataAdmissao(niver.atStartOfDay(ZoneOffset.UTC).toInstant());

						// OBS: Não existe no excel essa informação
						funcionarioRequest.setDataExpedicaoRg(niver.atStartOfDay(ZoneOffset.UTC).toInstant());

					} else if (cell.getColumnIndex() == 30) {
						funcionarioRequest.setNumeroConta(cellValue.trim().substring(0, cellValue.trim().length() - 1));
						funcionarioRequest.setDigitoConta(
								cellValue.trim().substring(cellValue.trim().length() - 1, cellValue.trim().length()));
					} else {
						break;
					}
				}

			}

			System.out.println("Processando: " + process);
			process++;

			if (!funcionarioMatriculaMap.containsKey(funcionarioRequest.getMatricula())) {
				funcionarioMatriculaMap.put(funcionarioRequest.getMatricula(), funcionarioRequest.getMatricula());
				funcionarioList.add(funcionarioRequest);
			}

		}

		workbook.close();

		// Gerando a ImportadorLancamentoManual em caso de sucesso.
		if (result.getErroList().isEmpty()) {

			Map<Integer, Funcionario> funcionarioMap = new HashMap<>();

			for (Funcionario func : funcionarioRepository.findAll()) {
				funcionarioMap.put(func.getMatricula(), func);
			}

			process = 1;
			for (FuncionarioRequest funcionario : funcionarioList) {

				if (funcionario.getBairro() == null || funcionario.getBairro().isEmpty())
					funcionario.setBairro("Não Informado");

				if (!funcionarioMap.containsKey(funcionario.getMatricula())) {
					funcionarioService.createFuncionario(funcionario);

					System.out.println("Salvando: " + process);
					process++;

				} else {
					result.getErroList().add(new ErroImportData(funcionario.getRowNum(),
							"Já existe um funcionário com esta matrícula: " + funcionario.getMatricula()));
				}

			}
		}

		return result;

	}

	public ValidacaoArquivoImportacaoResponse importadorVerbas(MultipartFile file)
			throws IOException, InvalidFormatException {

		ValidacaoArquivoImportacaoResponse result = new ValidacaoArquivoImportacaoResponse();
		List<Verba> verbaList = new ArrayList<Verba>();

		Workbook workbook = WorkbookFactory.create(file.getInputStream());

		if (workbook.getNumberOfSheets() == 0)
			throw new BadRequestException("A planilha não possui aba para ser validada.");

		Sheet sheet = workbook.getSheetAt(0);

		DataFormatter dataFormatter = new DataFormatter();

		boolean firstLoop = true;
		for (Row row : sheet) {
			if (firstLoop) {
				firstLoop = false;
				continue;
			}

			if (isRowEmpty(row))
				break;

			Verba verba = new Verba();

			for (Cell cell : row) {

				String cellValue = dataFormatter.formatCellValue(cell);

				// Validação Básica
				if (Objects.isNull(cellValue) || cell.getCellTypeEnum() == CellType.BLANK
						|| cellValue.trim().isEmpty()) {

					if (cell.getColumnIndex() == 0) {
						// Index 0 - Validação do Código Verba
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna código verba não preenchida."));

					} else if (cell.getColumnIndex() == 1) {
						// Index 1 - Validação de Matrícula
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna descrição não preenchida."));

					} else if (cell.getColumnIndex() == 2) {
						// Index 2 - Validação de Valor
						result.getErroList().add(
								new ErroImportData(row.getRowNum() + 1, "Coluna descrição resumida não preenchida."));

					} else if (cell.getColumnIndex() == 3) {
						// Index 2 - Validação de Valor
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna tipo de verba não preenchida."));
					} else {
						break;
					}

					break;

				} else {

					if (cell.getColumnIndex() == 0) {

						Optional<Verba> verbaOpt = verbaRepository.findByCodigo(cellValue.trim());
						if (verbaOpt.isPresent()) {
							break;
						} else {
							verba.setCodigo(cellValue);
						}

					} else if (cell.getColumnIndex() == 1) {
						verba.setDescricaoVerba(cellValue.trim());
					} else if (cell.getColumnIndex() == 2) {
						verba.setDescricaoResumida(cellValue.trim());
					} else if (cell.getColumnIndex() == 3) {

						if (cellValue.trim().equals("Vantagens"))
							verba.setTipoVerba(TipoVerba.VANTAGEM);
						else if (cellValue.trim().equals("Desconto"))
							verba.setTipoVerba(TipoVerba.DESCONTO);
						else
							result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
									"Campo Tipo de Verba está fora de padrão: " + cellValue));

					} else if (cell.getColumnIndex() == 4) {
						verba.setComentario(cellValue.trim());
					} else {
						break;
					}
				}
				verbaList.add(verba);
			}

		}

		workbook.close();

		// Gerando a ImportadorLancamentoManual em caso de sucesso.
		if (result.getErroList().isEmpty()) {
			for (Verba verba : verbaList) {

				Optional<Verba> verbaByCodigoOpt = verbaRepository.findByCodigo(verba.getCodigo());

				if (!verbaByCodigoOpt.isPresent()) {
					verba.setTipoValor(TipoValorEnum.MOEDA);
					verba.setCentroCusto(new CentroCusto(1L));
					verba.setRecorrencia(RecorrenciaEnum.FIXA);
					verba.setContaCredito(12345);
					verba.setContaDebito(12345);
					verbaRepository.save(verba);

					ItemFormula itemFormula = new ItemFormula();
					itemFormula.setOrdem(0);
					itemFormula.setTipo(ItemFormulaTipoEnum.NUMERAL);
					itemFormula.setValor("0");
					itemFormula.setVerba(verba);
					itemFormulaRepository.save(itemFormula);
				}

			}
		}

		return result;

	}

	public ValidacaoArquivoImportacaoResponse importadorConsignados(MultipartFile file)
			throws IOException, InvalidFormatException {

		ValidacaoArquivoImportacaoResponse result = new ValidacaoArquivoImportacaoResponse();
		List<FuncionarioVerba> funcionarioVerbaList = new ArrayList<FuncionarioVerba>();
		List<PensionistaVerba> pensionistaVerbaList = new ArrayList<PensionistaVerba>();

		Workbook workbook = WorkbookFactory.create(file.getInputStream());

		if (workbook.getNumberOfSheets() == 0)
			throw new BadRequestException("A planilha não possui aba para ser validada.");

		Sheet sheet = workbook.getSheetAt(0);

		DataFormatter dataFormatter = new DataFormatter();

		boolean firstLoop = true;
		for (Row row : sheet) {
			if (firstLoop) {
				firstLoop = false;
				continue;
			}

			if (isRowEmpty(row))
				break;

			FuncionarioVerba funcionarioVerba = new FuncionarioVerba();
			PensionistaVerba pensionistaVerba = new PensionistaVerba();

			for (Cell cell : row) {

				String cellValue = dataFormatter.formatCellValue(cell);

				// Validação Básica
				if (Objects.isNull(cellValue) || cell.getCellTypeEnum() == CellType.BLANK) {

//					if (cell.getColumnIndex() == 0) {
//						// Index 0 - Validação do Código Verba
//						result.getErroList()
//								.add(new ErroImportData(row.getRowNum() + 1, "Coluna código verba não preenchida."));
//
//					} else if (cell.getColumnIndex() == 1) {
//						// Index 1 - Validação de Matrícula
//						result.getErroList()
//								.add(new ErroImportData(row.getRowNum() + 1, "Coluna matrícula não preenchida."));
//
//					} else if (cell.getColumnIndex() == 2) {
//						// Index 2 - Validação de Valor
//						result.getErroList()
//								.add(new ErroImportData(row.getRowNum() + 1, "Coluna valor não preenchida."));
//					} else {
//						break;
//					}

					break;

				} else {

					if (cell.getColumnIndex() == 0) {

						Optional<Funcionario> funcionario = funcionarioRepository
								.findByMatricula(Integer.parseInt(cellValue));
						if (funcionario.isPresent()) {
							funcionarioVerba.setFuncionario(funcionario.get());

						} else {

							Optional<Pensionista> pensionista = pensionistaRepository
									.findByMatricula(Integer.parseInt(cellValue));
							if (pensionista.isPresent()) {
								pensionistaVerba.setPensionista(pensionista.get());

							} else {
								result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
										"Funcionário ou Pensionista não retornado para matrícula: " + cellValue));
							}

						}

					} else if (cell.getColumnIndex() == 1) {

						Optional<Verba> verba = verbaRepository.findByCodigo(cellValue);
						if (verba.isPresent()) {
							if (Objects.isNull(funcionarioVerba.getFuncionario()))
								pensionistaVerba.setVerba(verba.get());
							else
								funcionarioVerba.setVerba(verba.get());

						} else {
							result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
									"Verba não retornada para o código: " + cellValue));
						}

					} else if (cell.getColumnIndex() == 2) {

						if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {

							if (Objects.isNull(funcionarioVerba.getFuncionario())) {
								if (pensionistaVerba.getVerba() != null) {

									Verba verba = pensionistaVerba.getVerba();

									Double valorDouble = null;

									try {
										valorDouble = cell.getNumericCellValue();

									} catch (Exception e) {
										result.getErroList()
												.add(new ErroImportData(row.getRowNum() + 1,
														"A verba " + verba.getDescricaoVerba() + " de código "
																+ verba.getCodigo() + " está com o valor (" + cellValue
																+ ") fora de padrão."));
									}

									if (Objects.nonNull(valorDouble)) {
										pensionistaVerba.setParcelas(valorDouble.intValue());

									} else {
										result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
												"O valor não pode ser processado."));
									}

								} else {
									result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
											"Não foi possível gravar o valor, pois a verba em questão não foi retornada."));
								}
							} else {
								if (funcionarioVerba.getVerba() != null) {

									Verba verba = funcionarioVerba.getVerba();

									Double valorDouble = null;

									try {
										valorDouble = cell.getNumericCellValue();

									} catch (Exception e) {
										result.getErroList()
												.add(new ErroImportData(row.getRowNum() + 1,
														"A verba " + verba.getDescricaoVerba() + " de código "
																+ verba.getCodigo() + " está com o valor (" + cellValue
																+ ") fora de padrão."));
									}

									if (Objects.nonNull(valorDouble)) {
										funcionarioVerba.setParcelas(valorDouble.intValue());

									} else {
										result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
												"O valor não pode ser processado."));
									}

								} else {
									result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
											"Não foi possível gravar o valor, pois a verba em questão não foi retornada."));
								}
							}

						} else {
							result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
									"Valor inválido: " + cellValue + ", pois não trata-se um numeral."));
						}
					} else if (cell.getColumnIndex() == 3) {

						if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {

							if (Objects.isNull(funcionarioVerba.getFuncionario())) {
								if (pensionistaVerba.getVerba() != null) {

									Verba verba = pensionistaVerba.getVerba();

									Double valorDouble = null;

									try {
										valorDouble = cell.getNumericCellValue();

									} catch (Exception e) {
										result.getErroList()
												.add(new ErroImportData(row.getRowNum() + 1,
														"A verba " + verba.getDescricaoVerba() + " de código "
																+ verba.getCodigo() + " está com o valor (" + cellValue
																+ ") fora de padrão."));
									}

									if (Objects.nonNull(valorDouble)) {
										pensionistaVerba.setParcelasPagas(valorDouble.intValue() - 1);

									} else {
										result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
												"O valor não pode ser processado."));
									}

								} else {
									result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
											"Não foi possível gravar o valor, pois a verba em questão não foi retornada."));
								}
							} else {
								if (funcionarioVerba.getVerba() != null) {

									Verba verba = funcionarioVerba.getVerba();

									Double valorDouble = null;

									try {
										valorDouble = cell.getNumericCellValue();

									} catch (Exception e) {
										result.getErroList()
												.add(new ErroImportData(row.getRowNum() + 1,
														"A verba " + verba.getDescricaoVerba() + " de código "
																+ verba.getCodigo() + " está com o valor (" + cellValue
																+ ") fora de padrão."));
									}

									if (Objects.nonNull(valorDouble)) {
										funcionarioVerba.setParcelasPagas(valorDouble.intValue() - 1);

									} else {
										result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
												"O valor não pode ser processado."));
									}

								} else {
									result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
											"Não foi possível gravar o valor, pois a verba em questão não foi retornada."));
								}
							}

						} else {
							result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
									"Valor inválido: " + cellValue + ", pois não trata-se um numeral."));
						}

					} else if (cell.getColumnIndex() == 4) {

						if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {

							if (Objects.isNull(funcionarioVerba.getFuncionario())) {
								if (pensionistaVerba.getVerba() != null) {

									Verba verba = pensionistaVerba.getVerba();

									Double valorDouble = null;

									try {
										valorDouble = cell.getNumericCellValue();

									} catch (Exception e) {
										result.getErroList()
												.add(new ErroImportData(row.getRowNum() + 1,
														"A verba " + verba.getDescricaoVerba() + " de código "
																+ verba.getCodigo() + " está com o valor (" + cellValue
																+ ") fora de padrão."));
									}

									if (Objects.nonNull(valorDouble)) {
										pensionistaVerba.setValor(valorDouble);

									} else {
										result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
												"O valor não pode ser processado."));
									}

								} else {
									result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
											"Não foi possível gravar o valor, pois a verba em questão não foi retornada."));
								}
							} else {
								if (funcionarioVerba.getVerba() != null) {

									Verba verba = funcionarioVerba.getVerba();

									Double valorDouble = null;

									try {
										valorDouble = cell.getNumericCellValue();

									} catch (Exception e) {
										result.getErroList()
												.add(new ErroImportData(row.getRowNum() + 1,
														"A verba " + verba.getDescricaoVerba() + " de código "
																+ verba.getCodigo() + " está com o valor (" + cellValue
																+ ") fora de padrão."));
									}

									if (Objects.nonNull(valorDouble)) {
										funcionarioVerba.setValor(valorDouble);

									} else {
										result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
												"O valor não pode ser processado."));
									}

								} else {
									result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
											"Não foi possível gravar o valor, pois a verba em questão não foi retornada."));
								}
							}

						} else {
							result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
									"Valor inválido: " + cellValue + ", pois não trata-se um numeral."));
						}

					} else {
						break;
					}
				}
			}

			if (Objects.isNull(funcionarioVerba.getFuncionario()))
				pensionistaVerbaList.add(pensionistaVerba);
			else
				funcionarioVerbaList.add(funcionarioVerba);

		}

		workbook.close();

		// Gerando a ImportadorLancamentoManual em caso de sucesso.
		if (result.getErroList().isEmpty()) {
			for (FuncionarioVerba funcionarioVerba : funcionarioVerbaList) {
				if (!funcionarioVerbaRepository.existsByFuncionarioAndVerbaAndAtivo(funcionarioVerba.getFuncionario(),
						funcionarioVerba.getVerba(), true)) {
					funcionarioVerba.setTipoValor(TipoValorEnum.MOEDA);
					funcionarioVerba.setRecorrencia(RecorrenciaEnum.EM_PARCELA);
					funcionarioVerba.setAtivo(true);
					funcionarioVerbaRepository.save(funcionarioVerba);
				}
			}
			for (PensionistaVerba pensionistaVerba : pensionistaVerbaList) {
				if (!pensionistaVerbaRepository.existsByPensionistaAndVerbaAndAtivo(pensionistaVerba.getPensionista(),
						pensionistaVerba.getVerba(), true)) {
					pensionistaVerba.setTipoValor(TipoValorEnum.MOEDA);
					pensionistaVerba.setRecorrencia(RecorrenciaEnum.EM_PARCELA);
					pensionistaVerba.setAtivo(true);
					pensionistaVerbaRepository.save(pensionistaVerba);
				}
			}
		}

		return result;

	}

	public ValidacaoArquivoImportacaoResponse validarArquivo(MultipartFile file, Long folhaPagamentoId)
			throws IOException, InvalidFormatException {

		FolhaPagamento folhaPagamento = fPagamentoRepository.findById(folhaPagamentoId)
				.orElseThrow(() -> new ResourceNotFoundException("FolhaPagamento", "id", folhaPagamentoId));

		if (folhaPagamento.getStatus().equals(StatusFolhaEnum.BLOQUEADO))
			throw new ServiceException("Não é possível incluir um funcionário em uma folha de pagamento bloqueada.");

		ValidacaoArquivoImportacaoResponse result = new ValidacaoArquivoImportacaoResponse();
		List<PlanilhaDados> dadosCelulaList = new ArrayList<PlanilhaDados>();

		Workbook workbook = WorkbookFactory.create(file.getInputStream());

		if (workbook.getNumberOfSheets() == 0)
			throw new BadRequestException("A planilha não possui aba para ser validada.");

		Sheet sheet = workbook.getSheetAt(0);

		DataFormatter dataFormatter = new DataFormatter();

		Map<String, Funcionario> mapFuncByMatricula = new HashMap<>();
		Map<String, Verba> mapVerbaByCodigo = new HashMap<>();

		boolean firstLoop = true;
		for (Row row : sheet) {
			if (firstLoop) {
				firstLoop = false;
				continue;
			}

			if (isRowEmpty(row))
				break;

			PlanilhaDados dadosCelula = new PlanilhaDados();

			for (Cell cell : row) {

				String cellValue = dataFormatter.formatCellValue(cell);

				// Validação Básica
				if (Objects.isNull(cellValue) || cell.getCellTypeEnum() == CellType.BLANK) {

					if (cell.getColumnIndex() == 0) {
						// Index 0 - Validação do Código Verba
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna código verba não preenchida."));

					} else if (cell.getColumnIndex() == 1) {
						// Index 1 - Validação de Matrícula
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna matrícula não preenchida."));

					} else if (cell.getColumnIndex() == 2) {
						// Index 2 - Validação de Valor
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna valor não preenchida."));
					} else {
						break;
					}

				} else {

					if (cell.getColumnIndex() == 0) {
						// Index 0 - Validação do Código Verba

						if (!mapVerbaByCodigo.containsKey(cellValue)) {
							Optional<Verba> verba = verbaRepository.findByCodigo(cellValue);
							if (verba.isPresent()) {
								Verba verbaObj = verba.get();
								mapVerbaByCodigo.put(cellValue, verbaObj);
								dadosCelula.setVerbaId(verbaObj.getId());
								dadosCelula.setVerbaCodigo(verbaObj.getCodigo());
							} else {
								result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
										"Verba não retornada para o código: " + cellValue));
							}
						} else {
							dadosCelula.setVerbaId(mapVerbaByCodigo.get(cellValue).getId());
							dadosCelula.setVerbaCodigo(mapVerbaByCodigo.get(cellValue).getCodigo());
						}

					} else if (cell.getColumnIndex() == 1) {
						// Index 1 - Validação de Matrícula

						if (!mapFuncByMatricula.containsKey(cellValue)) {
							Optional<Funcionario> funcionario = funcionarioRepository
									.findByMatricula(Integer.parseInt(cellValue));
							if (funcionario.isPresent()) {

								Funcionario func = funcionario.get();
								if (func.getFilial() != null && !func.getFilial().equals(folhaPagamento.getFilial())) {
									result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
											"Funcionário com divergência de filial com a folha de pagamento. "));
								} else {
									mapFuncByMatricula.put(cellValue, func);
									dadosCelula.setFuncionarioId(func.getId());
								}

							} else {
								result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
										"Funcionário não retornado para matrícula: " + cellValue));
							}
						} else {
							dadosCelula.setFuncionarioId(mapFuncByMatricula.get(cellValue).getId());
						}

					} else if (cell.getColumnIndex() == 2) {
						// Index 2 - Validação de Valor

						if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {

							if (dadosCelula.getVerbaCodigo() != null) {

								Verba verba = mapVerbaByCodigo.get(dadosCelula.getVerbaCodigo());

								Double valorDouble = null;

								try {
									valorDouble = cell.getNumericCellValue();

								} catch (Exception e) {
									result.getErroList()
											.add(new ErroImportData(row.getRowNum() + 1,
													"A verba " + verba.getDescricaoVerba() + " de código "
															+ verba.getCodigo() + " está com o valor (" + cellValue
															+ ") fora de padrão."));
								}

								if (Objects.nonNull(valorDouble)) {
									if (verba.getValorMaximo() != null && verba.getValorMaximo() > 0
											&& valorDouble > verba.getValorMaximo()) {
										result.getErroList()
												.add(new ErroImportData(row.getRowNum() + 1,
														"A verba " + verba.getDescricaoVerba() + " de código "
																+ verba.getCodigo()
																+ " teve o valor máximo ultrapassado."));
									} else {
										dadosCelula.setValor(valorDouble);
									}
								} else {
									result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
											"O valor não pode ser processado."));
								}

							} else {
								result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
										"Não foi possível gravar o valor, pois a verba em questão não foi retornada."));
							}

						} else {
							result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
									"Valor inválido: " + cellValue + ", pois não trata-se um numeral."));
						}

					} else {
						break;
					}
				}
			}
			dadosCelulaList.add(dadosCelula);
		}

		workbook.close();

		// Gerando a ImportadorLancamentoManual em caso de sucesso.
		if (result.getErroList().isEmpty()) {
			AnexoResponse anexo = anexoService.createAnexo(file, AnexoPastaEnum.IMPORTACAO_LANCAMENTO_MANUAL.getPasta(),
					null);

			ImportadorLancamentoManual importLancManual = new ImportadorLancamentoManual();
			importLancManual.setArquivoImportacao(new Anexo());
			importLancManual.getArquivoImportacao().setId(anexo.getId());
			importLancManual.setFolhaPagamento(folhaPagamento);
			importLancManual.setExcluido(false);
			importadorLancamentoManualRepository.save(importLancManual);

			Map<Long, List<LancamentoVerbaManualRequest>> verbaManualByFuncMap = new HashMap<>();

			for (PlanilhaDados planilhaDados : dadosCelulaList) {
				LancamentoVerbaManualRequest verbaManual = new LancamentoVerbaManualRequest();
				verbaManual.setValor(planilhaDados.getValor());
				verbaManual.setVerbaId(planilhaDados.getVerbaId());

				if (verbaManualByFuncMap.containsKey(planilhaDados.getFuncionarioId())) {
					verbaManualByFuncMap.get(planilhaDados.getFuncionarioId()).add(verbaManual);
				} else {
					List<LancamentoVerbaManualRequest> verbaManualRequestList = new ArrayList<LancamentoVerbaManualRequest>();
					verbaManualRequestList.add(verbaManual);
					verbaManualByFuncMap.put(planilhaDados.getFuncionarioId(), verbaManualRequestList);
				}

			}

			for (Map.Entry<Long, List<LancamentoVerbaManualRequest>> entry : verbaManualByFuncMap.entrySet()) {

				// Processamento dos funcionários da folha em questão
				LancamentoRequest fpContraVerbaReq = new LancamentoRequest();

				fpContraVerbaReq.setFolhaPagamentoId(folhaPagamentoId);
				fpContraVerbaReq.setFuncionariosId(new ArrayList<Long>());
				fpContraVerbaReq.getFuncionariosId().add(entry.getKey());
				fpContraVerbaReq.setVerbaManualList(new ArrayList<LancamentoVerbaManualRequest>());
				fpContraVerbaReq.getVerbaManualList().addAll(entry.getValue());

				// TODO ANALISAR
//				contrachequeService.adicionarFuncionario(fpContraVerbaReq);

			}
		}

		return result;

	}

	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellTypeEnum() != CellType.BLANK)
				return false;
		}
		return true;
	}

	public List<ImportadorLancamentoManualResponse> getAllImportadorLancamentoManuals() {
		List<ImportadorLancamentoManual> importadorLancamentoManuals = importadorLancamentoManualRepository.findAll();

		List<ImportadorLancamentoManualResponse> listImportadorLancamentoManualResponse = new ArrayList<>();
		for (ImportadorLancamentoManual importadorLancamentoManual : importadorLancamentoManuals) {
			listImportadorLancamentoManualResponse
					.add(new ImportadorLancamentoManualResponse(importadorLancamentoManual));
		}
		return listImportadorLancamentoManualResponse;
	}

	public ImportadorLancamentoManualResponse getImportadorLancamentoManualById(Long importadorLancamentoManualId) {
		ImportadorLancamentoManual importadorLancamentoManual = importadorLancamentoManualRepository
				.findById(importadorLancamentoManualId)
				.orElseThrow(() -> new ResourceNotFoundException("ImportadorLancamentoManual", "id",
						importadorLancamentoManualId));

		ImportadorLancamentoManualResponse importadorLancamentoManualResponse = new ImportadorLancamentoManualResponse(
				importadorLancamentoManual);
		Usuario criadoPor = usuarioRepository.findById(importadorLancamentoManual.getCreatedBy()).orElseThrow(
				() -> new ResourceNotFoundException("User", "id", importadorLancamentoManual.getCreatedBy()));
		importadorLancamentoManualResponse.setCriadoPor(criadoPor.getNome());

		Usuario alteradoPor = usuarioRepository.findById(importadorLancamentoManual.getUpdatedBy()).orElseThrow(
				() -> new ResourceNotFoundException("User", "id", importadorLancamentoManual.getUpdatedBy()));
		importadorLancamentoManualResponse.setAlteradoPor(alteradoPor.getNome());

		return importadorLancamentoManualResponse;
	}

	public PagedResponse<ImportadorLancamentoManualResponse> getAll(int page, int size, String order,
			Long folhaPagamentoId) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ImportadorLancamentoManual> importadorLancamentoManuals = importadorLancamentoManualRepository
				.findByFolhaPagamentoIdAndExcluido(folhaPagamentoId, false, pageable);

		if (importadorLancamentoManuals.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), importadorLancamentoManuals.getNumber(),
					importadorLancamentoManuals.getSize(), importadorLancamentoManuals.getTotalElements(),
					importadorLancamentoManuals.getTotalPages(), importadorLancamentoManuals.isLast());
		}

		List<ImportadorLancamentoManualResponse> importadorLancamentoManualResponses = importadorLancamentoManuals
				.map(importadorLancamentoManual -> {
					return new ImportadorLancamentoManualResponse(importadorLancamentoManual);
				}).getContent();
		return new PagedResponse<>(importadorLancamentoManualResponses, importadorLancamentoManuals.getNumber(),
				importadorLancamentoManuals.getSize(), importadorLancamentoManuals.getTotalElements(),
				importadorLancamentoManuals.getTotalPages(), importadorLancamentoManuals.isLast());

	}

	public void deleteImportadorLancamentoManual(Long id) {
		ImportadorLancamentoManual importadorLancamentoManual = importadorLancamentoManualRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ImportadorLancamentoManual", "id", id));
		importadorLancamentoManual.setExcluido(true);
		importadorLancamentoManualRepository.save(importadorLancamentoManual);
	}

}

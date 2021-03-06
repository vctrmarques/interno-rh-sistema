package com.rhlinkcon.service;

import java.io.IOException;
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
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.AnexoPastaEnum;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.FuncionarioVerba;
import com.rhlinkcon.model.ImportadorVerbasFuncionario;
import com.rhlinkcon.model.RecorrenciaEnum;
import com.rhlinkcon.model.TipoValorEnum;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.importadorLancamentoManual.ErroImportData;
import com.rhlinkcon.payload.importadorLancamentoManual.ValidacaoArquivoImportacaoResponse;
import com.rhlinkcon.payload.importadorVerbasFuncionario.ImportadorVerbasFuncionarioResponse;
import com.rhlinkcon.payload.importadorVerbasFuncionario.PlanilhaDadosImportVerbaFunc;
import com.rhlinkcon.repository.FuncionarioVerbaRepository;
import com.rhlinkcon.repository.ImportadorVerbasFuncionarioRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class ImportadorVerbasFuncionarioService {

	@Autowired
	private ImportadorVerbasFuncionarioRepository importadorVerbaFuncionarioRepository;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private VerbaRepository verbaRepository;
	@Autowired
	private AnexoService anexoService;
	@Autowired
	private FuncionarioVerbaRepository funcionarioVerbaRepository;

	public ValidacaoArquivoImportacaoResponse validarArquivo(MultipartFile file)
			throws IOException, InvalidFormatException {

		ValidacaoArquivoImportacaoResponse result = new ValidacaoArquivoImportacaoResponse();
		List<PlanilhaDadosImportVerbaFunc> dadosCelulaList = new ArrayList<PlanilhaDadosImportVerbaFunc>();

		Workbook workbook = WorkbookFactory.create(file.getInputStream());

		if (workbook.getNumberOfSheets() == 0)
			throw new BadRequestException("A planilha n??o possui aba para ser validada.");

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

			PlanilhaDadosImportVerbaFunc dadosCelula = new PlanilhaDadosImportVerbaFunc();

			for (Cell cell : row) {

				String cellValue = dataFormatter.formatCellValue(cell);

				// Valida????o B??sica
				if (Objects.isNull(cellValue) || cell.getCellTypeEnum() == CellType.BLANK) {

					if (cell.getColumnIndex() == 0) {
						// Index 0 - Valida????o de Matr??cula
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna Matr??cula n??o preenchida."));

					} else if (cell.getColumnIndex() == 1) {
						// Index 1 - Valida????o do C??digo Verba
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna C??digo Verba n??o preenchida."));

					} else if (cell.getColumnIndex() == 2) {
						// Index 2 - Valida????o de Tipo
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna Tipo n??o preenchida."));

					} else if (cell.getColumnIndex() == 3) {
						// Index 3 - Valida????o de Recorr??ncia
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna Recorr??ncia n??o preenchida."));

					} else if (cell.getColumnIndex() == 4) {
						// Index 4 - Valida????o de Parcela
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna Parcela n??o preenchida."));

					} else if (cell.getColumnIndex() == 5) {
						// Index 5 - Valida????o de Valor
						result.getErroList()
								.add(new ErroImportData(row.getRowNum() + 1, "Coluna Valor n??o preenchida."));

					} else {
						break;
					}

				} else {

					if (cell.getColumnIndex() == 0) {
						// Index 0 - Valida????o de Matr??cula

						if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {

							if (!mapFuncByMatricula.containsKey(cellValue)) {
								Optional<Funcionario> funcionario = funcionarioRepository
										.findByMatricula(Integer.parseInt(cellValue));
								if (funcionario.isPresent()) {
									Funcionario func = funcionario.get();
									mapFuncByMatricula.put(cellValue, func);
									dadosCelula.setFuncionarioId(func.getId());
								} else {
									result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
											"Funcion??rio n??o retornado para matr??cula: " + cellValue));
								}
							} else {
								dadosCelula.setFuncionarioId(mapFuncByMatricula.get(cellValue).getId());
							}

						} else {
							result.getErroList().add(
									new ErroImportData(row.getRowNum() + 1, "Funcion??rio n??o retornado para matr??cula: "
											+ cellValue + ", pois n??o trata-se de um numeral."));
						}

					} else if (cell.getColumnIndex() == 1) {
						// Index 1 - Valida????o do C??digo Verba

						if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
							if (!mapVerbaByCodigo.containsKey(cellValue)) {
								Optional<Verba> verba = verbaRepository.findByCodigo(cellValue);
								if (verba.isPresent()) {
									Verba verbaObj = verba.get();
									mapVerbaByCodigo.put(cellValue, verbaObj);
									dadosCelula.setVerbaId(verbaObj.getId());
									dadosCelula.setVerbaCodigo(verbaObj.getCodigo());
								} else {
									result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
											"Verba n??o retornada para o c??digo: " + cellValue));
								}
							} else {
								dadosCelula.setVerbaCodigo(mapVerbaByCodigo.get(cellValue).getCodigo());
								dadosCelula.setVerbaId(mapVerbaByCodigo.get(cellValue).getId());
							}
						} else {
							result.getErroList()
									.add(new ErroImportData(row.getRowNum() + 1, "Verba n??o retornada para o c??digo: "
											+ cellValue + ", pois n??o trata-se de um numeral."));
						}

					} else if (cell.getColumnIndex() == 2) {
						// Index 2 - Valida????o de Tipo

						if (cell.getCellTypeEnum().equals(CellType.STRING)) {

							if (cellValue.equals("Moeda") || cellValue.equals("moeda")) {
								dadosCelula.setTipo(TipoValorEnum.MOEDA);
							} else if (cellValue.equals("Percentual") || cellValue.equals("percentual")) {
								dadosCelula.setTipo(TipoValorEnum.PERCENTUAL);
							} else if (cellValue.equals("Quantidade") || cellValue.equals("quantidade")) {
								dadosCelula.setTipo(TipoValorEnum.QUANTIDADE);
							} else {
								result.getErroList()
										.add(new ErroImportData(row.getRowNum() + 1,
												"Tipo n??o retornada para o valor: " + cellValue
														+ ". Favor seguir o padr??o (Moeda, Percentual ou Quantidade)"));
							}

						} else {
							result.getErroList().add(
									new ErroImportData(row.getRowNum() + 1, "Recorr??ncia n??o retornada para o valor: "
											+ cellValue + ", pois n??o trata-se um texto."));
						}

					} else if (cell.getColumnIndex() == 3) {
						// Index 3 - Valida????o de Recorr??ncia

						if (cell.getCellTypeEnum().equals(CellType.STRING)) {

							if (cellValue.equals("Fixa") || cellValue.equals("fixa")) {
								dadosCelula.setRecorrencia(RecorrenciaEnum.FIXA);
							} else if (cellValue.equals("Parcelada") || cellValue.equals("parcelada")) {
								dadosCelula.setRecorrencia(RecorrenciaEnum.EM_PARCELA);
							} else {
								result.getErroList()
										.add(new ErroImportData(row.getRowNum() + 1,
												"Recorr??ncia n??o retornada para o valor: " + cellValue
														+ ". Favor seguir o padr??o (Fixa ou Parcelada)"));
							}

						} else {
							result.getErroList().add(
									new ErroImportData(row.getRowNum() + 1, "Recorr??ncia n??o retornada para o valor: "
											+ cellValue + ", pois n??o trata-se um texto."));
						}

					} else if (cell.getColumnIndex() == 4) {
						// Index 4 - Valida????o de Parcela

						if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {

							// Validando parcelas de acordo com a recorrencia.
							if (dadosCelula.getRecorrencia() != null) {

								if (dadosCelula.getRecorrencia().equals(RecorrenciaEnum.FIXA)) {
									if (Integer.valueOf(cellValue) != 0) {
										result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
												"Parcela incorreta para a recorr??ncia selecionada."));
									} else {
										dadosCelula.setParcela(Integer.valueOf(cellValue));
									}

								} else {
									if (Integer.valueOf(cellValue) == 0) {
										result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
												"Parcela incorreta para a recorr??ncia selecionada."));
									} else {
										dadosCelula.setParcela(Integer.valueOf(cellValue));
									}
								}

							} else {
								dadosCelula.setParcela(Integer.valueOf(cellValue));
							}

						} else {
							result.getErroList()
									.add(new ErroImportData(row.getRowNum() + 1, "Parcela n??o retornada para o valor: "
											+ cellValue + ", pois n??o trata-se um numeral."));
						}

					} else if (cell.getColumnIndex() == 5) {
						// Index 5 - Valida????o de Valor

						if (cell.getCellTypeEnum().equals(CellType.NUMERIC)) {

							if (dadosCelula.getVerbaCodigo() != null) {

								Verba verba = mapVerbaByCodigo.get(dadosCelula.getVerbaCodigo());

								Double valorDouble = null;

								try {
									valorDouble = cell.getNumericCellValue();

								} catch (Exception e) {
									result.getErroList()
											.add(new ErroImportData(row.getRowNum() + 1,
													"A verba " + verba.getDescricaoVerba() + " de c??digo "
															+ verba.getCodigo() + " est?? com o valor (" + cellValue
															+ ") fora de padr??o."));
								}

								if (Objects.nonNull(valorDouble)) {
									if (verba.getValorMaximo() != null && verba.getValorMaximo() > 0
											&& valorDouble > verba.getValorMaximo()) {
										result.getErroList()
												.add(new ErroImportData(row.getRowNum() + 1,
														"A verba " + verba.getDescricaoVerba() + " de c??digo "
																+ verba.getCodigo()
																+ " teve o valor m??ximo ultrapassado."));
									} else {
										dadosCelula.setValor(valorDouble);
									}

								} else {
									result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
											"O valor n??o pode ser processado."));
								}

							} else {
								result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
										"N??o foi poss??vel gravar o valor, pois a verba em quest??o n??o foi retornada."));
							}

						} else {
							result.getErroList().add(new ErroImportData(row.getRowNum() + 1,
									"Valor inv??lido: " + cellValue + ", pois n??o trata-se um numeral."));
						}

					} else {
						break;
					}
				}
			}
			dadosCelulaList.add(dadosCelula);
		}

		workbook.close();

		// Gerando a ImportadorVerbasFuncionario em caso de sucesso.
		if (result.getErroList().isEmpty()) {
			AnexoResponse anexo = anexoService.createAnexo(file, AnexoPastaEnum.IMPORTACAO_VERBA_FUNCIONARIO.getPasta(),
					null);

			ImportadorVerbasFuncionario importVerbaFunc = new ImportadorVerbasFuncionario();
			importVerbaFunc.setArquivoImportacao(new Anexo());
			importVerbaFunc.getArquivoImportacao().setId(anexo.getId());
			importVerbaFunc.setExcluido(false);
			importadorVerbaFuncionarioRepository.save(importVerbaFunc);

			for (PlanilhaDadosImportVerbaFunc planilhaDados : dadosCelulaList) {

				funcionarioVerbaRepository.deleteByFuncionarioIdAndVerbaId(planilhaDados.getFuncionarioId(),
						planilhaDados.getVerbaId());

				FuncionarioVerba entity = new FuncionarioVerba();

				// Set Funcion??rio
				entity.setFuncionario(new Funcionario());
				entity.getFuncionario().setId(planilhaDados.getFuncionarioId());

				// Set Verba
				entity.setVerba(new Verba());
				entity.getVerba().setId(planilhaDados.getVerbaId());

				// Set demais valores
				entity.setTipoValor(planilhaDados.getTipo());
				entity.setRecorrencia(planilhaDados.getRecorrencia());
				entity.setParcelas(planilhaDados.getParcela());
				entity.setValor(planilhaDados.getValor());

				funcionarioVerbaRepository.save(entity);

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

	public void deleteImportadorVerbasFuncionario(Long id) {
		ImportadorVerbasFuncionario importadorVerbaFuncionario = importadorVerbaFuncionarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ImportadorVerbasFuncionario", "id", id));
		importadorVerbaFuncionario.setExcluido(true);
		importadorVerbaFuncionarioRepository.save(importadorVerbaFuncionario);
	}

	public PagedResponse<ImportadorVerbasFuncionarioResponse> getAll(int page, int size, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// ordena????o
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<ImportadorVerbasFuncionario> importadorVerbasFuncionario = importadorVerbaFuncionarioRepository
				.findByExcluido(false, pageable);

		if (importadorVerbasFuncionario.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), importadorVerbasFuncionario.getNumber(),
					importadorVerbasFuncionario.getSize(), importadorVerbasFuncionario.getTotalElements(),
					importadorVerbasFuncionario.getTotalPages(), importadorVerbasFuncionario.isLast());
		}

		List<ImportadorVerbasFuncionarioResponse> responses = importadorVerbasFuncionario.map(row -> {
			return new ImportadorVerbasFuncionarioResponse(row);
		}).getContent();
		return new PagedResponse<>(responses, importadorVerbasFuncionario.getNumber(),
				importadorVerbasFuncionario.getSize(), importadorVerbasFuncionario.getTotalElements(),
				importadorVerbasFuncionario.getTotalPages(), importadorVerbasFuncionario.isLast());
	}

}

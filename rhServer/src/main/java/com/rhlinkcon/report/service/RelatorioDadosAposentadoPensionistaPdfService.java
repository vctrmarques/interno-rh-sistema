package com.rhlinkcon.report.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.jrimum.utilix.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.rhlinkcon.filtro.RelatorioDadosAposentadoPensionistaFiltro;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.GeneroEnum;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.payload.relatorioDadosAposentadoPensionista.RelatorioDadosAposentadoPensionistaDto;
import com.rhlinkcon.service.RelatorioDadosAposentadoPensionistaService;
import com.rhlinkcon.util.Constantes;
import com.rhlinkcon.util.Utils;

@Service
public class RelatorioDadosAposentadoPensionistaPdfService implements CrudPdfReportService<RelatorioDadosAposentadoPensionistaFiltro> {
	
	private static final String TITULO = "Relatório dados de aposentadorias e pensões";
	private static final String SUBTITULO = "Relatório baseado nos dados de aposentados e pensionistas";
	
	private static final String DATEFORMAT = "dd/MM/yyyy";
	
	@Autowired
	private RelatorioDadosAposentadoPensionistaService service;
	
	@Override
	public void personalizarDocumento(PdfWriter writer, Document document, RelatorioDadosAposentadoPensionistaFiltro valor) throws DocumentException {
		
		addAtributos(document, TITULO, SUBTITULO);
		
		RelatorioDadosAposentadoPensionistaDto info = service.get(valor);
		
		if(valor.getSituacao().equals("APOSENTADO")) {
			if(Collections.isNotEmpty(info.getAposentados())) {
				for(Funcionario obj : info.getAposentados()) {
					criarCabecalho(document, valor.getSituacao());
					mountPaginaAposentado(document, valor, obj);
					document.newPage();
				}
			}
		} else {
			if(Collections.isNotEmpty(info.getPensionistas())) {
				for(Pensionista obj : info.getPensionistas()) {
					criarCabecalho(document, valor.getSituacao());
					mountPaginaPensionista(document, valor, obj);
					document.newPage();
				}
			}
		}
		
	}
	
	/*
	 * Monta a página relacionada ao pensionista
	 * */

	private void mountPaginaPensionista(Document document, RelatorioDadosAposentadoPensionistaFiltro valor, Pensionista obj) throws DocumentException {
		if(Collections.isNotEmpty(valor.getAtributosDadoPessoalPensionista())) {
			document.add(getTitulo("Dados pessoal"));
			document.add(dadoPessoalPensionista(valor, obj));
		}
		
		if(Collections.isNotEmpty(valor.getAtributosDadoExSeguradoPensionista())) {
			document.add(getTitulo("Dados ex-segurado"));
			document.add(dadoExSeguradoPensionista(valor, obj));
		}
		
		if(Collections.isNotEmpty(valor.getAtributosDadoDependentePensionista())) {
			document.add(getTitulo("Dados dependente"));
			document.add(dadoDependentePensionista(valor, obj));
		}
		
		if(Collections.isNotEmpty(valor.getAtributosDocPessoalPensionista())) {
			document.add(getTitulo("Documentação"));
			document.add(documentacaoPensionista(valor, obj));
		}
		
		if(Collections.isNotEmpty(valor.getAtributosContatoEnderecoPensionista())) {
			document.add(getTitulo("Contato e endereço"));
			document.add(contatoEnderecoPensionista(valor, obj));
		}
		
		if(Collections.isNotEmpty(valor.getAtributosDadoBeneficioPensionista()) && Objects.nonNull(obj.getPensaoPagamento())) {
			document.add(getTitulo("Benefício"));
			document.add(dadoBeneficioPensionista(valor, obj));
		}
		
		if(Collections.isNotEmpty(valor.getAtributosDadoContaCreditoPensionista()) && Objects.nonNull(obj.getPensaoPagamento())) {
			document.add(getTitulo("Dados de pagamento"));
			document.add(dadoContaCreditoPensionista(valor, obj));
		}
		
		if(Collections.isNotEmpty(valor.getAtributosDadoIsencaoPensionista()) && Objects.nonNull(obj.getPensaoPagamento())) {
			document.add(getTitulo("Isenção"));
			document.add(dadoIsencaoPensionista(valor, obj));
		}
	}

	private PdfPTable dadoIsencaoPensionista(RelatorioDadosAposentadoPensionistaFiltro valor, Pensionista obj) {
		List<PdfPTable> tables = new ArrayList<>();
		
		// TODO REFATORAR.
//		if(contem(valor.getAtributosDadoIsencaoPensionista(), "CONDICOES"))
//			tables.add(mountTable("Condições",  Objects.nonNull(obj.getPensaoPagamento().getCondicaoIsencao()) ? obj.getPensaoPagamento().getCondicaoIsencao().getLabel() : " "));
//		if(contem(valor.getAtributosDadoIsencaoPensionista(), "DATAINICIAL"))
//			tables.add(mountTable("Data inicial", data(DATEFORMAT, obj.getPensaoPagamento().getDataInicioIsencao())));
//		if(contem(valor.getAtributosDadoIsencaoPensionista(), "DATAFINAL"))
//			tables.add(mountTable("Data final", data(DATEFORMAT, obj.getPensaoPagamento().getDataFimIsencao())));
		
		return mountTable(tables);
	}

	private PdfPTable dadoContaCreditoPensionista(RelatorioDadosAposentadoPensionistaFiltro valor, Pensionista obj) {
		List<PdfPTable> tables = new ArrayList<>();
		
		if(contem(valor.getAtributosDadoContaCreditoPensionista(), "BANCO"))
			tables.add(mountTable("Banco",  Objects.nonNull(obj.getPensaoPagamento().getAgencia()) ? obj.getPensaoPagamento().getAgencia().getBanco().getCodigo() : " "));
		if(contem(valor.getAtributosDadoContaCreditoPensionista(), "AGENCIA"))
			tables.add(mountTable("Agência", Objects.nonNull(obj.getPensaoPagamento().getAgencia()) ? check(obj.getPensaoPagamento().getAgencia().getNumero(), false) : " "));
		if(contem(valor.getAtributosDadoContaCreditoPensionista(), "TIPOCONTA"))
			tables.add(mountTable("Tipo de conta", Objects.nonNull(obj.getPensaoPagamento().getTipoConta()) ? obj.getPensaoPagamento().getTipoConta().getLabel() : " "));
		if(contem(valor.getAtributosDadoContaCreditoPensionista(), "NUMCONTA"))
			tables.add(mountTable("Nº da Conta", check(obj.getPensaoPagamento().getNumeroConta(), false)));
		if(contem(valor.getAtributosDadoContaCreditoPensionista(), "DIGITO"))
			tables.add(mountTable("Digito", check(obj.getPensaoPagamento().getDigito(), false)));
		if(contem(valor.getAtributosDadoContaCreditoPensionista(), "OPERACAO"))
			tables.add(mountTable("Operação", check(obj.getPensaoPagamento().getOperacao(), false)));
		
		return mountTable(tables);
	}

	private PdfPTable dadoBeneficioPensionista(RelatorioDadosAposentadoPensionistaFiltro valor, Pensionista obj) {
		List<PdfPTable> tables = new ArrayList<>();
		
		if(contem(valor.getAtributosDadoBeneficioPensionista(), "PRIMEIROPAGAMENTO"))
			tables.add(mountTable("Primeiro pagamento", data(DATEFORMAT, obj.getPensaoPagamento().getDataPrimeiroPagamento())));
		if(contem(valor.getAtributosDadoBeneficioPensionista(), "TIPORATEIO"))
			tables.add(mountTable("Tipo de rateio", Objects.nonNull(obj.getPensaoPagamento().getTipoRateio()) ? obj.getPensaoPagamento().getTipoRateio().getLabel() : " "));
		if(contem(valor.getAtributosDadoBeneficioPensionista(), "TIPOPENSAO"))
			tables.add(mountTable("Tipo de pensão", Objects.nonNull(obj.getPensaoPagamento().getTipoPensao()) ? obj.getPensaoPagamento().getTipoPensao().getLabel() : " "));
		if(contem(valor.getAtributosDadoBeneficioPensionista(), "COMPARIDADE"))
			tables.add(mountTable("Com paridade", Objects.nonNull(obj.getPensaoPagamento().getComParidade()) ? "Sim" : "Não"));
		if(contem(valor.getAtributosDadoBeneficioPensionista(), "NUMPROCESSO"))
			tables.add(mountTable("Nº do Processo", check(obj.getPensaoPagamento().getNumeroProcessoPensao(), false)));
		if(contem(valor.getAtributosDadoBeneficioPensionista(), "FIMBENEFICIO"))
			tables.add(mountTable("Fim benefício", data(DATEFORMAT, obj.getPensaoPagamento().getDataFimBeneficio())));
		if(contem(valor.getAtributosDadoBeneficioPensionista(), "LIMITERETROATIVO"))
			tables.add(mountTable("Limite retroativo", data(DATEFORMAT, obj.getPensaoPagamento().getDataLimiteRetroativo())));
		if(contem(valor.getAtributosDadoBeneficioPensionista(), "TIPOCOTA"))
			tables.add(mountTable("Tipo de cota", Objects.nonNull(obj.getPensaoPagamento().getTipoCota()) ? obj.getPensaoPagamento().getTipoCota().getLabel() : " "));
		if(contem(valor.getAtributosDadoBeneficioPensionista(), "NUMRESERVA"))
			tables.add(mountTable("Nº Reserva", check(obj.getPensaoPagamento().getNumeroReserva(), false)));
		
		return mountTable(tables);
	}

	private PdfPTable contatoEnderecoPensionista(RelatorioDadosAposentadoPensionistaFiltro valor, Pensionista obj) {
		List<PdfPTable> tables = new ArrayList<>();
		
		if(contem(valor.getAtributosContatoEnderecoPensionista(), "LOGRADOURO"))
			tables.add(mountTable("Logradouro", check(obj.getLogradouro(), false)));
		if(contem(valor.getAtributosContatoEnderecoPensionista(), "NUMERO"))
			tables.add(mountTable("Número", check(obj.getNumeroLogradouro(), false)));
		if(contem(valor.getAtributosContatoEnderecoPensionista(), "COMPLEMENTO"))
			tables.add(mountTable("Complemento", check(obj.getComplementoLogradouro(), false)));
		if(contem(valor.getAtributosContatoEnderecoPensionista(), "UF"))
			tables.add(mountTable("UF", Objects.nonNull(obj.getMunicipio()) ? obj.getMunicipio().getUf().getEstado() : " "));
		if(contem(valor.getAtributosContatoEnderecoPensionista(), "MUNICIPIO"))
			tables.add(mountTable("Município", Objects.nonNull(obj.getMunicipio()) ? obj.getMunicipio().getDescricao() : " "));
		if(contem(valor.getAtributosContatoEnderecoPensionista(), "BAIRRO"))
			tables.add(mountTable("Bairro", check(obj.getBairro(), false)));
		if(contem(valor.getAtributosContatoEnderecoPensionista(), "CEP"))
			tables.add(mountTable("CEP", check(obj.getCep(), false)));
		if(contem(valor.getAtributosContatoEnderecoPensionista(), "EMAILPESSOAL"))
			tables.add(mountTable("E-mail pessoal", check(obj.getEmailPessoal(), false)));
		if(contem(valor.getAtributosContatoEnderecoPensionista(), "TELEFONEPRINCIPAL"))
			tables.add(mountTable("Telefone principal", check(obj.getTelefoneFixo(), false)));
		if(contem(valor.getAtributosContatoEnderecoPensionista(), "TELEFONEOPCIONAL"))
			tables.add(mountTable("Telefone opicional", check(obj.getCelular(), false)));
		
		return mountTable(tables);
	}

	private PdfPTable documentacaoPensionista(RelatorioDadosAposentadoPensionistaFiltro valor, Pensionista obj) {
		List<PdfPTable> tables = new ArrayList<>();

		if(contem(valor.getAtributosDocPessoalPensionista(), "CPF"))
			tables.add(mountTable("CPF", check(Utils.formatarCpf(obj.getCpf()), false)));
		if(contem(valor.getAtributosDocPessoalPensionista(), "IDENTIDADE"))
			tables.add(mountTable("Identidade", check(obj.getIdentidade(), false)));
		if(contem(valor.getAtributosDocPessoalPensionista(), "DATAEXPEDICAOIDENTIDADE"))
			tables.add(mountTable("Data expedição RG", data(DATEFORMAT, obj.getDataExpedicaoRg())));
		if(contem(valor.getAtributosDocPessoalPensionista(), "NUMEROTITULOELEITOR"))
			tables.add(mountTable("Nº Título eleitor", check(obj.getTituloEleitor(), false)));
		
		
		return mountTable(tables);
	}
	
	private PdfPTable dadoDependentePensionista(RelatorioDadosAposentadoPensionistaFiltro valor, Pensionista obj) {
		List<PdfPTable> tables = new ArrayList<>();

		if(contem(valor.getAtributosDadoDependentePensionista(), "FAMILIA"))
			tables.add(mountTable("Família", Objects.nonNull(obj.getTipoFamilia()) ? obj.getTipoFamilia().getLabel() : " "));
		if(contem(valor.getAtributosDadoDependentePensionista(), "CPFRESPONSAVEL"))
			tables.add(mountTable("CPF responsável", check(Objects.nonNull(obj.getResponsavelLegal()) ? Utils.formatarCpf(obj.getResponsavelLegal().getCpf()) : " ", false)));
		if(contem(valor.getAtributosDadoDependentePensionista(), "DATAINICIO"))
			tables.add(mountTable("Data de início", data(DATEFORMAT, obj.getDataInicioResponsavel())));
		if(contem(valor.getAtributosDadoDependentePensionista(), "GRAUPARENTESCO"))
			tables.add(mountTable("Grau de parentesco", Objects.nonNull(obj.getGrauParentesco()) ? obj.getGrauParentesco().getLabel() : " "));
		if(contem(valor.getAtributosDadoDependentePensionista(), "RESPONSAVEL"))
			tables.add(mountTable("Responsável", check(Objects.nonNull(obj.getResponsavelLegal()) ? obj.getResponsavelLegal().getNome() : " ", false)));
		if(contem(valor.getAtributosDadoDependentePensionista(), "DATAVENCIMENTO"))
			tables.add(mountTable("Data de vencimento", data(DATEFORMAT, obj.getDataVencimentoResponsavel())));
		if(contem(valor.getAtributosDadoDependentePensionista(), "MOTIVO"))
			tables.add(mountTable("Motivo", Objects.nonNull(obj.getMotivo()) ? obj.getMotivo().getLabel() : " "));
		if(contem(valor.getAtributosDadoDependentePensionista(), "NUMPROCESSO"))
			tables.add(mountTable("Nº Processo", check(obj.getNumeroProcessoResponsavel(), false)));
		
		return mountTable(tables);
	}

	private PdfPTable dadoExSeguradoPensionista(RelatorioDadosAposentadoPensionistaFiltro valor, Pensionista obj) {
		List<PdfPTable> tables = new ArrayList<>();

		if(contem(valor.getAtributosDadoExSeguradoPensionista(), "MATRICULA"))
			tables.add(mountTable("Matrícula", check(obj.getFuncionario().getMatricula(), false)));
		if(contem(valor.getAtributosDadoExSeguradoPensionista(), "NOME"))
			tables.add(mountTable("Nome", check(obj.getFuncionario().getNome(), false)));
		if(contem(valor.getAtributosDadoExSeguradoPensionista(), "CPF"))
			tables.add(mountTable("CPF", check(Utils.formatarCpf(obj.getFuncionario().getCpf()), false)));
		if(contem(valor.getAtributosDadoExSeguradoPensionista(), "IDENTIDADE"))
			tables.add(mountTable("Identidade", check(obj.getFuncionario().getIdentidade(), false)));
		if(contem(valor.getAtributosDadoExSeguradoPensionista(), "DATAEXPEDICAOIDENTIDADE"))
			tables.add(mountTable("Data expedição RG", data(DATEFORMAT, obj.getDataExpedicaoRg())));
		if(contem(valor.getAtributosDadoExSeguradoPensionista(), "DATAOBITO") && Objects.nonNull(obj.getFuncionario().getDadoCadastral()))
			tables.add(mountTable("Data do óbito", data(DATEFORMAT, obj.getFuncionario().getDadoCadastral().getDataFalecimento())));
		
		return mountTable(tables);
	}

	private PdfPTable dadoPessoalPensionista(RelatorioDadosAposentadoPensionistaFiltro valor, Pensionista obj) {
		List<PdfPTable> tables = new ArrayList<>();

		if(contem(valor.getAtributosDadoPessoalPensionista(), "MATRICULA"))
			tables.add(mountTable("Matrícula", check(obj.getMatricula(), false)));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "NOME"))
			tables.add(mountTable("Nome", check(obj.getNome(), false)));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "GENERO"))
			tables.add(mountTable("Gênero", Objects.nonNull(obj.getGenero()) ? obj.getGenero().getLabel() : " "));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "DATANASCIMENTO"))
			tables.add(mountTable("Data de Nascimento", data(DATEFORMAT, obj.getDataNascimento())));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "ESTADOCIVIL"))
			tables.add(mountTable("Estado Civil", Objects.nonNull(obj.getEstadoCivil()) ? obj.getEstadoCivil().getLabel() : " "));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "GRAUINSTRUCAO"))
			tables.add(mountTable("Grau de instrução", Objects.nonNull(obj.getGrauInstrucao()) ? obj.getGrauInstrucao().getLabel() : " "));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "NATURALIDADE"))
			tables.add(mountTable("Naturalidade", Objects.nonNull(obj.getNaturalidade()) ? obj.getNaturalidade().getDescricao() : " "));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "NACIONALIDADE"))
			tables.add(mountTable("Nacionalidade", Objects.nonNull(obj.getNacionalidade()) ? obj.getNacionalidade().getNacionalidadeFeminina() : " "));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "CORPELE"))
			tables.add(mountTable("Cor da pele", check(obj.getCorPele() != null ? obj.getCorPele().getLabel() : " ", false)));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "TIPOSANGUINEO"))
			tables.add(mountTable("Tipo sanguíneo", check(obj.getTipoSanguineo(), false)));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "NOMEPAI"))
			tables.add(mountTable("Nome pai", check(obj.getNomePai(), false)));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "NOMEMAE"))
			tables.add(mountTable("Nome mãe", check(obj.getNomeMae(), false)));
		if(contem(valor.getAtributosDadoPessoalPensionista(), "STATUS"))
			tables.add(mountTable("Status", check(obj.isStatus() ? "Ativo" : "Inativo", false)));
		
		return mountTable(tables);
	}
	
	/*
	 * Monta a página relacionada ao aposentado
	 * */

	private void mountPaginaAposentado(Document document, RelatorioDadosAposentadoPensionistaFiltro valor, Funcionario obj) throws DocumentException {
		if(Collections.isNotEmpty(valor.getAtributosDadoPessoalAposentado()) || Collections.isNotEmpty(valor.getAtributosDadoProfissionalAposentado())) {
			document.add(getTitulo("Dados cadastrais"));
			document.add(dadoPessoalAposentado(valor, obj));
		}
		
		if(Collections.isNotEmpty(valor.getAtributosDocPessoalAposentado()) || Collections.isNotEmpty(valor.getAtributosDocNomeacaoAposentado())) {
			document.add(getTitulo("Documentação"));
			document.add(documentacaoAposentado(valor, obj));
		}
		
		if(Collections.isNotEmpty(valor.getAtributosContatoEnderecoAposentado())) {
			document.add(getTitulo("Contato e endereço"));
			document.add(contatoEnderecoAposentado(valor, obj));
		}
		
		if(Collections.isNotEmpty(valor.getAtributosBancoPessoalAposentado()) || Collections.isNotEmpty(valor.getAtributosDadoBancarioAposentado())) {
			document.add(getTitulo("Dados de pagamento"));
			document.add(dadosPagamentoAposentado(valor, obj));
		}
	}
	
	private PdfPTable dadosPagamentoAposentado(RelatorioDadosAposentadoPensionistaFiltro valor, Funcionario obj) {
		List<PdfPTable> tables = new ArrayList<>();
		
		if(contem(valor.getAtributosBancoPessoalAposentado(), "JORNADA"))
			tables.add(mountTable("Jornada de trabalho", Objects.nonNull(obj.getJornadaTrabalho()) ? obj.getJornadaTrabalho().getCodigo() : " "));
		if(contem(valor.getAtributosBancoPessoalAposentado(), "VINCULO"))
			tables.add(mountTable("Vínculo", Objects.nonNull(obj.getVinculo()) ? obj.getVinculo().getDescricao() : " "));
		if(contem(valor.getAtributosBancoPessoalAposentado(), "DATAADMISSAO"))
			tables.add(mountTable("Data de admissão", Objects.nonNull(obj.getDataAdmissao()) ? data(DATEFORMAT, obj.getDataAdmissao()) : " "));
		if(contem(valor.getAtributosBancoPessoalAposentado(), "DEPENDENTESIR"))
			tables.add(mountTable("Nº dependentes IR", check(obj.getNumeroDependentesImpostoRenda(), false)));
		if(contem(valor.getAtributosBancoPessoalAposentado(), "DEPENDENTESSR"))
			tables.add(mountTable("Nº dependentes SR", check(obj.getNumeroDependentesSalarioFamilia(), false)));
		if(contem(valor.getAtributosBancoPessoalAposentado(), "CARGO"))
			tables.add(mountTable("Cargo", Objects.nonNull(obj.getCargo()) ? obj.getCargo().getNome() : " "));
		if(contem(valor.getAtributosBancoPessoalAposentado(), "NIVELSALARIAL"))
			tables.add(mountTable("Nível salarial", Objects.nonNull(obj.getFaixaSalarialCargo()) ? obj.getFaixaSalarialCargo().getClasseSalarial().getNome() : " "));
		if(contem(valor.getAtributosBancoPessoalAposentado(), "REFERENCIASALARIAL"))
			tables.add(mountTable("Referencia salarial", Objects.nonNull(obj.getReferenciaSalarialCargo()) ? obj.getReferenciaSalarialCargo().getDescricao() : " "));
		
		if(contem(valor.getAtributosDadoBancarioAposentado(), "TIPOCONTA"))
			tables.add(mountTable("Tipo de conta", Objects.nonNull(obj.getTipoConta()) ? obj.getTipoConta().getLabel() : " "));
		if(contem(valor.getAtributosDadoBancarioAposentado(), "BANCO"))
			tables.add(mountTable("Banco", Objects.nonNull(obj.getAgenciaBancaria()) ? obj.getAgenciaBancaria().getBanco().getCodigo(): " "));
		if(contem(valor.getAtributosDadoBancarioAposentado(), "AGENCIA"))
			tables.add(mountTable("Agência", Objects.nonNull(obj.getAgenciaBancaria()) ? obj.getAgenciaBancaria().getNumero().toString() + "-" + obj.getAgenciaBancaria().getDigito().toString() : " "));
		if(contem(valor.getAtributosDadoBancarioAposentado(), "NUMEROCONTA"))
			tables.add(mountTable("Nº da conta", check(obj.getNumeroConta(), false)));
		if(contem(valor.getAtributosDadoBancarioAposentado(), "DIGITOCONTA"))
			tables.add(mountTable("Digito", check(obj.getDigitoConta(), false)));
		if(contem(valor.getAtributosDadoBancarioAposentado(), "OPERAÇÃO"))
			tables.add(mountTable("Operação", check(obj.getOperacao(), false)));
		
		
		return mountTable(tables);
	}

	private PdfPTable contatoEnderecoAposentado(RelatorioDadosAposentadoPensionistaFiltro valor, Funcionario obj) {
		List<PdfPTable> tables = new ArrayList<>();
		
		if(contem(valor.getAtributosContatoEnderecoAposentado(), "LOGRADOURO"))
			tables.add(mountTable("Logradouro", check(obj.getLogradouro(), false)));
		if(contem(valor.getAtributosContatoEnderecoAposentado(), "NUMERO"))
			tables.add(mountTable("Número", check(obj.getNumero(), false)));
		if(contem(valor.getAtributosContatoEnderecoAposentado(), "COMPLEMENTO"))
			tables.add(mountTable("Complemento", check(obj.getComplemento(), false)));
		if(contem(valor.getAtributosContatoEnderecoAposentado(), "UF"))
			tables.add(mountTable("UF", Objects.nonNull(obj.getUf()) ? obj.getUf().getEstado() : " "));
		if(contem(valor.getAtributosContatoEnderecoAposentado(), "MUNICIPIO"))
			tables.add(mountTable("Município", Objects.nonNull(obj.getMunicipio()) ? obj.getMunicipio().getDescricao() : " "));
		if(contem(valor.getAtributosContatoEnderecoAposentado(), "BAIRRO"))
			tables.add(mountTable("Bairro", check(obj.getBairro(), false)));
		if(contem(valor.getAtributosContatoEnderecoAposentado(), "CEP"))
			tables.add(mountTable("CEP", check(obj.getCep(), false)));
		if(contem(valor.getAtributosContatoEnderecoAposentado(), "EMAILPESSOAL"))
			tables.add(mountTable("E-mail pessoal", check(obj.getEmailPessoal(), false)));
		if(contem(valor.getAtributosContatoEnderecoAposentado(), "EMAILCOORPORATIVO"))
			tables.add(mountTable("E-mail corporativo", check(obj.getEmailCorporativo(), false)));
		if(contem(valor.getAtributosContatoEnderecoAposentado(), "TELEFONEPRINCIPAL"))
			tables.add(mountTable("Telefone principal", check(obj.getTelefonePrincipal(), false)));
		if(contem(valor.getAtributosContatoEnderecoAposentado(), "TELEFONEOPCIONAL"))
			tables.add(mountTable("Telefone opicional", check(obj.getTelefoneOpcional(), false)));
		
		return mountTable(tables);
	}

	private PdfPTable documentacaoAposentado(RelatorioDadosAposentadoPensionistaFiltro valor, Funcionario obj) {
		List<PdfPTable> tables = new ArrayList<>();

		if(contem(valor.getAtributosDocPessoalAposentado(), "CPF"))
			tables.add(mountTable("CPF", check(Utils.formatarCpf(obj.getCpf()), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "IDENTIDADE"))
			tables.add(mountTable("Identidade", check(obj.getIdentidade(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "ORGAOEXPEDITORIDENTIDADE"))
			tables.add(mountTable("Orgão expeditor", check(obj.getOrgaoExpeditor(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "UFIDENTIDADE"))
			tables.add(mountTable("UF identidade", Objects.nonNull(obj.getUfOrgaoExpeditor()) ? obj.getUfOrgaoExpeditor().getEstado() : " "));
		if(contem(valor.getAtributosDocPessoalAposentado(), "DATAEXPEDICAOIDENTIDADE"))
			tables.add(mountTable("Data expedição ident", Objects.nonNull(obj.getDataExpedicaoRg()) ? data(DATEFORMAT, obj.getDataExpedicaoRg()) : " "));
		if(contem(valor.getAtributosDocPessoalAposentado(), "NUMEROCTPS"))
			tables.add(mountTable("Nº CTPS", check(obj.getNumeroCtps(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "SERIECTPS"))
			tables.add(mountTable("Série CTPS", check(obj.getSerieCtps(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "PIS"))
			tables.add(mountTable("Pis/Pasep", check(obj.getPisPasep(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "DATAPIS"))
			tables.add(mountTable("Dt emissão Pis/Pasep", Objects.nonNull(obj.getDataEmissaoPisPasep()) ? data(DATEFORMAT, obj.getDataEmissaoPisPasep()) : " "));
		//if(contem(valor.getAtributosDocPessoalAposentado(), "NUMEROSUS"))
		//	tables.add(mountTable("Agência Pis/Pasep", check(obj.getAgenciaPisPasep(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "NUMEROSUS"))
			tables.add(mountTable("Nº SUS", check(obj.getNumeroSus(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "NUMEROTITULOELEITOR"))
			tables.add(mountTable("Nº Título eleitor", check(obj.getTituloEleitor(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "SECAO"))
			tables.add(mountTable("Seção", check(obj.getSecao(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "ZONA"))
			tables.add(mountTable("Zona", check(obj.getZona(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "UFTITULOELEITOR"))
			tables.add(mountTable("UF Título eleitor", Objects.nonNull(obj.getUfTituloEleitor()) ? obj.getUfTituloEleitor().getEstado() : " "));
		if(contem(valor.getAtributosDocPessoalAposentado(), "CNH"))
			tables.add(mountTable("CNH", check(obj.getCnh(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "DATAVALIDADECNH"))
			tables.add(mountTable("Dt vencimento CNH", Objects.nonNull(obj.getDataValidadeCnh()) ? data(DATEFORMAT, obj.getDataValidadeCnh()) : " "));
		if(contem(valor.getAtributosDocPessoalAposentado(), "REGALISTAMENTO"))
			tables.add(mountTable("Registro alistamento", check(obj.getRegistroAlistamento(), false)));
		if(contem(valor.getAtributosDocPessoalAposentado(), "CATALISTAMENTO"))
			tables.add(mountTable("Categoria alistamento", check(obj.getCategoriaAlistamento(), false)));
		
		if(contem(valor.getAtributosDocNomeacaoAposentado(), "CLASSIFICACAOATO"))
			tables.add(mountTable("Classificação Ato", Objects.nonNull(obj.getClassificacaoAto()) ? obj.getClassificacaoAto().getDescricao() : " "));
		if(contem(valor.getAtributosDocNomeacaoAposentado(), "NUMPROCESSO"))
			tables.add(mountTable("Nº Processo", check(obj.getNumeroProcesso(), false)));
		if(contem(valor.getAtributosDocNomeacaoAposentado(), "NUMATO"))
			tables.add(mountTable("Nº Ato", check(obj.getNumeroAto(), false)));
		if(contem(valor.getAtributosDocNomeacaoAposentado(), "DATANOMEACAO"))
			tables.add(mountTable("Data de nomeação", Objects.nonNull(obj.getDataNomeacao()) ? data(DATEFORMAT, obj.getDataNomeacao()) : " "));
		if(contem(valor.getAtributosDocNomeacaoAposentado(), "NUMDIARIOOFICIAL"))
			tables.add(mountTable("Nº Diário Oficial", check(obj.getNumeroDiarioOficial(), false)));
		if(contem(valor.getAtributosDocNomeacaoAposentado(), "PUBLICACAODIARIOOFICIAL"))
			tables.add(mountTable("Publicação Diário Oficial", Objects.nonNull(obj.getDataPublicacaoDiarioOficial()) ? data(DATEFORMAT, obj.getDataPublicacaoDiarioOficial()) : " "));
		
		return mountTable(tables);
	}
	
	private PdfPTable dadoPessoalAposentado(RelatorioDadosAposentadoPensionistaFiltro valor, Funcionario obj) {
		List<PdfPTable> tables = new ArrayList<>();

		if(contem(valor.getAtributosDadoProfissionalAposentado(), "MATRICULA"))
			tables.add(mountTable("Matrícula", check(obj.getMatricula(), false)));
		if(contem(valor.getAtributosDadoPessoalAposentado(), "NOME"))
			tables.add(mountTable("Nome", check(obj.getNome(), false)));
		if(contem(valor.getAtributosDadoPessoalAposentado(), "GENERO"))
			tables.add(mountTable("Gênero", Objects.nonNull(obj.getSexo()) ? getGenero(obj.getSexo()) : " "));
		if(contem(valor.getAtributosDadoPessoalAposentado(), "DATANASCIMENTO"))
			tables.add(mountTable("Data de Nascimento", data(DATEFORMAT, obj.getDataNascimento())));
		if(contem(valor.getAtributosDadoPessoalAposentado(), "ESTADOCIVIL"))
			tables.add(mountTable("Estado Civil", Objects.nonNull(obj.getEstadoCivil()) ? obj.getEstadoCivil().getLabel() : " "));
		if(contem(valor.getAtributosDadoPessoalAposentado(), "NATURALIDADE"))
			tables.add(mountTable("Naturalidade", Objects.nonNull(obj.getNaturalidade()) ? obj.getNaturalidade().getDescricao() : " "));
		if(contem(valor.getAtributosDadoPessoalAposentado(), "NACIONALIDADE"))
			tables.add(mountTable("Nacionalidade", Objects.nonNull(obj.getNacionalidade()) ? obj.getNacionalidade().getNacionalidadeFeminina() : " "));
		if(contem(valor.getAtributosDadoPessoalAposentado(), "CORPELE"))
			tables.add(mountTable("Cor da pele", check(obj.getCorPele() != null ? obj.getCorPele().getLabel() : " ", false)));
		if(contem(valor.getAtributosDadoPessoalAposentado(), "TIPOSANGUINEO"))
			tables.add(mountTable("Tipo sanguíneo", check(obj.getTipoSanguineo(), false)));
		if(contem(valor.getAtributosDadoPessoalAposentado(), "NOMEPAI"))
			tables.add(mountTable("Nome pai", check(obj.getNomePai(), false)));
		if(contem(valor.getAtributosDadoPessoalAposentado(), "NOMEMAE"))
			tables.add(mountTable("Nome mãe", check(obj.getNomeMae(), false)));
		
		if(contem(valor.getAtributosDadoProfissionalAposentado(), "FILIAL"))
			tables.add(mountTable("Filial", check(Objects.nonNull(obj.getFilial()) ? obj.getFilial().getNomeFilial() : " ", false)));
		if(contem(valor.getAtributosDadoProfissionalAposentado(), "LOTACAO"))
			tables.add(mountTable("Lotação", check(Objects.nonNull(obj.getLotacao()) ? obj.getLotacao().getDescricao() : " ", false)));
		if(contem(valor.getAtributosDadoProfissionalAposentado(), "CENTROCUSTO"))
			tables.add(mountTable("Centro de custo", check(Objects.nonNull(obj.getCentroCusto()) ? obj.getCentroCusto().getCodigo() : " ", false)));
		if(contem(valor.getAtributosDadoProfissionalAposentado(), "GRAUINSTRUCAO"))
			tables.add(mountTable("Grau de instrução", check(Objects.nonNull(obj.getGrauInstrucao()) ? obj.getGrauInstrucao().getLabel() : " ", false)));
		
		return mountTable(tables);
	}

	/*
	 * Método que verifica se a lista possui o atributo passado
	 */
	private boolean contem(List<String> lista, String string) {
		if(Collections.isNotEmpty(lista)) {
			return lista.contains(string);
		} 
		return false;
	}

	/*
	 * Método de criação do cabeçalho
	 */
	private void criarCabecalho(Document document, String titulo) throws DocumentException {
		addDefaultLogoOnTop(document);
		
		Paragraph paragrafo = new Paragraph();

		Paragraph rh = new Paragraph("RH", Constantes.TIMES12BOLD);
		rh.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(rh);

		Paragraph relatStat = new Paragraph("Relatório de " + titulo.toLowerCase() + "s", Constantes.TIMES12BOLD);
		relatStat.setAlignment(Element.ALIGN_CENTER);
		paragrafo.add(relatStat);


		document.add(paragrafo);

	}
	
	private Paragraph getTitulo(String valor) {
		Paragraph titulo = new Paragraph(valor, Constantes.TIMES10BOLD);
		titulo.setAlignment(Element.ALIGN_LEFT);
		titulo.setSpacingAfter(8);
		
		DottedLineSeparator dottedline = new DottedLineSeparator();
		dottedline.setOffset(-5);
		dottedline.setGap(2f);
		titulo.add(dottedline);
		
		return titulo;
	}
	
	private PdfPTable mountTable(String valorTitulo, String valor) {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		
		table.addCell(cellNoBorder(mountText(valorTitulo, true), true));
		table.addCell(cellNoBorder(mountText(valor, false), false));
		
		return table;
	}
	
	private PdfPTable mountTable(String valorTitulo, Integer valor) {
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		
		table.addCell(cellNoBorder(mountText(valorTitulo, true), true));
		table.addCell(cellNoBorder(mountText(valor.toString(), false), false));
		
		return table;
	}
	
	//método padrão para montar as celulas do relatorio com os dados passados
	private PdfPCell cellNoBorder(Phrase texto, boolean isTitulo) {
		PdfPCell c = new PdfPCell(texto);
		if(isTitulo) {
			c.setVerticalAlignment(Element.ALIGN_TOP);
			c.setPaddingBottom(0);
			c.setPaddingTop(0);
		} else {
			c.setVerticalAlignment(Element.ALIGN_MIDDLE);
			c.setPaddingBottom(3);
			c.setPaddingTop(3);
			c.setPaddingLeft(3);
		}
		c.setBorderWidthTop(0);
		c.setBorderWidthBottom(0);
		c.setBorderWidthLeft(0);
		c.setBorderWidthRight(0);
		c.setPaddingRight(0);
		
		return c;
	}
	
	//Transforma um string em uma Phrase
	private Phrase mountText(String valor, boolean isTitulo) {
		return new Phrase(0, isTitulo ? valor.toUpperCase() : valor, isTitulo ? Constantes.TIMES8BOLD : Constantes.TIMES10);
	}
	
	
	//Método para converter uma data tipo instant num formato a ser passado
	private String data(String valor, Instant data) {
		if(Objects.nonNull(data)) {
			SimpleDateFormat formato = new SimpleDateFormat(valor);
			Date result = Date.from(data);
			return formato.format(result);
		}
		return " ";
	}
	
	//Método para converter uma data tipo localDate num formato a ser passado
	private String data(String valor, LocalDate data) {
		if(Objects.nonNull(data)) {
			SimpleDateFormat formato = new SimpleDateFormat(valor);
			Date result = Date.from(data.atStartOfDay().toInstant(ZoneOffset.UTC));
			return formato.format(result);
		}
		return " ";
	}
	
	//Método para conversão para exibição do gênero
	private String getGenero(char valor) {
		return valor == 'F' ? GeneroEnum.FEMININO.getLabel() : GeneroEnum.MASCULINO.getLabel();
	}
	
	//verifica se o valor está nulo e retorna uma string dele	
	private String check(String valor, boolean upperCase) {
		return Objects.nonNull(valor) ? (upperCase ? valor.toUpperCase() : valor): " ";
	}
	
	//verifica se o valor está nulo e retorna uma string dele
	private String check(Integer valor, boolean upperCase) {
		return Objects.nonNull(valor) ? (upperCase ? valor.toString().toUpperCase() : valor.toString()): " ";
	}
	
	/* Pega uma lista de tabelas e estrutura dentro de outra tabela
	 * 
	 * Calcula a quantidade de colunas baseado no tamanho da lista de tabelas
	 */
	private PdfPTable mountTable(List<PdfPTable> tables) {
		int size = tables.size();
		int coluna = size <= 5 ? size : 4;
		
		PdfPTable table = new PdfPTable(coluna);
		table.setWidthPercentage(100);
		
		for(PdfPTable t : tables) {
			table.addCell(t);
		}
		int falta = size - coluna;
		while (falta > 0) {
			table.addCell(mountTable("", ""));
			falta--;
		}

		
		return table;
	}
	

}

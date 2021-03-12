package com.rhlinkcon.model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.ExcludeFromAudit;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.MotorCalculoAttribute;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Contracheque")
@Table(name = "folha_pagamento_contracheque")
public class Contracheque extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3311865683342850014L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "folha_pagamento_id")
	private FolhaPagamento folhaPagamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pensionista_id")
	private Pensionista pensionista;

	@Column(name = "valor_bruto")
	private Double valorBruto;

	@Column(name = "valor_liquido")
	private Double valorLiquido;

	@Column(name = "valor_desconto")
	private Double valorDesconto;

	@Enumerated(EnumType.STRING)
	@Column(name = "situacao")
	private SituacaoProcessamentoEnum situacao;

	@OneToMany(mappedBy = "contracheque", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Lancamento> lancamentos;

	@Column(name = "nome")
	private String nome;

	@Column(name = "matricula")
	private Integer matricula;

	@Column(name = "lotacao")
	private String lotacao;

	@Column(name = "municipio")
	private String municipio;

	@Column(name = "data_admissao")
	private Instant dataAdmissao;

	@Column(name = "cargo_efetivo")
	private String cargoEfetivo;

	@Column(name = "data_nascimento")
	private Instant dataNascimento;

	@Column(name = "nivel")
	private String nivel;

	@Column(name = "cargo_funcao")
	private String cargoFuncao;

	@Column(name = "referencia")
	private String referencia;

	@Column(name = "vinculo")
	private String vinculo;

	@Column(name = "tipo_situacao_funcional")
	private String tipoSituacaoFuncional;

	@Column(name = "identidade")
	private String identidade;

	@Column(name = "cpf")
	private String cpf;

	@Column(name = "orgao_pagador")
	private String orgaoPagador;

	@Column(name = "dep_sf")
	private Integer depSf;

	@Column(name = "dep_ir")
	private Integer depIr;

	@Column(name = "carga_horaria")
	private String cargaHoraria;

	@ExcludeFromAudit
	@Column(name = "feedback")
	private String feedback;

	@MotorCalculoAttribute(name = "contracheque.valorBruto", label = "Total de Proventos do Contracheque")
	public Double getTotalProventos() {
		return (Objects.nonNull(this.valorBruto) ? this.valorBruto : 0.0);
	}

	@MotorCalculoAttribute(name = "contracheque.valorDesconto", label = "Total de Descontos do Contracheque")
	public Double getTotalDescontos() {
		return (Objects.nonNull(this.valorDesconto) ? this.valorDesconto : 0.0);
	}

	@MotorCalculoAttribute(name = "contracheque.valorLiquido", label = "Total Líquido do Contracheque")
	public Double getTotalLiquido() {
		return (Objects.nonNull(this.valorLiquido) ? this.valorLiquido : 0.0);
	}

	@MotorCalculoAttribute(name = "contracheque.titularFuncionario", label = "Titular é Funcionário?")
	public boolean titularFuncionario() {
		return (Objects.nonNull(this.funcionario) ? true : false);
	}

	@MotorCalculoAttribute(name = "contracheque.titularPensionista", label = "Titular é Pensionista?")
	public boolean titularPensionista() {
		return (Objects.nonNull(this.pensionista) ? true : false);
	}

	@MotorCalculoAttribute(name = "funcionario.dataAdmissao", label = "Data de Admissão do Funcionário")
	public String getDataAdmissaoParametro() {
		Instant admissao = null;
		if (Objects.isNull(this.funcionario))
			admissao = new Date().toInstant();
		else
			admissao = this.funcionario.getDataAdmissao();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(Date.from(admissao));
		return "new Date('" + date + "').getTime()";
	}

	@MotorCalculoAttribute(name = "pensionista.pensaoPagamento.dataPrimeiroPagamento", label = "Data de 1º Pagamento do Pensionista")
	public String getDataAdmissaoParametroInstituidor() {
		Instant admissao = null;
		if (Objects.isNull(this.pensionista) || Objects.isNull(this.pensionista.getPensaoPagamento()))
			admissao = new Date().toInstant();
		else
			admissao = this.pensionista.getPensaoPagamento().getDataPrimeiroPagamento()
					.atStartOfDay(ZoneId.systemDefault()).toInstant();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(Date.from(admissao));
		return "new Date('" + date + "').getTime()";
	}

	@MotorCalculoAttribute(name = "funcionario.situacaoFuncionalAposentado", label = "Funcionário é Aposentado?")
	public boolean isSituacaoFuncionalAposentado() {
		if (Objects.isNull(this.funcionario))
			return false;
		if (this.funcionario.getTipoSituacaoFuncional().getDescricao().equals("APOSENTADO"))
			return true;
		return false;
	}

	@MotorCalculoAttribute(name = "funcionario.situacaoFuncionalAposentado", label = "Funcionário é Ativo?")
	public boolean isSituacaoFuncionalAtivo() {
		if (Objects.isNull(this.funcionario))
			return false;
		if (this.funcionario.getTipoSituacaoFuncional().getDescricao().equals("ATIVO"))
			return true;
		return false;
	}

	@MotorCalculoAttribute(name = "funcionario.idade", label = "Idade do Funcionário")
	public long getFuncionarioIdade() {
		if (Objects.isNull(this.funcionario))
			return 0;

		LocalDate data1 = this.funcionario.getDataNascimento().atZone(ZoneOffset.UTC).toLocalDate();

		LocalDate data2 = LocalDate.of(this.folhaPagamento.getFolhaCompetencia().getAnoCompetencia(),
				this.folhaPagamento.getFolhaCompetencia().getMesCompetencia(), 1);

		Period period = Period.between(data1, data2);
		return period.getYears();

	}

	@MotorCalculoAttribute(name = "pensionista.idade", label = "Idade do Pensionista")
	public long getPensionistaIdade() {
		if (Objects.isNull(this.pensionista))
			return 0;

		LocalDate data1 = this.pensionista.getDataNascimento();

		LocalDate data2 = LocalDate.of(this.folhaPagamento.getFolhaCompetencia().getAnoCompetencia(),
				this.folhaPagamento.getFolhaCompetencia().getMesCompetencia(), 1);

		Period period = Period.between(data1, data2);
		return period.getYears();

	}

	@MotorCalculoAttribute(name = "funcionario.grauInstrucao", label = "Grau de Instrução do Funcionário")
	public GrauInstrucaoEnum getGrauInstrucao() {
		if (Objects.isNull(this.funcionario))
			return null;
		return this.funcionario.getGrauInstrucao();
	}

	@MotorCalculoAttribute(name = "funcionario.previdenciaEspecial", label = "Funcionário tem Previdência Especial?")
	public boolean getPrevidenciaEspecial() {
		if (Objects.isNull(this.funcionario))
			return false;
		if (Objects.isNull(this.funcionario.getDadoCadastral()))
			return false;
		if (Objects.isNull(this.funcionario.getDadoCadastral().getPrevidenciaEspecial()))
			return false;
		return this.funcionario.getDadoCadastral().getPrevidenciaEspecial();
	}

	@MotorCalculoAttribute(name = "titular.aniversariante", label = "Titular é Aniversariante do Mês?")
	public boolean getMesAniversarioFuncionario() {
		if (Objects.isNull(this.funcionario) && Objects.isNull(this.pensionista))
			return false;

		int month = 0;
		Calendar cal = Calendar.getInstance();

		if (Objects.nonNull(this.funcionario)) {
			cal.setTime(Date.from(this.funcionario.getDataNascimento()));
			month = cal.get(Calendar.MONTH);

		} else {
			cal.setTime(
					Date.from(this.pensionista.getDataNascimento().atStartOfDay(ZoneId.systemDefault()).toInstant()));
			month = cal.get(Calendar.MONTH);
		}

		int monthNow = this.folhaPagamento.getFolhaCompetencia().getMesCompetencia() - 1;

		return (month == monthNow) ? true : false;

	}

	@MotorCalculoAttribute(name = "titular.isentoIr", label = "Titular é Isento de Imposto de Renda?")
	public boolean getIsentoIr() {
		if (Objects.isNull(this.funcionario) && Objects.isNull(this.pensionista))
			return false;

//		 Instant dataInicialIsencaoPrevidencia = null;
//		 Instant dataFinalIsencaoPrevidencia = null;
		Instant dataInicialIsencaoIr = null;
		Instant dataFinalIsencaoIr = null;

		if (Objects.nonNull(this.funcionario)) {
			if (Objects.isNull(this.funcionario.getDadoCadastral()))
				return false;

			if (this.funcionario.getDadoCadastral().getDataInicialIsencaoIr() == null
					&& this.funcionario.getDadoCadastral().getDataFinalIsencaoIr() == null)
				return false;

			dataInicialIsencaoIr = this.funcionario.getDadoCadastral().getDataInicialIsencaoIr();
			dataFinalIsencaoIr = this.funcionario.getDadoCadastral().getDataFinalIsencaoIr();

		} else {
			if (Objects.isNull(this.pensionista.getPensaoPagamento()))
				return false;

			if (this.pensionista.getPensaoPagamento().getDataInicialIsencaoIr() == null
					&& this.pensionista.getPensaoPagamento().getDataFinalIsencaoIr() == null)
				return false;

			dataInicialIsencaoIr = this.pensionista.getPensaoPagamento().getDataInicialIsencaoIr();
			dataFinalIsencaoIr = this.pensionista.getPensaoPagamento().getDataFinalIsencaoIr();

		}

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, this.folhaPagamento.getFolhaCompetencia().getMesCompetencia() - 1);
		calendar.set(Calendar.YEAR, this.folhaPagamento.getFolhaCompetencia().getAnoCompetencia());
		Instant compInstant = calendar.getTime().toInstant();

		Instant inicialIsencaoIrInstant = null;
		if (dataInicialIsencaoIr != null) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(Date.from(dataInicialIsencaoIr));
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);

			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.YEAR, year);

			inicialIsencaoIrInstant = calendar.getTime().toInstant();

		}

		Instant finalIsencaoIrInstant = null;
		if (dataFinalIsencaoIr != null) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(Date.from(dataFinalIsencaoIr));
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);

			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.YEAR, year);

			finalIsencaoIrInstant = calendar.getTime().toInstant();

		}

		if (inicialIsencaoIrInstant != null && finalIsencaoIrInstant == null) {
			if (compInstant.isAfter(inicialIsencaoIrInstant) || compInstant.equals(inicialIsencaoIrInstant))
				return true;
		}

		if (inicialIsencaoIrInstant == null && finalIsencaoIrInstant != null) {
			if (compInstant.isBefore(finalIsencaoIrInstant) || compInstant.equals(finalIsencaoIrInstant))
				return true;
		}

		if (inicialIsencaoIrInstant != null && finalIsencaoIrInstant != null) {
			if ((compInstant.isAfter(inicialIsencaoIrInstant) || compInstant.equals(inicialIsencaoIrInstant))
					&& (compInstant.isBefore(finalIsencaoIrInstant) || compInstant.equals(finalIsencaoIrInstant)))
				return true;
		}

		return false;
	}

	@MotorCalculoAttribute(name = "titular.isentoPrevidencia", label = "Titular é Isento de Previdência?")
	public boolean getIsentoPrev() {
		if (Objects.isNull(this.funcionario) && Objects.isNull(this.pensionista))
			return false;

		Instant dataInicialIsencaoPrevidencia = null;
		Instant dataFinalIsencaoPrevidencia = null;

		if (Objects.nonNull(this.funcionario)) {
			if (Objects.isNull(this.funcionario.getDadoCadastral()))
				return false;

			if (getPrevidenciaEspecial())
				return false;

			if (this.funcionario.getDadoCadastral().getDataInicialIsencaoPrevidencia() == null
					&& this.funcionario.getDadoCadastral().getDataFinalIsencaoPrevidencia() == null)
				return false;

			dataInicialIsencaoPrevidencia = this.funcionario.getDadoCadastral().getDataInicialIsencaoPrevidencia();
			dataFinalIsencaoPrevidencia = this.funcionario.getDadoCadastral().getDataFinalIsencaoPrevidencia();

		} else {
			if (Objects.isNull(this.pensionista.getPensaoPagamento()))
				return false;

			if (this.pensionista.getPensaoPagamento().getDataInicialIsencaoPrevidencia() == null
					&& this.pensionista.getPensaoPagamento().getDataFinalIsencaoPrevidencia() == null)
				return false;

			dataInicialIsencaoPrevidencia = this.pensionista.getPensaoPagamento().getDataInicialIsencaoPrevidencia();
			dataFinalIsencaoPrevidencia = this.pensionista.getPensaoPagamento().getDataFinalIsencaoPrevidencia();

		}

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, this.folhaPagamento.getFolhaCompetencia().getMesCompetencia() - 1);
		calendar.set(Calendar.YEAR, this.folhaPagamento.getFolhaCompetencia().getAnoCompetencia());
		Instant compInstant = calendar.getTime().toInstant();

		Instant inicialIsencaoPrevInstant = null;
		if (dataInicialIsencaoPrevidencia != null) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(Date.from(dataInicialIsencaoPrevidencia));
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);

			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.YEAR, year);

			inicialIsencaoPrevInstant = calendar.getTime().toInstant();

		}

		Instant finalIsencaoPrevInstant = null;
		if (dataFinalIsencaoPrevidencia != null) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(Date.from(dataFinalIsencaoPrevidencia));
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);

			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.YEAR, year);

			finalIsencaoPrevInstant = calendar.getTime().toInstant();

		}

		if (inicialIsencaoPrevInstant != null && finalIsencaoPrevInstant == null) {
			if (compInstant.isAfter(inicialIsencaoPrevInstant) || compInstant.equals(inicialIsencaoPrevInstant))
				return true;
		}

		if (inicialIsencaoPrevInstant == null && finalIsencaoPrevInstant != null) {
			if (compInstant.isBefore(finalIsencaoPrevInstant) || compInstant.equals(finalIsencaoPrevInstant))
				return true;
		}

		if (inicialIsencaoPrevInstant != null && finalIsencaoPrevInstant != null) {
			if ((compInstant.isAfter(inicialIsencaoPrevInstant) || compInstant.equals(inicialIsencaoPrevInstant))
					&& (compInstant.isBefore(finalIsencaoPrevInstant) || compInstant.equals(finalIsencaoPrevInstant)))
				return true;
		}

		return false;
	}

	@MotorCalculoAttribute(name = "funcionario.referenciaSalarialCargo.valor", label = "Salário - Referência Cargo")
	public Double getSalarioBaseCargoFuncionario() {
		if (Objects.isNull(this.funcionario))
			return 0.0;
		return (Objects.nonNull(this.funcionario.getReferenciaSalarialCargo())
				? this.funcionario.getReferenciaSalarialCargo().getValor()
				: 0.0);
	}

	@MotorCalculoAttribute(name = "funcionario.referenciaSalarialFuncao.valor", label = "Salário - Referência Função")
	public Double getSalarioBaseFuncaoFuncionario() {
		if (Objects.isNull(this.funcionario))
			return 0.0;
		return (Objects.nonNull(this.funcionario.getReferenciaSalarialFuncao())
				? this.funcionario.getReferenciaSalarialFuncao().getValor()
				: 0.0);
	}

	@MotorCalculoAttribute(name = "funcionario.cpf", label = "CPF do Funcionário")
	public String getCpfFuncionario() {
		if (Objects.isNull(this.funcionario))
			return "";
		return this.funcionario.getCpf();
	}

	@MotorCalculoAttribute(name = "funcionario.numeroDependentesSalarioFamilia", label = "Número de Dependentes do Funcionário - Salário Família")
	public Integer getDependentesSl() {
		if (Objects.isNull(this.funcionario))
			return 0;
		return (Objects.nonNull(this.funcionario.getNumeroDependentesSalarioFamilia())
				? this.funcionario.getNumeroDependentesSalarioFamilia()
				: 0);
	}

	@MotorCalculoAttribute(name = "funcionario.numeroDependentesImpostoRenda", label = "Número de Dependentes do Funcionário - IRRF")
	public Integer getDependentesIrrf() {
		if (Objects.isNull(this.funcionario))
			return 0;
		return (Objects.nonNull(this.funcionario.getNumeroDependentesImpostoRenda())
				? this.funcionario.getNumeroDependentesImpostoRenda()
				: 0);
	}

	@MotorCalculoAttribute(name = "funcionario.dataNascimento", label = "Data de Nascimento do Funcionário")
	public String getDataNascimentoParametro() {
		Instant dataNascimento = null;
		if (Objects.isNull(this.funcionario))
			dataNascimento = new Date().toInstant();
		else
			dataNascimento = this.funcionario.getDataNascimento();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(Date.from(dataNascimento));
		return "new Date('" + date + "').getTime()";
	}

	@MotorCalculoAttribute(name = "pensionista.dataNascimento", label = "Data de Nascimento do Pensionista")
	public String getDataNascimentoParametroPensionista() {
		Instant dataNascimento = null;
		if (Objects.isNull(this.pensionista))
			dataNascimento = new Date().toInstant();
		else
			dataNascimento = this.pensionista.getDataNascimento().atStartOfDay(ZoneId.systemDefault()).toInstant();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(Date.from(dataNascimento));
		return "new Date('" + date + "').getTime()";
	}

	@MotorCalculoAttribute(name = "pensionista.pagamento.tipoRateio", label = "Tipo de Rateio de Pagamento do Pensionista")
	public String getTipoRateio() {
		if (Objects.isNull(this.pensionista) || Objects.isNull(this.pensionista.getPensaoPagamento()))
			return "";
		return this.pensionista.getPensaoPagamento().getTipoRateio().toString();
	}

	@MotorCalculoAttribute(name = "pensionista.pagamento.valorRateio", label = "Valor de Rateio de Pagamento do Pensionista")
	public Double getValorRateio() {
		if (Objects.isNull(this.pensionista) || Objects.isNull(this.pensionista.getPensaoPagamento()))
			return 0.0;
		return this.pensionista.getPensaoPagamento().getValorRateio();
	}

	public Contracheque() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FolhaPagamento getFolhaPagamento() {
		return folhaPagamento;
	}

	public void setFolhaPagamento(FolhaPagamento folhaPagamento) {
		this.folhaPagamento = folhaPagamento;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Pensionista getPensionista() {
		return pensionista;
	}

	public void setPensionista(Pensionista pensionista) {
		this.pensionista = pensionista;
	}

	public Double getValorBruto() {
		return valorBruto;
	}

	public void setValorBruto(Double valorBruto) {
		this.valorBruto = valorBruto;
		if (Objects.isNull(this.valorDesconto))
			this.valorDesconto = 0D;
		this.valorLiquido = this.valorBruto - this.valorDesconto;
	}

	public Double getValorLiquido() {
		return valorLiquido;
	}

	public void setValorLiquido(Double valorLiquido) {
		this.valorLiquido = valorLiquido;
	}

	public Double getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(Double valorDesconto) {
		this.valorDesconto = valorDesconto;
		if (Objects.isNull(this.valorBruto))
			this.valorBruto = 0D;
		this.valorLiquido = this.valorBruto - this.valorDesconto;
	}

	public SituacaoProcessamentoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProcessamentoEnum situacao) {
		this.situacao = situacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getLotacao() {
		return lotacao;
	}

	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public Instant getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Instant dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public String getCargoEfetivo() {
		return cargoEfetivo;
	}

	public void setCargoEfetivo(String cargoEfetivo) {
		this.cargoEfetivo = cargoEfetivo;
	}

	public Instant getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Instant dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getCargoFuncao() {
		return cargoFuncao;
	}

	public void setCargoFuncao(String cargoFuncao) {
		this.cargoFuncao = cargoFuncao;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}

	public String getTipoSituacaoFuncional() {
		return tipoSituacaoFuncional;
	}

	public void setTipoSituacaoFuncional(String tipoSituacaoFuncional) {
		this.tipoSituacaoFuncional = tipoSituacaoFuncional;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getOrgaoPagador() {
		return orgaoPagador;
	}

	public void setOrgaoPagador(String orgaoPagador) {
		this.orgaoPagador = orgaoPagador;
	}

	public Integer getDepSf() {
		return depSf;
	}

	public void setDepSf(Integer depSf) {
		this.depSf = depSf;
	}

	public Integer getDepIr() {
		return depIr;
	}

	public void setDepIr(Integer depIr) {
		this.depIr = depIr;
	}

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Lancamento> getLancamentoProventos() {
		return getLancamentos().stream().filter(v -> v.getVerba().getTipoVerba().equals(TipoVerba.VANTAGEM))
				.collect(Collectors.toList());
	}

	public List<Lancamento> getLancamentoDescontos() {
		return getLancamentos().stream().filter(v -> v.getVerba().getTipoVerba().equals(TipoVerba.DESCONTO))
				.collect(Collectors.toList());
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

}

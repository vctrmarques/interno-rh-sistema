package com.rhlinkcon.model;

import java.time.Instant;
import java.util.Objects;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.dadoCadastralComplementar.DadoCadastralComplementarRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Dado Cadastral Complementar")
@Table(name = "dado_cadastral_complementar")
public class DadoCadastralComplementar extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5627761969715505008L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	// Início Aba Dados Pessoais
	@Enumerated(EnumType.STRING)
	@Column(name = "fardamento")
	private FardamentoEnum fardamento;

	@Column(name = "altura")
	private Double altura;

	@Column(name = "peso")
	private Double peso;

	@Column(name = "calcado")
	private String calcado;

	// Fim Aba Dados Pessoais
	// Início Aba Condições de Saúde

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "condicao_retorno_trabalho")
	private CondicaoRetornoTrabalhoEnum condicaoRetornoTrabalho;

	@Column(name = "dt_ini_deficiencia")
	private Instant dataInicioDeficiencia;

	@Column(name = "dt_fim_deficiencia")
	private Instant dataFimDeficiencia;

	@NotNull
	@Column(name = "reabilitado")
	private Boolean reabilitado;

	@NotNull
	@Column(name = "cotista")
	private Boolean cotista;

	@Column(name = "descricao_deficiencia")
	private String descricaoDeficiencia;

	// Fim Aba Deficiência
	// Início Aba Aponsentadoria
	@NotNull
	@Column(name = "dt_aposentadoria")
	private Instant dataAposentadoria;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_aposentadoria")
	private TipoAposentadoriaEnum tipoAposentadoria;

	@Column(name = "proporcionalidade")
	private Integer proporcionalidade;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_proporcao")
	private TipoProporcaoEnum tipoProporcao;

//	@NotNull
//	@Column(name = "contribuicao_inss")
//	private Boolean contribuicaoInss;
//
//	@Column(name = "consignado_bloqueado")
//	private Boolean consignadoBloqueado;

	@NotNull
	@Column(name = "em_processo_de_aposentadoria")
	private Boolean emProcessoDeAposentadoria;

//	@NotNull
//	@Column(name = "contribuicao_ir")
//	private Boolean contribuicaoIr;

	@Column(name = "num_processo_aposentadoria")
	private Long numeroProcessoAposentadoria;

	@Column(name = "local_redistribuicao")
	private String localRedistribuicao;

	@Column(name = "dt_distribuicao_cedencia")
	private Instant dataDistribuicaoCedencia;

//	@Column(name = "dt_ini_isencao_ir_previdencia")
//	private Instant dataInicialIsencaoIrPrevidencia;
//
//	@Column(name = "dt_fim_isencao_ir_previdencia")
//	private Instant dataFinalIsencaoIrPrevidencia;

	@Column(name = "dt_falecimento")
	private Instant dataFalecimento;

	@Column(name = "tempo_servidor_professor")
	private Integer tempoServidorProfessor;

	// Implementação de isenções

	@Column(name = "dt_ini_isencao_ir")
	private Instant dataInicialIsencaoIr;

	@Column(name = "dt_fim_isencao_ir")
	private Instant dataFinalIsencaoIr;

	@Column(name = "dt_ini_isencao_previdencia")
	private Instant dataInicialIsencaoPrevidencia;

	@Column(name = "dt_fim_isencao_previdencia")
	private Instant dataFinalIsencaoPrevidencia;

	@Column(name = "previdencia_especial")
	private Boolean previdenciaEspecial;

	// Fim aba aposentadoria

	public DadoCadastralComplementar() {

	}

	public DadoCadastralComplementar(DadoCadastralComplementarRequest dadoCadastralComplementarRequest) {

		if (Objects.nonNull(dadoCadastralComplementarRequest.getFardamento()))
			this.setFardamento(FardamentoEnum.valueOf(dadoCadastralComplementarRequest.getFardamento()));

		this.setAltura(dadoCadastralComplementarRequest.getAltura());
		this.setPeso(dadoCadastralComplementarRequest.getPeso());
		this.setCalcado(dadoCadastralComplementarRequest.getCalcado());

		if (Objects.nonNull(dadoCadastralComplementarRequest.getCondicaoRetornoTrabalho()))
			this.setCondicaoRetornoTrabalho(
					CondicaoRetornoTrabalhoEnum.valueOf(dadoCadastralComplementarRequest.getCondicaoRetornoTrabalho()));

		this.setDataInicioDeficiencia(dadoCadastralComplementarRequest.getDataInicioDeficiencia());
		this.setDataFimDeficiencia(dadoCadastralComplementarRequest.getDataFimDeficiencia());
		this.setReabilitado(Utils.setBool(dadoCadastralComplementarRequest.getReabilitado()));
		this.setCotista(Utils.setBool(dadoCadastralComplementarRequest.getCotista()));
		this.setDescricaoDeficiencia(dadoCadastralComplementarRequest.getDescricaoDeficiencia());
		this.setDataAposentadoria(dadoCadastralComplementarRequest.getDataAposentadoria());
		this.setTipoAposentadoria(
				TipoAposentadoriaEnum.valueOf(dadoCadastralComplementarRequest.getTipoAposentadoria()));
		this.setProporcionalidade(dadoCadastralComplementarRequest.getProporcionalidade());
		this.setTipoProporcao(TipoProporcaoEnum.valueOf(dadoCadastralComplementarRequest.getTipoProporcao()));
//		this.setContribuicaoInss(Utils.setBool(dadoCadastralComplementarRequest.getContribuicaoInss()));
//		this.setConsignadoBloqueado(Utils.setBool(dadoCadastralComplementarRequest.getConsignadoBloqueado()));
//		this.setContribuicaoIr(Utils.setBool(dadoCadastralComplementarRequest.getContribuicaoIr()));
		this.setNumeroProcessoAposentadoria(dadoCadastralComplementarRequest.getNumeroProcessoAposentadoria());
		this.setLocalRedistribuicao(dadoCadastralComplementarRequest.getLocalRedistribuicao());
		this.setDataDistribuicaoCedencia(dadoCadastralComplementarRequest.getDataDistribuicaoCedencia());
//		this.setDataInicialIsencaoIrPrevidencia(dadoCadastralComplementarRequest.getDataInicialIsencaoIrPrevidencia());
//		this.setDataFinalIsencaoIrPrevidencia(dadoCadastralComplementarRequest.getDataFinalIsencaoIrPrevidencia());
		this.setDataFalecimento(dadoCadastralComplementarRequest.getDataFalecimento());
		this.setTempoServidorProfessor(dadoCadastralComplementarRequest.getTempoServidorProfessor());
		this.setEmProcessoDeAposentadoria(dadoCadastralComplementarRequest.getEmProcessoDeAposentadoria());

		this.setDataInicialIsencaoIr(dadoCadastralComplementarRequest.getDataInicialIsencaoIr());
		this.setDataFinalIsencaoIr(dadoCadastralComplementarRequest.getDataFinalIsencaoIr());
		this.setDataInicialIsencaoPrevidencia(dadoCadastralComplementarRequest.getDataInicialIsencaoPrevidencia());
		this.setDataFinalIsencaoPrevidencia(dadoCadastralComplementarRequest.getDataFinalIsencaoPrevidencia());
		this.setPrevidenciaEspecial(dadoCadastralComplementarRequest.getPrevidenciaEspecial());

	}

	public Instant getDataInicialIsencaoIr() {
		return dataInicialIsencaoIr;
	}

	public void setDataInicialIsencaoIr(Instant dataInicialIsencaoIr) {
		this.dataInicialIsencaoIr = dataInicialIsencaoIr;
	}

	public Instant getDataFinalIsencaoIr() {
		return dataFinalIsencaoIr;
	}

	public void setDataFinalIsencaoIr(Instant dataFinalIsencaoIr) {
		this.dataFinalIsencaoIr = dataFinalIsencaoIr;
	}

	public Instant getDataInicialIsencaoPrevidencia() {
		return dataInicialIsencaoPrevidencia;
	}

	public void setDataInicialIsencaoPrevidencia(Instant dataInicialIsencaoPrevidencia) {
		this.dataInicialIsencaoPrevidencia = dataInicialIsencaoPrevidencia;
	}

	public Instant getDataFinalIsencaoPrevidencia() {
		return dataFinalIsencaoPrevidencia;
	}

	public void setDataFinalIsencaoPrevidencia(Instant dataFinalIsencaoPrevidencia) {
		this.dataFinalIsencaoPrevidencia = dataFinalIsencaoPrevidencia;
	}

	public Boolean getPrevidenciaEspecial() {
		return previdenciaEspecial;
	}

	public void setPrevidenciaEspecial(Boolean previdenciaEspecial) {
		this.previdenciaEspecial = previdenciaEspecial;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public FardamentoEnum getFardamento() {
		return fardamento;
	}

	public void setFardamento(FardamentoEnum fardamento) {
		this.fardamento = fardamento;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public String getCalcado() {
		return calcado;
	}

	public void setCalcado(String calcado) {
		this.calcado = calcado;
	}

	public CondicaoRetornoTrabalhoEnum getCondicaoRetornoTrabalho() {
		return condicaoRetornoTrabalho;
	}

	public void setCondicaoRetornoTrabalho(CondicaoRetornoTrabalhoEnum condicaoRetornoTrabalho) {
		this.condicaoRetornoTrabalho = condicaoRetornoTrabalho;
	}

	public Instant getDataInicioDeficiencia() {
		return dataInicioDeficiencia;
	}

	public void setDataInicioDeficiencia(Instant dataInicioDeficiencia) {
		this.dataInicioDeficiencia = dataInicioDeficiencia;
	}

	public Boolean getReabilitado() {
		return reabilitado;
	}

	public void setReabilitado(Boolean reabilitado) {
		this.reabilitado = reabilitado;
	}

	public Boolean getCotista() {
		return cotista;
	}

	public void setCotista(Boolean cotista) {
		this.cotista = cotista;
	}

	public String getDescricaoDeficiencia() {
		return descricaoDeficiencia;
	}

	public void setDescricaoDeficiencia(String descricaoDeficiencia) {
		this.descricaoDeficiencia = descricaoDeficiencia;
	}

	public Instant getDataAposentadoria() {
		return dataAposentadoria;
	}

	public void setDataAposentadoria(Instant dataAposentadoria) {
		this.dataAposentadoria = dataAposentadoria;
	}

	public TipoAposentadoriaEnum getTipoAposentadoria() {
		return tipoAposentadoria;
	}

	public void setTipoAposentadoria(TipoAposentadoriaEnum tipoAposentadoria) {
		this.tipoAposentadoria = tipoAposentadoria;
	}

	public Integer getProporcionalidade() {
		return proporcionalidade;
	}

	public void setProporcionalidade(Integer proporcionalidade) {
		this.proporcionalidade = proporcionalidade;
	}

	public TipoProporcaoEnum getTipoProporcao() {
		return tipoProporcao;
	}

	public void setTipoProporcao(TipoProporcaoEnum tipoProporcao) {
		this.tipoProporcao = tipoProporcao;
	}

//	public Boolean getContribuicaoInss() {
//		return contribuicaoInss;
//	}
//
//	public void setContribuicaoInss(Boolean contribuicaoInss) {
//		this.contribuicaoInss = contribuicaoInss;
//	}
//
//	public Boolean getConsignadoBloqueado() {
//		return consignadoBloqueado;
//	}
//
//	public void setConsignadoBloqueado(Boolean consignadoBloqueado) {
//		this.consignadoBloqueado = consignadoBloqueado;
//	}
//
//	public Boolean getContribuicaoIr() {
//		return contribuicaoIr;
//	}
//
//	public void setContribuicaoIr(Boolean contribuicaoIr) {
//		this.contribuicaoIr = contribuicaoIr;
//	}

	public Boolean getEmProcessoDeAposentadoria() {
		return emProcessoDeAposentadoria;
	}

	public void setEmProcessoDeAposentadoria(Boolean emProcessoDeAposentadoria) {
		this.emProcessoDeAposentadoria = emProcessoDeAposentadoria;
	}

	public Long getNumeroProcessoAposentadoria() {
		return numeroProcessoAposentadoria;
	}

	public void setNumeroProcessoAposentadoria(Long numeroProcessoAposentadoria) {
		this.numeroProcessoAposentadoria = numeroProcessoAposentadoria;
	}

	public String getLocalRedistribuicao() {
		return localRedistribuicao;
	}

	public void setLocalRedistribuicao(String localRedistribuicao) {
		this.localRedistribuicao = localRedistribuicao;
	}

	public Instant getDataDistribuicaoCedencia() {
		return dataDistribuicaoCedencia;
	}

	public void setDataDistribuicaoCedencia(Instant dataDistribuicaoCedencia) {
		this.dataDistribuicaoCedencia = dataDistribuicaoCedencia;
	}

//	public Instant getDataInicialIsencaoIrPrevidencia() {
//		return dataInicialIsencaoIrPrevidencia;
//	}
//
//	public void setDataInicialIsencaoIrPrevidencia(Instant dataInicialIsencaoIrPrevidencia) {
//		this.dataInicialIsencaoIrPrevidencia = dataInicialIsencaoIrPrevidencia;
//	}
//
//	public Instant getDataFinalIsencaoIrPrevidencia() {
//		return dataFinalIsencaoIrPrevidencia;
//	}
//
//	public void setDataFinalIsencaoIrPrevidencia(Instant dataFinalIsencaoIrPrevidencia) {
//		this.dataFinalIsencaoIrPrevidencia = dataFinalIsencaoIrPrevidencia;
//	}

	public Instant getDataFalecimento() {
		return dataFalecimento;
	}

	public void setDataFalecimento(Instant dataFalecimento) {
		this.dataFalecimento = dataFalecimento;
	}

	public Integer getTempoServidorProfessor() {
		return tempoServidorProfessor;
	}

	public void setTempoServidorProfessor(Integer tempoServidorProfessor) {
		this.tempoServidorProfessor = tempoServidorProfessor;
	}

	public Instant getDataFimDeficiencia() {
		return dataFimDeficiencia;
	}

	public void setDataFimDeficiencia(Instant dataFimDeficiencia) {
		this.dataFimDeficiencia = dataFimDeficiencia;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}

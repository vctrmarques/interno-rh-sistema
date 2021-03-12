package com.rhlinkcon.payload.dadoCadastralComplementar;

import java.time.Instant;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.DadoCadastralComplementar;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DadoCadastralComplementarResponse extends DadoCadastralResponse {

	private Long id;

	private FuncionarioResponse funcionario;

	// Início Aba Dados Pessoais
	private String fardamento;

	private Double altura;

	private Double peso;

	private String calcado;

	// Fim Aba Dados Pessoais
	// Início Aba Condições de Saúde

	private String condicaoRetornoTrabalho;

	private Instant dataInicioDeficiencia;

	private Instant dataFimDeficiencia;

	private Boolean reabilitado;

	private Boolean cotista;

	private String descricaoDeficiencia;

	// Fim Aba Deficiência
	// Início Aba Aponsentadoria
	private Instant dataAposentadoria;

	private String tipoAposentadoria;

	private Integer proporcionalidade;

	private String tipoProporcao;

	private Boolean contribuicaoInss;

	private Boolean consignadoBloqueado;

	private Boolean contribuicaoIr;

	private Boolean emProcessoDeAposentadoria;

	private Long numeroProcessoAposentadoria;

	private String localRedistribuicao;

	private Instant dataInicialIsencaoIr;

	private Instant dataFinalIsencaoIr;

	private Instant dataInicialIsencaoPrevidencia;

	private Instant dataFinalIsencaoPrevidencia;

	private Boolean previdenciaEspecial;

	private Instant dataDistribuicaoCedencia;

//	private Instant dataInicialIsencaoIrPrevidencia;

//	private Instant dataFinalIsencaoIrPrevidencia;

	private Instant dataFalecimento;

	private Integer tempoServidorProfessor;

	public DadoCadastralComplementarResponse(DadoCadastralComplementar dadoCadastralComplementar) {
		setDadoCadastralComplementar(dadoCadastralComplementar);
	}

	public DadoCadastralComplementarResponse(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public DadoCadastralComplementarResponse(DadoCadastralComplementar dadoCadastralComplementar, Instant criadoEm,
			String criadoPor, Instant alteradoEm, String alteradoPor) {
		setDadoCadastralComplementar(dadoCadastralComplementar);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setDadoCadastralComplementar(DadoCadastralComplementar dadoCadastralComplementar) {
		this.setId(dadoCadastralComplementar.getId());
		this.setFuncionario(new FuncionarioResponse(dadoCadastralComplementar.getFuncionario()));

		if (Objects.nonNull(dadoCadastralComplementar.getFardamento()))
			this.setFardamento(dadoCadastralComplementar.getFardamento().toString());

		this.setAltura(dadoCadastralComplementar.getAltura());
		this.setPeso(dadoCadastralComplementar.getPeso());
		this.setCalcado(dadoCadastralComplementar.getCalcado());

		if (Objects.nonNull(dadoCadastralComplementar.getCondicaoRetornoTrabalho()))
			this.setCondicaoRetornoTrabalho(dadoCadastralComplementar.getCondicaoRetornoTrabalho().toString());

		this.setDataInicioDeficiencia(dadoCadastralComplementar.getDataInicioDeficiencia());
		this.setDataFimDeficiencia(dadoCadastralComplementar.getDataFimDeficiencia());
		this.setReabilitado(dadoCadastralComplementar.getReabilitado());
		this.setCotista(dadoCadastralComplementar.getCotista());
		this.setDescricaoDeficiencia(dadoCadastralComplementar.getDescricaoDeficiencia());
		this.setDataAposentadoria(dadoCadastralComplementar.getDataAposentadoria());
		this.setTipoAposentadoria(dadoCadastralComplementar.getTipoAposentadoria().toString());
		this.setProporcionalidade(dadoCadastralComplementar.getProporcionalidade());
		this.setTipoProporcao(dadoCadastralComplementar.getTipoProporcao().toString());
//		this.setContribuicaoInss(dadoCadastralComplementar.getContribuicaoInss());
//		this.setConsignadoBloqueado(dadoCadastralComplementar.getConsignadoBloqueado());
//		this.setContribuicaoIr(dadoCadastralComplementar.getContribuicaoIr());
		this.setNumeroProcessoAposentadoria(dadoCadastralComplementar.getNumeroProcessoAposentadoria());
		this.setLocalRedistribuicao(dadoCadastralComplementar.getLocalRedistribuicao());
		this.setDataDistribuicaoCedencia(dadoCadastralComplementar.getDataDistribuicaoCedencia());
//		this.setDataInicialIsencaoIrPrevidencia(dadoCadastralComplementar.getDataInicialIsencaoIrPrevidencia());
//		this.setDataFinalIsencaoIrPrevidencia(dadoCadastralComplementar.getDataFinalIsencaoIrPrevidencia());
		this.setDataFalecimento(dadoCadastralComplementar.getDataFalecimento());
		this.setTempoServidorProfessor(dadoCadastralComplementar.getTempoServidorProfessor());
		this.setEmProcessoDeAposentadoria(dadoCadastralComplementar.getEmProcessoDeAposentadoria() == null ? false
				: dadoCadastralComplementar.getEmProcessoDeAposentadoria());

		this.setDataInicialIsencaoIr(dadoCadastralComplementar.getDataInicialIsencaoIr());
		this.setDataFinalIsencaoIr(dadoCadastralComplementar.getDataFinalIsencaoIr());
		this.setDataInicialIsencaoPrevidencia(dadoCadastralComplementar.getDataInicialIsencaoPrevidencia());
		this.setDataFinalIsencaoPrevidencia(dadoCadastralComplementar.getDataFinalIsencaoPrevidencia());
		this.setPrevidenciaEspecial(dadoCadastralComplementar.getPrevidenciaEspecial());

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

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public String getFardamento() {
		return fardamento;
	}

	public void setFardamento(String fardamento) {
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

	public String getCondicaoRetornoTrabalho() {
		return condicaoRetornoTrabalho;
	}

	public void setCondicaoRetornoTrabalho(String condicaoRetornoTrabalho) {
		this.condicaoRetornoTrabalho = condicaoRetornoTrabalho;
	}

	public Instant getDataInicioDeficiencia() {
		return dataInicioDeficiencia;
	}

	public void setDataInicioDeficiencia(Instant dataInicioDeficiencia) {
		this.dataInicioDeficiencia = dataInicioDeficiencia;
	}

	public Instant getDataFimDeficiencia() {
		return dataFimDeficiencia;
	}

	public void setDataFimDeficiencia(Instant dataFimDeficiencia) {
		this.dataFimDeficiencia = dataFimDeficiencia;
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

	public String getTipoAposentadoria() {
		return tipoAposentadoria;
	}

	public void setTipoAposentadoria(String tipoAposentadoria) {
		this.tipoAposentadoria = tipoAposentadoria;
	}

	public Integer getProporcionalidade() {
		return proporcionalidade;
	}

	public void setProporcionalidade(Integer proporcionalidade) {
		this.proporcionalidade = proporcionalidade;
	}

	public String getTipoProporcao() {
		return tipoProporcao;
	}

	public void setTipoProporcao(String tipoProporcao) {
		this.tipoProporcao = tipoProporcao;
	}

	public Boolean getContribuicaoInss() {
		return contribuicaoInss;
	}

	public void setContribuicaoInss(Boolean contribuicaoInss) {
		this.contribuicaoInss = contribuicaoInss;
	}

	public Boolean getConsignadoBloqueado() {
		return consignadoBloqueado;
	}

	public void setConsignadoBloqueado(Boolean consignadoBloqueado) {
		this.consignadoBloqueado = consignadoBloqueado;
	}

	public Boolean getContribuicaoIr() {
		return contribuicaoIr;
	}

	public void setContribuicaoIr(Boolean contribuicaoIr) {
		this.contribuicaoIr = contribuicaoIr;
	}

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

}

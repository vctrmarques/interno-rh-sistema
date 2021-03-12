package com.rhlinkcon.payload.treinamentoSugerido;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class TreinamentoSugeridoRequest {

	private Long id;

	@NotNull
	private Long cursoId;

	@NotEmpty
	private String tipo;

	@NotEmpty
	private String instrutor;

	@NotEmpty
	private String motivo;

	@NotEmpty
	private String local;

	@NotEmpty
	private List<Long> funcionariosId;

	@NotNull
	private LocalDate dataInicio;

	@NotNull
	private LocalDate dataFinal;

	@NotNull
	private String situacao;

	private String informacoesAdicionais;
	
	private String turma;
	
	private String justificativa;
	
	private String resultado;
	
	private Date horario;
	
	private String quantidadeVagas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getInstrutor() {
		return instrutor;
	}

	public void setInstrutor(String instrutor) {
		this.instrutor = instrutor;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public List<Long> getFuncionariosId() {
		return funcionariosId;
	}

	public void setFuncionariosId(List<Long> funcionariosId) {
		this.funcionariosId = funcionariosId;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getInformacoesAdicionais() {
		return informacoesAdicionais;
	}

	public void setInformacoesAdicionais(String informacoesAdicionais) {
		this.informacoesAdicionais = informacoesAdicionais;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Date getHorario() {
		return horario;
	}

	public void setHorario(Date horario) {
		this.horario = horario;
	}

	public String getQuantidadeVagas() {
		return quantidadeVagas;
	}

	public void setQuantidadeVagas(String quantidadeVagas) {
		this.quantidadeVagas = quantidadeVagas;
	}

}
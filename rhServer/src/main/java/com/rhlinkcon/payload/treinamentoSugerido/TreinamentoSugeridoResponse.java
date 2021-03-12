package com.rhlinkcon.payload.treinamentoSugerido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.TreinamentoSugerido;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.curso.CursoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;

public class TreinamentoSugeridoResponse extends DadoCadastralResponse {

	private Long id;

	private CursoResponse curso;

	private String tipo;

	private String instrutor;

	private String motivo;

	private String local;

	private List<FuncionarioResponse> funcionarios;

	private LocalDate dataInicio;

	private LocalDate dataFinal;

	private String situacao;

	private String informacoesAdicionais;
	
	private String turma;
	
	private String justificativa;
	
	private String resultado;
	
	private Date horario;
	
	private String quantidadeVagas;

	public TreinamentoSugeridoResponse(TreinamentoSugerido treinamento) {
		this.id = treinamento.getId();
		this.curso = new CursoResponse(treinamento.getCurso());
		this.tipo = treinamento.getTipo().getLabel();
		this.instrutor = treinamento.getInstrutor();
		this.motivo = treinamento.getMotivo();
		this.local = treinamento.getLocal();
		this.dataInicio = treinamento.getDataInicio();
		this.dataFinal = treinamento.getDataFinal();
		this.situacao = treinamento.getSituacao().getLabel();
		this.informacoesAdicionais = treinamento.getInformacoesAdicionais();
		setAlteradoEm(treinamento.getUpdatedAt());
		setCriadoEm(treinamento.getCreatedAt());
		
		funcionarios = new ArrayList<>();
		if (Objects.nonNull(treinamento.getFuncionarios())) {
			for (Funcionario func : treinamento.getFuncionarios()) {
				funcionarios.add(new FuncionarioResponse(func));
			}
		}
		
		this.turma = treinamento.getTurma();
		this.justificativa = treinamento.getJustificativa();
		this.resultado = treinamento.getResultado();
		this.horario = treinamento.getHorario();
		this.quantidadeVagas = treinamento.getQuantidadeVagas();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CursoResponse getCurso() {
		return curso;
	}

	public void setCurso(CursoResponse curso) {
		this.curso = curso;
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

	public List<FuncionarioResponse> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<FuncionarioResponse> funcionarios) {
		this.funcionarios = funcionarios;
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

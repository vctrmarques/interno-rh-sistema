package com.rhlinkcon.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.treinamentoSugerido.TreinamentoSugeridoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Treinamento Sugerido")
@Table(name = "treinamento_sugerido")
public class TreinamentoSugerido extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "curso_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Curso curso;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoTreinamentoSugerido tipo;

	@NotEmpty
	private String instrutor;

	@NotEmpty
	private String motivo;

	@NotEmpty
	private String local;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "treinamento_sugerido_funcionario", joinColumns = @JoinColumn(name = "id_treinamento_sugerido"), inverseJoinColumns = @JoinColumn(name = "id_funcionario"))
	private List<Funcionario> funcionarios;

	@NotNull
	@Column(name = "data_inicio")
	private LocalDate dataInicio;

	@NotNull
	@Column(name = "data_final")
	private LocalDate dataFinal;

	@NotNull
	@Enumerated(EnumType.STRING)
	private SituacaoTreinamentoSugeridoEnum situacao;

	@Column(name = "informacoes_adicionais")
	private String informacoesAdicionais;

	private String turma;

	private String justificativa;

	private String resultado;

	private Date horario;

	@Column(name = "quantidade_vagas")
	private String quantidadeVagas;

	public TreinamentoSugerido() {

	}

	public TreinamentoSugerido(TreinamentoSugeridoRequest request) {
		this.id = request.getId();
		this.curso = new Curso(request.getCursoId());
		this.tipo = TipoTreinamentoSugerido.getEnumByString(request.getTipo());
		this.instrutor = request.getInstrutor();
		this.motivo = request.getMotivo();
		this.local = request.getLocal();
		List<Funcionario> f = new ArrayList<>();
		if (!request.getFuncionariosId().isEmpty()) {
			request.getFuncionariosId().forEach(fId -> {
				f.add(new Funcionario(fId));
			});
			this.setFuncionarios(f);
		}

		this.dataInicio = request.getDataInicio();
		this.dataFinal = request.getDataFinal();
		this.situacao = SituacaoTreinamentoSugeridoEnum.getEnumByString(request.getSituacao());
		this.informacoesAdicionais = request.getInformacoesAdicionais();
		this.turma = request.getTurma();
		this.justificativa = request.getJustificativa();
		this.resultado = request.getResultado();
		this.horario = request.getHorario();
		this.quantidadeVagas = request.getQuantidadeVagas();
	}

	public TreinamentoSugerido(Long treinamentoSugeridoId) {
		this.id = treinamentoSugeridoId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public TipoTreinamentoSugerido getTipo() {
		return tipo;
	}

	public void setTipo(TipoTreinamentoSugerido tipo) {
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

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
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

	public SituacaoTreinamentoSugeridoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoTreinamentoSugeridoEnum situacao) {
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

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}

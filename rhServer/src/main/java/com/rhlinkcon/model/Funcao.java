package com.rhlinkcon.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.funcao.FuncaoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Função")
@Table(name = "funcao")
public class Funcao extends UserDateAudit {

	private static final long serialVersionUID = 4440215695661565547L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "nome")
	private String nome;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "codigo")
	private String codigo;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cbo_id")
	private Cbo cbo;

	@NotNull
	@Column(name = "data_criacao")
	private Instant dataCriacao;

	@Column(name = "data_extincao")
	private Instant dataExtincao;

	@Column(name = "funcao_extinta")
	private Boolean funcaoExtinta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "natureza_funcao_id")
	private NaturezaFuncao naturezaFuncao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processo_funcao_id")
	private ProcessoFuncao processoFuncao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_profissional_id")
	private CategoriaProfissional categoriaProfissional;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "funcao_vinculo", joinColumns = @JoinColumn(name = "funcao_id"), inverseJoinColumns = @JoinColumn(name = "vinculo_id"))
	private Set<Vinculo> vinculos = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "funcao_curso", joinColumns = @JoinColumn(name = "funcao_id"), inverseJoinColumns = @JoinColumn(name = "curso_id"))
	private Set<Curso> cursos = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "funcao_habilidade", joinColumns = @JoinColumn(name = "funcao_id"), inverseJoinColumns = @JoinColumn(name = "habilidade_id"))
	private Set<Habilidade> habilidades = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "funcao_atividade", joinColumns = @JoinColumn(name = "funcao_id"), inverseJoinColumns = @JoinColumn(name = "atividade_id"))
	private Set<Atividade> atividades = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "funcao_requisito", joinColumns = @JoinColumn(name = "funcao_id"), inverseJoinColumns = @JoinColumn(name = "requisito_id"))
	private Set<Requisito> requisitos = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "funcao_verba", joinColumns = @JoinColumn(name = "funcao_id"), inverseJoinColumns = @JoinColumn(name = "verba_id"))
	private Set<Verba> verbas = new HashSet<>();

	@Column(name = "dedicacao_exclusiva")
	private Boolean dedicacaoExclusiva;

	@Column(name = "numero_lei")
	private Integer numeroLei;

	@Column(name = "data_lei")
	private Instant dataLei;

	@Enumerated(EnumType.STRING)
	@Column(name = "funcao_acumulavel")
	private FuncaoAcumulavelEnum funcaoAcumulavel;

	@Enumerated(EnumType.STRING)
	@Column(name = "contagem_tempo_especial")
	private ContagemTempoEspecialEnum contagemTempoEspecial;

	@Enumerated(EnumType.STRING)
	@Column(name = "motivo_lei")
	private MotivoLeiEnum motivoLei;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grupo_salarial_id")
	private GrupoSalarial grupoSalarial;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "centro_custo_id")
	private CentroCusto centroCusto;

	@OneToMany(mappedBy = "funcao", fetch = FetchType.LAZY)
	private List<FuncaoHistoricoLei> historicoLeis;

	public Funcao() {

	}

	public Funcao(Long id) {
		this.id = id;
	}

	public Funcao(FuncaoRequest funcaoRequest) {
		this.setNome(funcaoRequest.getNome());
		this.setCodigo(funcaoRequest.getCodigo());
		this.setDescricao(funcaoRequest.getDescricao());
		this.setDataExtincao(funcaoRequest.getDataExtincao());
		this.setDedicacaoExclusiva(funcaoRequest.getDedicacaoExclusiva());
		this.setNumeroLei(funcaoRequest.getNumeroLei());
		this.setDataLei(funcaoRequest.getDataLei());

		if (Objects.nonNull(funcaoRequest.getCentroCustoId()))
			this.setCentroCusto(new CentroCusto(funcaoRequest.getCentroCustoId()));

		if (Objects.nonNull(funcaoRequest.getFuncaoAcumulavel()))
			this.setFuncaoAcumulavel(FuncaoAcumulavelEnum.valueOf(funcaoRequest.getFuncaoAcumulavel()));

		if (Objects.nonNull(funcaoRequest.getContagemTempoEspecial()))
			this.setContagemTempoEspecial(ContagemTempoEspecialEnum.valueOf(funcaoRequest.getContagemTempoEspecial()));

		if (Objects.nonNull(funcaoRequest.getMotivoLei()))
			this.setMotivoLei(MotivoLeiEnum.valueOf(funcaoRequest.getMotivoLei()));
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Cbo getCbo() {
		return cbo;
	}

	public Instant getDataCriacao() {
		return dataCriacao;
	}

	public Instant getDataExtincao() {
		return dataExtincao;
	}

	public Boolean getFuncaoExtinta() {
		return funcaoExtinta;
	}

	public NaturezaFuncao getNaturezaFuncao() {
		return naturezaFuncao;
	}

	public ProcessoFuncao getProcessoFuncao() {
		return processoFuncao;
	}

	public CategoriaProfissional getCategoriaProfissional() {
		return categoriaProfissional;
	}

	public Set<Vinculo> getVinculos() {
		return vinculos;
	}

	public Boolean getDedicacaoExclusiva() {
		return dedicacaoExclusiva;
	}

	public Integer getNumeroLei() {
		return numeroLei;
	}

	public Instant getDataLei() {
		return dataLei;
	}

	public FuncaoAcumulavelEnum getFuncaoAcumulavel() {
		return funcaoAcumulavel;
	}

	public ContagemTempoEspecialEnum getContagemTempoEspecial() {
		return contagemTempoEspecial;
	}

	public MotivoLeiEnum getMotivoLei() {
		return motivoLei;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setCbo(Cbo cbo) {
		this.cbo = cbo;
	}

	public void setDataCriacao(Instant dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setDataExtincao(Instant dataExtincao) {
		this.dataExtincao = dataExtincao;
	}

	public void setFuncaoExtinta(Boolean funcaoExtinta) {
		this.funcaoExtinta = funcaoExtinta;
	}

	public void setNaturezaFuncao(NaturezaFuncao naturezaFuncao) {
		this.naturezaFuncao = naturezaFuncao;
	}

	public void setProcessoFuncao(ProcessoFuncao processoFuncao) {
		this.processoFuncao = processoFuncao;
	}

	public void setCategoriaProfissional(CategoriaProfissional categoriaProfissional) {
		this.categoriaProfissional = categoriaProfissional;
	}

	public void setVinculos(Set<Vinculo> vinculos) {
		this.vinculos = vinculos;
	}

	public void setDedicacaoExclusiva(Boolean dedicacaoExclusiva) {
		this.dedicacaoExclusiva = dedicacaoExclusiva;
	}

	public void setNumeroLei(Integer numeroLei) {
		this.numeroLei = numeroLei;
	}

	public void setDataLei(Instant dataLei) {
		this.dataLei = dataLei;
	}

	public void setFuncaoAcumulavel(FuncaoAcumulavelEnum funcaoAcumulavel) {
		this.funcaoAcumulavel = funcaoAcumulavel;
	}

	public void setContagemTempoEspecial(ContagemTempoEspecialEnum contagemTempoEspecial) {
		this.contagemTempoEspecial = contagemTempoEspecial;
	}

	public void setMotivoLei(MotivoLeiEnum motivoLei) {
		this.motivoLei = motivoLei;
	}

	public GrupoSalarial getGrupoSalarial() {
		return grupoSalarial;
	}

	public void setGrupoSalarial(GrupoSalarial grupoSalarial) {
		this.grupoSalarial = grupoSalarial;
	}

	public Set<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(Set<Curso> cursos) {
		this.cursos = cursos;
	}

	public Set<Habilidade> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(Set<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}

	public Set<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(Set<Atividade> atividades) {
		this.atividades = atividades;
	}

	public CentroCusto getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCusto centroCusto) {
		this.centroCusto = centroCusto;
	}

	public List<FuncaoHistoricoLei> getHistoricoLeis() {
		return historicoLeis;
	}

	public void setHistoricoLeis(List<FuncaoHistoricoLei> historicoLeis) {
		this.historicoLeis = historicoLeis;
	}

	public Set<Requisito> getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(Set<Requisito> requisitos) {
		this.requisitos = requisitos;
	}

	public Set<Verba> getVerbas() {
		return verbas;
	}

	public void setVerbas(Set<Verba> verbas) {
		this.verbas = verbas;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}

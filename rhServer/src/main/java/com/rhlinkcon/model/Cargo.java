package com.rhlinkcon.model;

import java.time.Instant;
import java.util.HashSet;
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
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.cargo.CargoRequest;
import com.rhlinkcon.util.AuditLabelClass;
import com.rhlinkcon.util.Utils;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Cargo")
@Table(name = "cargo")
public class Cargo extends UserDateAudit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4823721764116439053L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// aba principal
	@NotBlank
	@NotNull
	@Column(name = "nome", unique = true)
	private String nome;

	@Column(name = "codigo")
	private String codigo;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "extinto_em")
	private Instant extintoEm;

	@Column(name = "dedicacao_exclusiva")
	private Boolean dedicacaoExclusiva;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cbo_id")
	private Cbo cbo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "natureza_funcao_id")
	private NaturezaFuncao naturezaFuncao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processo_funcao_id")
	private ProcessoFuncao processoFuncao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_profissional_id")
	private CategoriaProfissional categoriaProfissional;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sindicato_id")
	private Sindicato sindicato;

	@Enumerated(EnumType.STRING)
	@Column(name = "contagem_tempo_especial")
	private ContagemTempoEspecialEnum contagemTempoEspecial;

	@Enumerated(EnumType.STRING)
	@Column(name = "motivo_lei")
	private MotivoLeiEnum motivoLei;

	@Column(name = "lei_respaldo")
	private String leiRespaldo;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "cargo_vinculo", joinColumns = @JoinColumn(name = "cargo_id"), inverseJoinColumns = @JoinColumn(name = "vinculo_id"))
	private Set<Vinculo> vinculos = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "cargo_verba", joinColumns = @JoinColumn(name = "cargo_id"), inverseJoinColumns = @JoinColumn(name = "verba_id"))
	private Set<Verba> verbas = new HashSet<>();

	// aba atividades

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "cargo_atividade", joinColumns = @JoinColumn(name = "cargo_id"), inverseJoinColumns = @JoinColumn(name = "atividade_id"))
	private Set<Atividade> atividades = new HashSet<>();

	// aba habilidades

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "cargo_habilidade", joinColumns = @JoinColumn(name = "cargo_id"), inverseJoinColumns = @JoinColumn(name = "habilidade_id"))
	private Set<Habilidade> habilidades = new HashSet<>();

	// aba cursos

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "cargo_curso", joinColumns = @JoinColumn(name = "cargo_id"), inverseJoinColumns = @JoinColumn(name = "curso_id"))
	private Set<Curso> cursos = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "grupo_salarial_id")
	private GrupoSalarial grupoSalarial;

	public Cargo() {

	}

	public Cargo(Long id) {
		this.id = id;
	}

	public Cargo(CargoRequest cargoRequest) {
		this.setNome(cargoRequest.getNome());
		this.setCodigo(cargoRequest.getCodigo());
		this.setDescricao(cargoRequest.getDescricao());
		this.setLeiRespaldo(cargoRequest.getLeiRespaldo());
		this.setDedicacaoExclusiva(Utils.setBool(cargoRequest.isDedicacaoExclusiva()));
	}

	public GrupoSalarial getGrupoSalarial() {
		return grupoSalarial;
	}

	public void setGrupoSalarial(GrupoSalarial grupoSalarial) {
		this.grupoSalarial = grupoSalarial;
	}

	public String getLeiRespaldo() {
		return leiRespaldo;
	}

	public void setLeiRespaldo(String leiRespaldo) {
		this.leiRespaldo = leiRespaldo;
	}

	public Boolean isDedicacaoExclusiva() {
		return dedicacaoExclusiva;
	}

	public void setDedicacaoExclusiva(Boolean dedicacaoExclusiva) {
		this.dedicacaoExclusiva = dedicacaoExclusiva;
	}

	public Cbo getCbo() {
		return cbo;
	}

	public void setCbo(Cbo cbo) {
		this.cbo = cbo;
	}

	public NaturezaFuncao getNaturezaFuncao() {
		return naturezaFuncao;
	}

	public void setNaturezaFuncao(NaturezaFuncao naturezaFuncao) {
		this.naturezaFuncao = naturezaFuncao;
	}

	public ProcessoFuncao getProcessoFuncao() {
		return processoFuncao;
	}

	public void setProcessoFuncao(ProcessoFuncao processoFuncao) {
		this.processoFuncao = processoFuncao;
	}

	public CategoriaProfissional getCategoriaProfissional() {
		return categoriaProfissional;
	}

	public void setCategoriaProfissional(CategoriaProfissional categoriaProfissional) {
		this.categoriaProfissional = categoriaProfissional;
	}

	public Sindicato getSindicato() {
		return sindicato;
	}

	public void setSindicato(Sindicato sindicato) {
		this.sindicato = sindicato;
	}

	public ContagemTempoEspecialEnum getContagemTempoEspecial() {
		return contagemTempoEspecial;
	}

	public void setContagemTempoEspecial(ContagemTempoEspecialEnum contagemTempoEspecial) {
		this.contagemTempoEspecial = contagemTempoEspecial;
	}

	public MotivoLeiEnum getMotivoLei() {
		return motivoLei;
	}

	public void setMotivoLei(MotivoLeiEnum motivoLei) {
		this.motivoLei = motivoLei;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Instant getExtintoEm() {
		return extintoEm;
	}

	public void setExtintoEm(Instant extintoEm) {
		this.extintoEm = extintoEm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Vinculo> getVinculos() {
		return vinculos;
	}

	public void setVinculos(Set<Vinculo> vinculos) {
		this.vinculos = vinculos;
	}

	public Set<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(Set<Atividade> atividades) {
		this.atividades = atividades;
	}

	public Set<Habilidade> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(Set<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}

	public Set<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(Set<Curso> cursos) {
		this.cursos = cursos;
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

	public Boolean getDedicacaoExclusiva() {
		return dedicacaoExclusiva;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}

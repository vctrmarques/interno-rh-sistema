package com.rhlinkcon.model;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.payload.avaliacaoDesempenho.AvaliacaoDesempenhoRequest;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Avaliação de Desempenho")
@Table(name = "avaliacao_desempenho")
public class AvaliacaoDesempenho extends UserDateAudit {

	private static final long serialVersionUID = -5923502530860236383L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_filial_id")
	private EmpresaFilial empresaFilial;

	@NotNull
	@Column(name = "cod_avaliacao")
	private String codAvaliacao;

	@NotNull
	@Column(name = "nome")
	private String nome;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "modelo")
	private AvaliacaoDesempenhoModeloEnum modelo;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "avaliacao_lotacao", joinColumns = @JoinColumn(name = "avaliacao_id"), inverseJoinColumns = @JoinColumn(name = "lotacao_id"))
	private List<Lotacao> lotacoes = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "avaliacao_cargo", joinColumns = @JoinColumn(name = "avaliacao_id"), inverseJoinColumns = @JoinColumn(name = "cargo_id"))
	private List<Cargo> cargos = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(name = "avaliacao_funcao", joinColumns = @JoinColumn(name = "avaliacao_id"), inverseJoinColumns = @JoinColumn(name = "funcao_id"))
	private List<Funcao> funcoes = new ArrayList<>();

	@OneToMany(mappedBy = "avaliacaoDesempenho", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AvaliacaoDesempenhoItem> itens = new ArrayList<>();

	public AvaliacaoDesempenho() {
	}

	public AvaliacaoDesempenho(AvaliacaoDesempenhoRequest avaliacaoDesempenhoRequest) {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmpresaFilial getEmpresaFilial() {
		return empresaFilial;
	}

	public void setEmpresaFilial(EmpresaFilial empresaFilial) {
		this.empresaFilial = empresaFilial;
	}

	public String getCodAvaliacao() {
		return codAvaliacao;
	}

	public void setCodAvaliacao(String codAvaliacao) {
		this.codAvaliacao = codAvaliacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public AvaliacaoDesempenhoModeloEnum getModelo() {
		return modelo;
	}

	public void setModelo(AvaliacaoDesempenhoModeloEnum modelo) {
		this.modelo = modelo;
	}

	public List<Lotacao> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(List<Lotacao> lotacoes) {
		this.lotacoes = lotacoes;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public List<AvaliacaoDesempenhoItem> getItens() {
		return itens;
	}

	public void setItens(List<AvaliacaoDesempenhoItem> itens) {
		this.itens = itens;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
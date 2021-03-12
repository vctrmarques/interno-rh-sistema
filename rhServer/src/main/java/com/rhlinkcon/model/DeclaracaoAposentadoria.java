package com.rhlinkcon.model;

import java.util.HashSet;
import java.util.List;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Declaração Aposentadoria")
@Table(name = "declaracao_aposentadoria")
public class DeclaracaoAposentadoria extends UserDateAudit {

	private static final long serialVersionUID = -4989429524867462929L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Long numero;

	@NotNull
	private Integer ano;

	private Boolean excluida;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "declaracao_aposentadoria_anexo", joinColumns = @JoinColumn(name = "declaracao_aposentadoria_id"), inverseJoinColumns = @JoinColumn(name = "anexo_id"))
	private List<Anexo> anexos;

	@OneToMany(mappedBy = "declaracaoAposentadoria")
	private Set<DeclaracaoAposentadoriaAverbacao> averbacoes = new HashSet<>();

	@OneToMany(mappedBy = "declaracaoAposentadoria")
	private Set<DeclaracaoAposentadoriaAssinatura> assinaturas = new HashSet<>();

	@Column(name = "numero_retificacao")
	private Long numeroRetificacao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "declaracao_aposentadoria_id")
	private DeclaracaoAposentadoria declaracaoAposentadoria;

	private Boolean arquivada;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_declaracao")
	private DeclaracaoAposentadoriaTipoEnum tipoDeclaracao;

	public DeclaracaoAposentadoria() {
	}

	public Long getNumeroRetificacao() {
		return numeroRetificacao;
	}

	public void setNumeroRetificacao(Long numeroRetificacao) {
		this.numeroRetificacao = numeroRetificacao;
	}

	public DeclaracaoAposentadoria getDeclaracaoAposentadoria() {
		return declaracaoAposentadoria;
	}

	public void setDeclaracaoAposentadoria(DeclaracaoAposentadoria declaracaoAposentadoria) {
		this.declaracaoAposentadoria = declaracaoAposentadoria;
	}

	public DeclaracaoAposentadoria(Long declaracaoAposentadoriaId) {
		this.id = declaracaoAposentadoriaId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Anexo> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<Anexo> anexos) {
		this.anexos = anexos;
	}

	public Set<DeclaracaoAposentadoriaAverbacao> getAverbacoes() {
		return averbacoes;
	}

	public void setAverbacoes(Set<DeclaracaoAposentadoriaAverbacao> averbacoes) {
		this.averbacoes = averbacoes;
	}

	public Set<DeclaracaoAposentadoriaAssinatura> getAssinaturas() {
		return assinaturas;
	}

	public void setAssinaturas(Set<DeclaracaoAposentadoriaAssinatura> assinaturas) {
		this.assinaturas = assinaturas;
	}

	public Boolean getExcluida() {
		return excluida;
	}

	public void setExcluida(Boolean excluida) {
		this.excluida = excluida;
	}

	public DeclaracaoAposentadoriaTipoEnum getTipoDeclaracao() {
		return tipoDeclaracao;
	}

	public void setTipoDeclaracao(DeclaracaoAposentadoriaTipoEnum tipoDeclaracao) {
		this.tipoDeclaracao = tipoDeclaracao;
	}

	public Boolean getArquivada() {
		return arquivada;
	}

	public void setArquivada(Boolean arquivada) {
		this.arquivada = arquivada;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.rhlinkcon.model;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.rhlinkcon.audit.AuditListener;
import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.util.AuditLabelClass;

@Entity
@EntityListeners(AuditListener.class)
@AuditLabelClass(label = "Avaliação de Desempenho Item")
@Table(name = "avaliacao_item")
public class AvaliacaoDesempenhoItem extends UserDateAudit {

	private static final long serialVersionUID = -2243475679954838672L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avaliacao_desempenho_id")
	private AvaliacaoDesempenho avaliacaoDesempenho;

	@NotNull
	@Column(name = "cod_item_avaliacao")
	private String codItemAvaliacao;

	@NotNull
	@Column(name = "nome")
	private String nome;

	@NotNull
	@Column(name = "seq_avaliacao")
	private String seqAvaliacao;

	@NotNull
	@Column(name = "descricao")
	private String descricao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_questao")
	private AvaliacaoDesempenhoItemTipoQuestaoEnum tipoQuestao;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "forma_avaliacao")
	private AvaliacaoDesempenhoItemFormaAvaliacaoEnum formaAvaliacao;

	public AvaliacaoDesempenhoItem() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AvaliacaoDesempenho getAvaliacaoDesempenho() {
		return avaliacaoDesempenho;
	}

	public void setAvaliacaoDesempenho(AvaliacaoDesempenho avaliacaoDesempenho) {
		this.avaliacaoDesempenho = avaliacaoDesempenho;
	}

	public String getCodItemAvaliacao() {
		return codItemAvaliacao;
	}

	public void setCodItemAvaliacao(String codItemAvaliacao) {
		this.codItemAvaliacao = codItemAvaliacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSeqAvaliacao() {
		return seqAvaliacao;
	}

	public void setSeqAvaliacao(String seqAvaliacao) {
		this.seqAvaliacao = seqAvaliacao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public AvaliacaoDesempenhoItemTipoQuestaoEnum getTipoQuestao() {
		return tipoQuestao;
	}

	public void setTipoQuestao(AvaliacaoDesempenhoItemTipoQuestaoEnum tipoQuestao) {
		this.tipoQuestao = tipoQuestao;
	}

	public AvaliacaoDesempenhoItemFormaAvaliacaoEnum getFormaAvaliacao() {
		return formaAvaliacao;
	}

	public void setFormaAvaliacao(AvaliacaoDesempenhoItemFormaAvaliacaoEnum formaAvaliacao) {
		this.formaAvaliacao = formaAvaliacao;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
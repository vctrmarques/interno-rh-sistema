package com.rhlinkcon.payload.avaliacaoDesempenho;

import com.rhlinkcon.model.AvaliacaoDesempenho;
import com.rhlinkcon.model.AvaliacaoDesempenhoItemFormaAvaliacaoEnum;
import com.rhlinkcon.model.AvaliacaoDesempenhoItemTipoQuestaoEnum;

import javax.validation.constraints.NotNull;

public class AvaliacaoDesempenhoItemRequest {

	private Long id;

	@NotNull
	private AvaliacaoDesempenho avaliacaoDesempenho;

	@NotNull
	private String codItemAvaliacao;

	@NotNull
	private String nome;

	@NotNull
	private String seqAvaliacao;

	@NotNull
	private String descricao;

	@NotNull
	private AvaliacaoDesempenhoItemTipoQuestaoEnum tipoQuestao;

	@NotNull
	private AvaliacaoDesempenhoItemFormaAvaliacaoEnum formaAvaliacao;

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
}
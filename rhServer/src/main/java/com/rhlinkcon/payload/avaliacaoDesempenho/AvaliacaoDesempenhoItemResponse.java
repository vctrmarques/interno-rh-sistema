package com.rhlinkcon.payload.avaliacaoDesempenho;

import com.rhlinkcon.model.AvaliacaoDesempenhoItem;
import com.rhlinkcon.model.AvaliacaoDesempenhoItemFormaAvaliacaoEnum;
import com.rhlinkcon.model.AvaliacaoDesempenhoItemTipoQuestaoEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class AvaliacaoDesempenhoItemResponse extends DadoCadastralResponse {

	private Long id;

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
	private String tipoQuestaoLabel;

	@NotNull
	private AvaliacaoDesempenhoItemFormaAvaliacaoEnum formaAvaliacao;

	@NotNull
	private String formaAvaliacaoLabel;

	public AvaliacaoDesempenhoItemResponse(AvaliacaoDesempenhoItem itemRequest) {
		setAvaliacaoDesempenhoItem(itemRequest);
	}

	public AvaliacaoDesempenhoItemResponse(AvaliacaoDesempenhoItem itemRequest, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setAvaliacaoDesempenhoItem(itemRequest);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setAvaliacaoDesempenhoItem(AvaliacaoDesempenhoItem itemRequest) {
		this.setId(itemRequest.getId());
		this.setCodItemAvaliacao(itemRequest.getCodItemAvaliacao());
		this.setNome(itemRequest.getNome());
		this.setSeqAvaliacao(itemRequest.getSeqAvaliacao());
		this.setDescricao(itemRequest.getDescricao());
		this.setTipoQuestao(itemRequest.getTipoQuestao());
		this.setTipoQuestaoLabel(itemRequest.getTipoQuestao().getLabel());
		this.setFormaAvaliacao(itemRequest.getFormaAvaliacao());
		this.setFormaAvaliacaoLabel(itemRequest.getFormaAvaliacao().getLabel());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTipoQuestaoLabel() {
		return tipoQuestaoLabel;
	}

	public void setTipoQuestaoLabel(String tipoQuestaoLabel) {
		this.tipoQuestaoLabel = tipoQuestaoLabel;
	}

	public AvaliacaoDesempenhoItemFormaAvaliacaoEnum getFormaAvaliacao() {
		return formaAvaliacao;
	}

	public void setFormaAvaliacao(AvaliacaoDesempenhoItemFormaAvaliacaoEnum formaAvaliacao) {
		this.formaAvaliacao = formaAvaliacao;
	}

	public String getFormaAvaliacaoLabel() {
		return formaAvaliacaoLabel;
	}

	public void setFormaAvaliacaoLabel(String formaAvaliacaoLabel) {
		this.formaAvaliacaoLabel = formaAvaliacaoLabel;
	}
}
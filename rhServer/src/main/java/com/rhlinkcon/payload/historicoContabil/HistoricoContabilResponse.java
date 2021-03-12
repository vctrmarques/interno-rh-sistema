package com.rhlinkcon.payload.historicoContabil;

import java.time.Instant;

import com.rhlinkcon.model.HistoricoContabil;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class HistoricoContabilResponse extends DadoCadastralResponse {
	private Long id;

	private String descricao;

	private String codigo;

	public HistoricoContabilResponse(HistoricoContabil historicoContabil) {
		setHistoricoContabil(historicoContabil);
	}

	public HistoricoContabilResponse(HistoricoContabil historicoContabil, Instant criadoEm, String criadoPor,
			Instant alteradoEm, String alteradoPor) {
		setHistoricoContabil(historicoContabil);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setHistoricoContabil(HistoricoContabil historicoContabil) {
		this.setId(historicoContabil.getId());
		this.setDescricao(historicoContabil.getDescricao());
		this.setCodigo(historicoContabil.getCodigo());

	}

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}

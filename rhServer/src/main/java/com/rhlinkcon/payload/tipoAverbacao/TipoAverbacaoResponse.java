package com.rhlinkcon.payload.tipoAverbacao;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.TipoAverbacao;
import com.rhlinkcon.payload.DadoCadastralResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoAverbacaoResponse extends DadoCadastralResponse {

	private Long id;
	
	private Integer codigo;

	private String descricao;
	
	private String formaAverbacao;

	private String deducaoAverbacao;

	public TipoAverbacaoResponse(TipoAverbacao tipoAverbacao) {
		setTipoAverbacao(tipoAverbacao);
	}

	public TipoAverbacaoResponse(TipoAverbacao tipoAverbacao, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		setTipoAverbacao(tipoAverbacao);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setTipoAverbacao(TipoAverbacao tipoAverbacao) {
		this.setId(tipoAverbacao.getId());
		this.setCodigo(tipoAverbacao.getCodigo());
		this.setDescricao(tipoAverbacao.getDescricao());
		
		if (!tipoAverbacao.getFormaAverbacao().getLabel().isEmpty())
			this.setFormaAverbacao(tipoAverbacao.getFormaAverbacao().getLabel());

		if (!tipoAverbacao.getDeducaoAverbacao().getLabel().isEmpty())
			this.setDeducaoAverbacao(tipoAverbacao.getDeducaoAverbacao().getLabel());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFormaAverbacao() {
		return formaAverbacao;
	}

	public void setFormaAverbacao(String formaAverbacao) {
		this.formaAverbacao = formaAverbacao;
	}

	public String getDeducaoAverbacao() {
		return deducaoAverbacao;
	}

	public void setDeducaoAverbacao(String deducaoAverbacao) {
		this.deducaoAverbacao = deducaoAverbacao;
	}

}

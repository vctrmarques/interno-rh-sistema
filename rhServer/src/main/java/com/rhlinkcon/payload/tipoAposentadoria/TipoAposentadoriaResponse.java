package com.rhlinkcon.payload.tipoAposentadoria;

import java.time.Instant;

import com.rhlinkcon.model.TipoAposentadoria;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class TipoAposentadoriaResponse extends DadoCadastralResponse {
	
	private Long id;

	private Integer codigo;

	private String descricao;

	public TipoAposentadoriaResponse(TipoAposentadoria tipoAposentadoria) {
		this.setId(tipoAposentadoria.getId());
		this.setCodigo(tipoAposentadoria.getCodigo());
		this.setDescricao(tipoAposentadoria.getDescricao());
	}
	
	public TipoAposentadoriaResponse(TipoAposentadoria tipoFolha, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(tipoFolha.getId());
		this.setCodigo(tipoFolha.getCodigo());
		this.setDescricao(tipoFolha.getDescricao());
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
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
	
	

}

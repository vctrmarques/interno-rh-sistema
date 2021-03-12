package com.rhlinkcon.payload.convenio;

import java.time.Instant;

//import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.Convenio;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class ConvenioResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;


	public ConvenioResponse(Convenio convenio) {
		this.setId(convenio.getId());
		this.setDescricao(convenio.getDescricao());
	}

	public ConvenioResponse(Convenio convenio, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(convenio.getId());
		this.setDescricao(convenio.getDescricao());
		
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

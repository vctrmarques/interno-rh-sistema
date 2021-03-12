package com.rhlinkcon.payload.contaContabilSimples;

import java.time.Instant;

import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.ContaContabilSimples;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class ContaContabilSimplesResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;


	public ContaContabilSimplesResponse(ContaContabilSimples contaContabil) {
		this.setId(contaContabil.getId());
		this.setCodigo(contaContabil.getCodigo());
		this.setDescricao(contaContabil.getDescricao());
	}

	public ContaContabilSimplesResponse(ContaContabilSimples contaContabil, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(contaContabil.getId());
		this.setCodigo(contaContabil.getCodigo());
		this.setDescricao(contaContabil.getDescricao());
		
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

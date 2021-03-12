package com.rhlinkcon.payload.etapa;

import java.time.Instant;

//import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.model.Etapa;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class EtapaResponse extends DadoCadastralResponse {

	private Long id;
	
	private Integer codigo;

	private String descricao;

	private String processo;

	public EtapaResponse(Etapa etapa) {
		this.setId(etapa.getId());
		this.setCodigo(etapa.getCodigo());
		this.setDescricao(etapa.getDescricao());

		if (!etapa.getProcesso().getLabel().isEmpty())
			this.setProcesso(etapa.getProcesso().getLabel());
	}

	public EtapaResponse(Etapa etapa, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		this.setId(etapa.getId());
		this.setCodigo(etapa.getCodigo());
		this.setDescricao(etapa.getDescricao());

		if (!etapa.getProcesso().getLabel().isEmpty())
			this.setProcesso(etapa.getProcesso().getLabel());

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

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

}

package com.rhlinkcon.payload.tipoProcessamento;

import java.util.ArrayList;
import java.util.List;

import com.rhlinkcon.model.TipoProcessamento;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.verba.VerbaResponse;

public class TipoProcessamentoResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;

	private List<VerbaResponse> verbas = new ArrayList<VerbaResponse>();

	public TipoProcessamentoResponse() {
	}

	public TipoProcessamentoResponse(TipoProcessamento tipoProcessamento) {
		setId(tipoProcessamento.getId());
		setDescricao(tipoProcessamento.getDescricao());
		setCodigo(tipoProcessamento.getCodigo());
		tipoProcessamento.getVerbas().forEach(verba -> {
			VerbaResponse vr = new VerbaResponse();
			vr.setId(verba.getId());
			vr.setDescricaoVerba(verba.getDescricaoVerba());
			vr.setTipoVerba(verba.getTipoVerba().toString());
			vr.setCodigo(verba.getCodigo());
			this.verbas.add(vr);
		});
		setCriadoEm(tipoProcessamento.getCreatedAt());
		setAlteradoEm(tipoProcessamento.getUpdatedAt());
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

	public List<VerbaResponse> getVerbas() {
		return verbas;
	}

	public void setVerbas(List<VerbaResponse> verbas) {
		this.verbas = verbas;
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

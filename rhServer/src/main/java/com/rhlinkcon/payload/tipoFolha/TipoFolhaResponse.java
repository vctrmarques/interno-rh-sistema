package com.rhlinkcon.payload.tipoFolha;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rhlinkcon.model.TipoFolha;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.verba.VerbaResponse;

public class TipoFolhaResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;
	
	private List<VerbaResponse> verbas;


	public TipoFolhaResponse(TipoFolha tipoFolha) {
		this.setId(tipoFolha.getId());
		this.setCodigo(tipoFolha.getCodigo());
		this.setDescricao(tipoFolha.getDescricao());
		
		verbas = new ArrayList<>();
		if (Objects.nonNull(tipoFolha.getVerbas())) {
			for (Verba ve : tipoFolha.getVerbas()) {
				verbas.add(new VerbaResponse(ve));
			}
		}
	}

	public TipoFolhaResponse(TipoFolha tipoFolha, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(tipoFolha.getId());
		this.setCodigo(tipoFolha.getCodigo());
		this.setDescricao(tipoFolha.getDescricao());
		
		verbas = new ArrayList<>();
		if (Objects.nonNull(tipoFolha.getVerbas())) {
			for (Verba ve : tipoFolha.getVerbas()) {
				verbas.add(new VerbaResponse(ve));
			}
		}
		
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

	public List<VerbaResponse> getVerbas() {
		return verbas;
	}

	public void setVerbas(List<VerbaResponse> verbas) {
		this.verbas = verbas;
	}

}

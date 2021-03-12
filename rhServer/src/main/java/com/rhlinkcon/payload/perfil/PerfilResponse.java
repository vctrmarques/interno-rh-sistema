package com.rhlinkcon.payload.perfil;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Papel;
import com.rhlinkcon.model.Perfil;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.papel.PapelResponse;
import com.rhlinkcon.util.Projecao;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PerfilResponse extends DadoCadastralResponse {

	private Long id;

	private String nome;

	private List<PapelResponse> papeis;

	public PerfilResponse() {

	}

	public PerfilResponse(Perfil perfil) {
		this.id = perfil.getId();
		this.nome = perfil.getNome();
		this.papeis = new ArrayList<PapelResponse>();
		for (Papel papel : perfil.getPapeis())
			this.papeis.add(new PapelResponse(papel, Projecao.COMPLETA));
		setCriadoEm(perfil.getCreatedAt());
		setAlteradoEm(perfil.getUpdatedAt());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<PapelResponse> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<PapelResponse> papeis) {
		this.papeis = papeis;
	}

}

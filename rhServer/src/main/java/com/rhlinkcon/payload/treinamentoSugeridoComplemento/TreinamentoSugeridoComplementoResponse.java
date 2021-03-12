package com.rhlinkcon.payload.treinamentoSugeridoComplemento;

import com.rhlinkcon.model.TreinamentoSugeridoComplemento;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.payload.treinamentoSugerido.TreinamentoSugeridoResponse;

public class TreinamentoSugeridoComplementoResponse extends DadoCadastralResponse{
	
	private Long id;

	private TreinamentoSugeridoResponse treinamentoSugerido;

	private String local;

	private String endereco;
	
	private String complemento;
	
	private String bairro;
	
	private MunicipioResponse municipio;
	
	public TreinamentoSugeridoComplementoResponse(TreinamentoSugeridoComplemento treinamento) {
		this.id = treinamento.getId();
		this.treinamentoSugerido = new TreinamentoSugeridoResponse(treinamento.getTreinamentoSugerido());
		this.local = treinamento.getLocal();
		this.endereco = treinamento.getEndereco();
		this.complemento = treinamento.getComplemento();
		this.bairro = treinamento.getBairro();
		this.municipio = new MunicipioResponse(treinamento.getMunicipio());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TreinamentoSugeridoResponse getTreinamentoSugerido() {
		return treinamentoSugerido;
	}

	public void setTreinamentoSugerido(TreinamentoSugeridoResponse treinamentoSugerido) {
		this.treinamentoSugerido = treinamentoSugerido;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public MunicipioResponse getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioResponse municipio) {
		this.municipio = municipio;
	}
}

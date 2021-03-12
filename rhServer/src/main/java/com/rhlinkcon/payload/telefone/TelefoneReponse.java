package com.rhlinkcon.payload.telefone;

import com.rhlinkcon.model.Telefone;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class TelefoneReponse extends DadoCadastralResponse{

	private Long id;
	
	private String numero;
	
	private String tipo;
	
	public TelefoneReponse(Telefone obj) {
		setId(obj.getId());
		setNumero(obj.getNumero());
		setTipo(obj.getTipo().getLabel());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}

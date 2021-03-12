package com.rhlinkcon.payload.declaracaoExServidor;

import java.time.Instant;

public class DeclaracaoExServidorDadosFuncionaisRequest {

	private Long id;
	
	private Long declaracaoExServidorId;

	private Long cargoId;
	
	private String atoNomeacao;
	
	private Instant dataEntrada;
	
	private Instant dataDiarioOficialEntrada;
	
	private Instant dataEncerramento;
	
	private String atoEncerramento;

	private Instant dataDiarioOficialEncerramento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeclaracaoExServidorId() {
		return declaracaoExServidorId;
	}

	public void setDeclaracaoExServidorId(Long declaracaoExServidorId) {
		this.declaracaoExServidorId = declaracaoExServidorId;
	}

	public Long getCargoId() {
		return cargoId;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	public String getAtoNomeacao() {
		return atoNomeacao;
	}

	public void setAtoNomeacao(String atoNomeacao) {
		this.atoNomeacao = atoNomeacao;
	}

	public Instant getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Instant dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Instant getDataDiarioOficialEntrada() {
		return dataDiarioOficialEntrada;
	}

	public void setDataDiarioOficialEntrada(Instant dataDiarioOficialEntrada) {
		this.dataDiarioOficialEntrada = dataDiarioOficialEntrada;
	}

	public Instant getDataEncerramento() {
		return dataEncerramento;
	}

	public void setDataEncerramento(Instant dataEncerramento) {
		this.dataEncerramento = dataEncerramento;
	}

	public String getAtoEncerramento() {
		return atoEncerramento;
	}

	public void setAtoEncerramento(String atoEncerramento) {
		this.atoEncerramento = atoEncerramento;
	}

	public Instant getDataDiarioOficialEncerramento() {
		return dataDiarioOficialEncerramento;
	}

	public void setDataDiarioOficialEncerramento(Instant dataDiarioOficialEncerramento) {
		this.dataDiarioOficialEncerramento = dataDiarioOficialEncerramento;
	}
	
	
	
}

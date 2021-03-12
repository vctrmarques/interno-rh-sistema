package com.rhlinkcon.payload.declaracaoExServidor;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Objects;

import com.rhlinkcon.model.DeclaracaoExServidorDadosFuncionais;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.cargo.CargoResponse;
import com.rhlinkcon.util.Projecao;

public class DeclaracaoExServidorDadosFuncionaisResponse extends DadoCadastralResponse {

	private Long id;
	
	private DeclaracaoExServidorResponse declaracaoExServidor;

	private CargoResponse cargo;
	
	private String atoNomeacao;
	
	private Instant dataEntrada;
	
	private Instant dataDiarioOficialEntrada;
	
	private Instant dataEncerramento;
	
	private String atoEncerramento;

	private Instant dataDiarioOficialEncerramento;
	
	public DeclaracaoExServidorDadosFuncionaisResponse(DeclaracaoExServidorDadosFuncionais obj) {
		
		setId(obj.getId());
		setDeclaracaoExServidor(new DeclaracaoExServidorResponse(obj.getDeclaracaoExServidor(), Projecao.BASICA));
		
		if(Objects.nonNull(obj.getCargo()))
			setCargo(new CargoResponse(obj.getCargo()));
		
		if(Objects.nonNull(obj.getAtoNomeacao()))
			setAtoNomeacao(obj.getAtoNomeacao());
		
		if(Objects.nonNull(obj.getDataEntrada()))
			setDataEntrada(obj.getDataEntrada().atStartOfDay().toInstant(ZoneOffset.UTC));
		
		if(Objects.nonNull(obj.getDataDiarioOficialEntrada()))
			setDataDiarioOficialEntrada(obj.getDataDiarioOficialEntrada().atStartOfDay().toInstant(ZoneOffset.UTC));
		
		if(Objects.nonNull(obj.getDataEncerramento()))
			setDataEncerramento(obj.getDataEncerramento().atStartOfDay().toInstant(ZoneOffset.UTC));
		
		if(Objects.nonNull(obj.getAtoEncerramento()))
			setAtoEncerramento(obj.getAtoEncerramento());
		
		if(Objects.nonNull(obj.getDataDiarioOficialEncerramento()))
			setDataDiarioOficialEncerramento(obj.getDataDiarioOficialEncerramento().atStartOfDay().toInstant(ZoneOffset.UTC));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeclaracaoExServidorResponse getDeclaracaoExServidor() {
		return declaracaoExServidor;
	}

	public void setDeclaracaoExServidor(DeclaracaoExServidorResponse declaracaoExServidor) {
		this.declaracaoExServidor = declaracaoExServidor;
	}

	public CargoResponse getCargo() {
		return cargo;
	}

	public void setCargo(CargoResponse cargo) {
		this.cargo = cargo;
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

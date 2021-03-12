package com.rhlinkcon.payload.folhaPagamento;

import java.util.HashMap;
import java.util.Map;

import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.verba.VerbaResponse;

public class FolhaPagamentoProcessamentoResponse {
	
	private String nomeFuncionario;
	
	private Map<VerbaResponse, Object> verbasProcessadas;
	
	private FuncionarioResponse funcionario;

	public FolhaPagamentoProcessamentoResponse() {
		this.verbasProcessadas = new HashMap<VerbaResponse, Object>();
	}
	
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public Map<VerbaResponse, Object> getVerbasProcessadas() {
		return verbasProcessadas;
	}

	public void setVerbasProcessadas(Map<VerbaResponse, Object> verbasProcessadas) {
		this.verbasProcessadas = verbasProcessadas;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}
	
	

}

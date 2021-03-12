package com.rhlinkcon.payload.funcionario;

import java.util.List;;

public class FuncionarioVerbaRequest {
	
	private FuncionarioRequest funcionario;

	private List<FuncionarioVerbasRequest> verbasFuncionario;

	public List<FuncionarioVerbasRequest> getVerbasFuncionario() {
		return verbasFuncionario;
	}

	public void setVerbasFuncionario(List<FuncionarioVerbasRequest> verbasFuncionario) {
		this.verbasFuncionario = verbasFuncionario;
	}

	public FuncionarioRequest getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioRequest funcionario) {
		this.funcionario = funcionario;
	}

	
}

package com.rhlinkcon.payload.recadastramento;

public class RecadastroRequest {

	private Long recadastramentoId;
	
	private Long funcionarioId;
	
	private Long pensaoId;

	public Long getRecadastramentoId() {
		return recadastramentoId;
	}

	public void setRecadastramentoId(Long recadastramentoId) {
		this.recadastramentoId = recadastramentoId;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Long getPensaoId() {
		return pensaoId;
	}

	public void setPensaoId(Long pensaoId) {
		this.pensaoId = pensaoId;
	}
	
}

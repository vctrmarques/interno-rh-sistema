package com.rhlinkcon.payload.pensionista;

public class PensionistaCreateRequest {

	private PensionistaRequest pensionista;
	
	private PensaoPrevidenciariaPagamentoRequest pensaoPrevidenciariaPagamento;

	public PensionistaRequest getPensaoPrevidenciaria() {
		return pensionista;
	}

	public void setPensaoPrevidenciaria(PensionistaRequest pensionista) {
		this.pensionista = pensionista;
	}

	public PensaoPrevidenciariaPagamentoRequest getPensaoPrevidenciariaPagamento() {
		return pensaoPrevidenciariaPagamento;
	}

	public void setPensaoPrevidenciariaPagamento(PensaoPrevidenciariaPagamentoRequest pensaoPrevidenciariaPagamento) {
		this.pensaoPrevidenciariaPagamento = pensaoPrevidenciariaPagamento;
	}
	
}

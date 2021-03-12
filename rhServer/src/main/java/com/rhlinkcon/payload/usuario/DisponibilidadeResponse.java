package com.rhlinkcon.payload.usuario;


public class DisponibilidadeResponse {
    private Boolean disponivel;

    public DisponibilidadeResponse(Boolean disponivel) {
        this.disponivel = disponivel;
    }

	public Boolean getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(Boolean disponivel) {
		this.disponivel = disponivel;
	}
}
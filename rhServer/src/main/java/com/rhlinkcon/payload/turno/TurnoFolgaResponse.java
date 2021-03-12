package com.rhlinkcon.payload.turno;


import com.rhlinkcon.model.TurnoFolga;
import com.rhlinkcon.model.TurnoFolgaDiaEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class TurnoFolgaResponse extends DadoCadastralResponse {

	private Long id;

	private TurnoFolgaDiaEnum dia;
	
	private String stringDia;

	public TurnoFolgaResponse() {
	}

	public TurnoFolgaResponse(TurnoFolga turnoFolga) {
		setId(turnoFolga.getId());
		setDia(turnoFolga.getDia());
		setStringDia(turnoFolga.getDia().getLabel());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TurnoFolgaDiaEnum getDia() {
		return dia;
	}

	public void setDia(TurnoFolgaDiaEnum dia) {
		this.dia = dia;
	}

	public String getStringDia() {
		return stringDia;
	}

	public void setStringDia(String stringDia) {
		this.stringDia = stringDia;
	}
	
	

}

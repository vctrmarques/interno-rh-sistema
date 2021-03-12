package com.rhlinkcon.payload.turno;


import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TurnoRequest {

	private Long id;

	@NotNull
	@NotBlank
	private String codigo;
	
	@NotNull
	private Date dataInicio;
	
	@NotNull
	private Date dataFim;
	
	private boolean horarioFlexivel;
	
	private Date horarioFlexivelInicio;
	
	private Date horarioFlexivelFim;
	
	private Date jornada;
	
	private boolean intervaloFlexivel;
	
	private Date intervalo;
	
	private Date intervaloFlexivelInicio;
	
	private Date intervaloFlexivelFim;

	private List<TurnoFolgaRequest> turnoFolga;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public boolean isHorarioFlexivel() {
		return horarioFlexivel;
	}

	public void setHorarioFlexivel(boolean horarioFlexivel) {
		this.horarioFlexivel = horarioFlexivel;
	}

	public Date getHorarioFlexivelInicio() {
		return horarioFlexivelInicio;
	}

	public void setHorarioFlexivelInicio(Date horarioFlexivelInicio) {
		this.horarioFlexivelInicio = horarioFlexivelInicio;
	}

	public Date getHorarioFlexivelFim() {
		return horarioFlexivelFim;
	}

	public void setHorarioFlexivelFim(Date horarioFlexivelFim) {
		this.horarioFlexivelFim = horarioFlexivelFim;
	}

	public Date getJornada() {
		return jornada;
	}

	public void setJornada(Date jornada) {
		this.jornada = jornada;
	}

	public boolean isIntervaloFlexivel() {
		return intervaloFlexivel;
	}

	public void setIntervaloFlexivel(boolean intervaloFlexivel) {
		this.intervaloFlexivel = intervaloFlexivel;
	}

	public Date getIntervaloFlexivelInicio() {
		return intervaloFlexivelInicio;
	}

	public void setIntervaloFlexivelInicio(Date intervaloFlexivelInicio) {
		this.intervaloFlexivelInicio = intervaloFlexivelInicio;
	}

	public Date getIntervaloFlexivelFim() {
		return intervaloFlexivelFim;
	}

	public void setIntervaloFlexivelFim(Date intervaloFlexivelFim) {
		this.intervaloFlexivelFim = intervaloFlexivelFim;
	}

	public List<TurnoFolgaRequest> getTurnoFolga() {
		return turnoFolga;
	}

	public void setTurnoFolga(List<TurnoFolgaRequest> turnoFolga) {
		this.turnoFolga = turnoFolga;
	}

	public Date getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Date intervalo) {
		this.intervalo = intervalo;
	}
	
	

}

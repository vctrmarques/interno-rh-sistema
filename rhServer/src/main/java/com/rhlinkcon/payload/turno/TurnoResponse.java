package com.rhlinkcon.payload.turno;


import java.util.Date;
import java.util.List;

import com.rhlinkcon.model.Turno;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class TurnoResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private boolean horarioFlexivel;
	
	private Date horarioFlexivelInicio;
	
	private Date horarioFlexivelFim;
	
	private Date jornada;
	
	private boolean intervaloFlexivel;
	
	private Date intervalo;
	
	private Date intervaloFlexivelInicio;
	
	private Date intervaloFlexivelFim;
	
	private boolean ativo;
	
	List<TurnoFolgaResponse> turnoFolgaResponse;
	
	public TurnoResponse() {
	}

	public TurnoResponse(Turno turno) {
		populate(turno);
		
	}
	
	public TurnoResponse(Turno turno, List<TurnoFolgaResponse> listTurnoFolgaResponse) {
		populate(turno);
		setTurnoFolgaResponse(listTurnoFolgaResponse);
	}
	
	private void populate(Turno turno) {
		setId(turno.getId());
		setCodigo(turno.getCodigo());
		setDataInicio(turno.getDataInicio());
		setDataFim(turno.getDataFim());
		setHorarioFlexivel(turno.isHorarioFlexivel());
		setHorarioFlexivelInicio(turno.getHorarioFlexivelInicio());
		setHorarioFlexivelFim(turno.getHorarioFlexivelFim());
		setJornada(turno.getJornada());
		setIntervaloFlexivel(turno.isIntervaloFlexivel());
		setIntervalo(turno.getIntervalo());
		setIntervaloFlexivelInicio(turno.getIntervaloFlexivelInicio());
		setIntervaloFlexivelFim(turno.getIntervaloFlexivelFim());
		setCriadoEm(turno.getCreatedAt());
		setAlteradoEm(turno.getUpdatedAt());
		
		Date dataAtual = new Date();
		boolean ativo = (turno.getDataInicio().before(dataAtual)) && (dataAtual.before(turno.getDataFim()));
		setAtivo(ativo);
	}

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

	public List<TurnoFolgaResponse> getTurnoFolgaResponse() {
		return turnoFolgaResponse;
	}

	public void setTurnoFolgaResponse(List<TurnoFolgaResponse> turnoFolgaResponse) {
		this.turnoFolgaResponse = turnoFolgaResponse;
	}

	public Date getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Date intervalo) {
		this.intervalo = intervalo;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	
	
	
	

}

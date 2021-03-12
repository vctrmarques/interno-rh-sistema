package com.rhlinkcon.payload.frequencia;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import com.rhlinkcon.model.Frequencia;
import com.rhlinkcon.model.MesEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.falta.FaltaResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.util.DateUtils;
import com.rhlinkcon.util.Projecao;

public class FrequenciaResponse extends DadoCadastralResponse {
	private Long id;

	private FuncionarioResponse funcionario;

	private LocalDateTime horasTrabalhadas;

	private LocalDate data;

	private LocalDateTime horaExtra;

	private LocalDateTime entradaUm;

	private LocalDateTime saidaUm;

	private LocalDateTime entradaDois;

	private LocalDateTime saidaDois;

	private FaltaResponse falta;
	
	private MesEnum mes;
	
	private String ano;
	
	private String horasExercidas;
	private String horasExtrasExercidas;
	
	public FrequenciaResponse(Frequencia frequencia, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.id = frequencia.getId();
			this.horasTrabalhadas = frequencia.getHorasTrabalhadas();
			this.horaExtra = frequencia.getHoraExtra();
			this.entradaUm = frequencia.getEntradaUm();
			this.saidaUm = frequencia.getSaidaUm();
			this.entradaDois = frequencia.getEntradaDois();
			this.saidaDois = frequencia.getSaidaDois();
			this.data = frequencia.getData();
			if (Objects.nonNull(frequencia.getFalta()))
				this.falta = new FaltaResponse(frequencia.getFalta());
			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
				this.funcionario = new FuncionarioResponse(frequencia.getFuncionario());
				setCriadoEm(frequencia.getCreatedAt());
				setAlteradoEm(frequencia.getUpdatedAt());
			}
		}
	}
	

	public FrequenciaResponse(Duration horasExercidas, Duration horaExtra, MesEnum mes, String ano) {
		if(Objects.nonNull(horasExercidas))
			this.horasExercidas =  DateUtils.formatDuration(horasExercidas);
		if(Objects.nonNull(horaExtra))
			this.horasExtrasExercidas = DateUtils.formatDuration(horaExtra);
		this.mes = mes;
		this.ano = ano;
	}



	public FrequenciaResponse() {

	}
	
	

	public LocalDate getData() {
		return data;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}


	public LocalDateTime getHorasTrabalhadas() {
		return horasTrabalhadas;
	}


	public void setHorasTrabalhadas(LocalDateTime horasTrabalhadas) {
		this.horasTrabalhadas = horasTrabalhadas;
	}



	public MesEnum getMes() {
		return mes;
	}


	public void setMes(MesEnum mes) {
		this.mes = mes;
	}


	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalDateTime getHoraExtra() {
		return horaExtra;
	}

	public void setHoraExtra(LocalDateTime horaExtra) {
		this.horaExtra = horaExtra;
	}

	public LocalDateTime getEntradaUm() {
		return entradaUm;
	}

	public void setEntradaUm(LocalDateTime entradaUm) {
		this.entradaUm = entradaUm;
	}

	public LocalDateTime getSaidaUm() {
		return saidaUm;
	}

	public void setSaidaUm(LocalDateTime saidaUm) {
		this.saidaUm = saidaUm;
	}

	public LocalDateTime getEntradaDois() {
		return entradaDois;
	}

	public void setEntradaDois(LocalDateTime entradaDois) {
		this.entradaDois = entradaDois;
	}

	public LocalDateTime getSaidaDois() {
		return saidaDois;
	}

	public void setSaidaDois(LocalDateTime saidaDois) {
		this.saidaDois = saidaDois;
	}

	public FaltaResponse getFalta() {
		return falta;
	}

	public void setFalta(FaltaResponse falta) {
		this.falta = falta;
	}


	public String getHorasExercidas() {
		return horasExercidas;
	}


	public void setHorasExercidas(String horasExercidas) {
		this.horasExercidas = horasExercidas;
	}


	public String getHorasExtrasExercidas() {
		return horasExtrasExercidas;
	}


	public void setHorasExtrasExercidas(String horasExtrasExercidas) {
		this.horasExtrasExercidas = horasExtrasExercidas;
	}

	

}

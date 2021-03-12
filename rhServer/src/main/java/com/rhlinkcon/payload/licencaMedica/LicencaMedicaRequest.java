package com.rhlinkcon.payload.licencaMedica;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LicencaMedicaRequest {
	private Long id;
	
	@NotNull
	private Long afastamentoId;

	@NotNull
	private Long motivoAfastamentoId;

	@NotNull
	private Long funcionarioId;
	
	@NotNull
	private Long cidId;
	
	private Long crmId;
	
	@NotNull
	private Instant periodoInicial;
	
	private Instant periodoFinal;
	
	@NotNull
	private Instant dataInspecao;
	
	public Long getAfastamentoId() {
		return afastamentoId;
	}
	public void setAfastamentoId(Long afastamentoId) {
		this.afastamentoId = afastamentoId;
	}
	public Long getMotivoAfastamentoId() {
		return motivoAfastamentoId;
	}
	public void setMotivoAfastamentoId(Long motivoAfastamentoId) {
		this.motivoAfastamentoId = motivoAfastamentoId;
	}
	public Long getFuncionarioId() {
		return funcionarioId;
	}
	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}
	public Long getCrmId() {
		return crmId;
	}
	public void setCrmId(Long crmId) {
		this.crmId = crmId;
	}
	public Instant getPeriodoInicial() {
		return periodoInicial;
	}
	public void setPeriodoInicial(Instant periodoInicial) {
		this.periodoInicial = periodoInicial;
	}
	public Instant getPeriodoFinal() {
		return periodoFinal;
	}
	public void setPeriodoFinal(Instant periodoFinal) {
		this.periodoFinal = periodoFinal;
	}
	
	public Instant getDataInspecao() {
		return dataInspecao;
	}
	public void setDataInspecao(Instant dataInspecao) {
		this.dataInspecao = dataInspecao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCidId() {
		return cidId;
	}
	public void setCidId(Long cidId) {
		this.cidId = cidId;
	}
	
	
}
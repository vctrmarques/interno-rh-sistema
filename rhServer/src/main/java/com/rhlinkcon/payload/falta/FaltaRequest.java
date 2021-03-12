package com.rhlinkcon.payload.falta;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FaltaRequest {
	private Long id;
	
	@NotNull
	private Long funcionarioId;
	
	@NotNull
	private Instant dataInicio;
	
	private Instant dataRetorno;
	
	@NotNull
	private Long motivoAfastamentoId;
	
	@NotNull
	private Long afastamentoId;
	
	@NotBlank
	@NotNull
	private String diagnosticoMedico;
	
	private String observacaoDocumento;
	
	private Long anexoId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Instant getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Instant dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Instant getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Instant dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public Long getMotivoAfastamentoId() {
		return motivoAfastamentoId;
	}

	public void setMotivoAfastamentoId(Long motivoAfastamentoId) {
		this.motivoAfastamentoId = motivoAfastamentoId;
	}

	public Long getAfastamentoId() {
		return afastamentoId;
	}

	public void setAfastamentoId(Long afastamentoId) {
		this.afastamentoId = afastamentoId;
	}	

	public String getDiagnosticoMedico() {
		return diagnosticoMedico;
	}

	public void setDiagnosticoMedico(String diagnosticoMedico) {
		this.diagnosticoMedico = diagnosticoMedico;
	}

	public String getObservacaoDocumento() {
		return observacaoDocumento;
	}

	public void setObservacaoDocumento(String observacaoDocumento) {
		this.observacaoDocumento = observacaoDocumento;
	}

	public Long getAnexoId() {
		return anexoId;
	}

	public void setAnexoId(Long anexoId) {
		this.anexoId = anexoId;
	}

	
	

}

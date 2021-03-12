package com.rhlinkcon.payload.licencaPremio;

import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Dependente;
import com.rhlinkcon.model.FuncionarioExercicio;
import com.rhlinkcon.model.LicencaPremio;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.classificacaoAto.ClassificacaoAtoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LicencaPremioResponse extends DadoCadastralResponse {
	
	private Long id;
	
	private Long funcionarioExercicioId;

	private String observacao;

	private Instant dataInicio;

	private Instant dataRetorno;

	private String dias;	
	
	public LicencaPremioResponse(LicencaPremio licencaPremio) {
		setLicencaPremio(licencaPremio);
	}
	
	public LicencaPremioResponse(LicencaPremio licencaPremio, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setLicencaPremio(licencaPremio);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	public void setLicencaPremio(LicencaPremio licencaPremio) {
		this.setId(licencaPremio.getId());

		
		if(Objects.nonNull(licencaPremio.getDataInicio()))
			this.setDataInicio(licencaPremio.getDataInicio());		

		if(Objects.nonNull(licencaPremio.getDataRetorno()))
			this.setDataRetorno(licencaPremio.getDataRetorno());
		
		if(Objects.nonNull(licencaPremio.getObservacao()))
			this.setObservacao(licencaPremio.getObservacao());
		
		if(Objects.nonNull(licencaPremio.getDias()))
			this.setDias(licencaPremio.getDias());		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncionarioExercicioId() {
		return funcionarioExercicioId;
	}

	public void setFuncionarioExercicioId(Long funcionarioExercicioId) {
		this.funcionarioExercicioId = funcionarioExercicioId;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

	public String getDias() {
		return dias;
	}

	public void setDias(String dias) {
		this.dias = dias;
	}
	
}

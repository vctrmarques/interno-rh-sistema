package com.rhlinkcon.payload.funcionarioExercicio;

import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Dependente;
import com.rhlinkcon.model.FuncionarioExercicio;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.classificacaoAto.ClassificacaoAtoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FuncionarioExercicioResponse extends DadoCadastralResponse {
	
	private Long id;
	
	private Long funcionarioId;
	
	private Long classificacaoAtoId;	
	
	private String exercicio;
	
	private Instant dataInicio;
	
	private Instant dataFim;
	
	private String processo;
	
	private String numDiarioOficial;
	
	private Instant dataDiarioOficial;	
	
	private String numeroAto;
	
	private String anoAto;
	
	private Instant dataAto;
	
	private Integer quantLicencaPremio;
	
	public FuncionarioExercicioResponse(FuncionarioExercicio funcionarioExercicio) {
		setFuncionarioExercicio(funcionarioExercicio);
	}
	
	public FuncionarioExercicioResponse(FuncionarioExercicio funcionarioExercicio, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setFuncionarioExercicio(funcionarioExercicio);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	public void setFuncionarioExercicio(FuncionarioExercicio funcionarioExercicio) {
		this.setId(funcionarioExercicio.getId());

		if(Objects.nonNull(funcionarioExercicio.getExercicio()))
			this.setExercicio(funcionarioExercicio.getExercicio());
		
		if(Objects.nonNull(funcionarioExercicio.getDataInicio()))
			this.setDataInicio(funcionarioExercicio.getDataInicio());		

		if(Objects.nonNull(funcionarioExercicio.getDataFim()))
			this.setDataFim(funcionarioExercicio.getDataFim());
		
		if(Objects.nonNull(funcionarioExercicio.getProcesso()))
			this.setProcesso(funcionarioExercicio.getProcesso());
		
		if(Objects.nonNull(funcionarioExercicio.getNumDiarioOficial()))
			this.setNumDiarioOficial(funcionarioExercicio.getNumDiarioOficial());		

		if(Objects.nonNull(funcionarioExercicio.getDataDiarioOficial()))
			this.setDataDiarioOficial(funcionarioExercicio.getDataDiarioOficial());
		
		if(Objects.nonNull(funcionarioExercicio.getNumeroAto()))
			this.setNumeroAto(funcionarioExercicio.getNumeroAto());
		
		if(Objects.nonNull(funcionarioExercicio.getAnoAto()))
			this.setAnoAto(funcionarioExercicio.getAnoAto());
		
		if(Objects.nonNull(funcionarioExercicio.getDataAto()))
			this.setDataAto(funcionarioExercicio.getDataAto());			
		
	}

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

	public Long getClassificacaoAtoId() {
		return classificacaoAtoId;
	}

	public void setClassificacaoAtoId(Long classificacaoAtoId) {
		this.classificacaoAtoId = classificacaoAtoId;
	}

	public String getExercicio() {
		return exercicio;
	}

	public void setExercicio(String exercicio) {
		this.exercicio = exercicio;
	}

	public Instant getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Instant dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Instant getDataFim() {
		return dataFim;
	}

	public void setDataFim(Instant dataFim) {
		this.dataFim = dataFim;
	}

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getNumDiarioOficial() {
		return numDiarioOficial;
	}

	public void setNumDiarioOficial(String numDiarioOficial) {
		this.numDiarioOficial = numDiarioOficial;
	}

	public Instant getDataDiarioOficial() {
		return dataDiarioOficial;
	}

	public void setDataDiarioOficial(Instant dataDiarioOficial) {
		this.dataDiarioOficial = dataDiarioOficial;
	}

	public String getNumeroAto() {
		return numeroAto;
	}

	public void setNumeroAto(String numeroAto) {
		this.numeroAto = numeroAto;
	}

	public String getAnoAto() {
		return anoAto;
	}

	public void setAnoAto(String anoAto) {
		this.anoAto = anoAto;
	}

	public Instant getDataAto() {
		return dataAto;
	}

	public void setDataAto(Instant dataAto) {
		this.dataAto = dataAto;
	}

	public Integer getQuantLicencaPremio() {
		return quantLicencaPremio;
	}

	public void setQuantLicencaPremio(Integer quantLicencaPremio) {
		this.quantLicencaPremio = quantLicencaPremio;
	}

	
	
	
}

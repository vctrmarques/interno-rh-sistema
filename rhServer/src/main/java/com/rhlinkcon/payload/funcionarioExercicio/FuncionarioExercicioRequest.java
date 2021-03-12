package com.rhlinkcon.payload.funcionarioExercicio;

import java.time.Instant;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FuncionarioExercicioRequest {
		
		private Long id;
		
		@NotNull
		private Long funcionarioId;
		
		@NotNull
		private Long classificacaoAtoId;	
		
		@NotNull
		private String exercicio;
		
		@NotNull
		private Instant dataInicio;
		
		@NotNull
		private Instant dataFim;
		
		@NotNull
		private String processo;
		
		@NotNull
		private String numDiarioOficial;
		
		@NotNull
		private Instant dataDiarioOficial;	
		
		@NotNull
		private String numeroAto;
		
		@NotNull
		private String anoAto;
		
		@NotNull
		private Instant dataAto;
	

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


}
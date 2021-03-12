package com.rhlinkcon.payload.licencaPremio;

import java.time.Instant;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LicencaPremioRequest {
		
		private Long id;
		
		@NotNull
		private Long funcionarioExercicioId;
		
		@NotNull
		private String observacao;
		
		@NotNull
		private Instant dataInicio;
		
		@NotNull
		private Instant dataRetorno;
		
		@NotNull
		private String dias;	
	

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
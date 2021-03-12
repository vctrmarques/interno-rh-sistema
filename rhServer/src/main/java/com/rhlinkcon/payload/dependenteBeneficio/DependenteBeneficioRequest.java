package com.rhlinkcon.payload.dependenteBeneficio;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DependenteBeneficioRequest {
		
		private Long id;
		
		@NotNull
		private Long consignadoId;
		
		@NotNull
		private Long dependenteId;
		
		@NotNull
		private BigDecimal valor;
	

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getConsignadoId() {
			return consignadoId;
		}

		public void setConsignadoId(Long consignadoId) {
			this.consignadoId = consignadoId;
		}

		public Long getDependenteId() {
			return dependenteId;
		}

		public void setDependenteId(Long dependenteId) {
			this.dependenteId = dependenteId;
		}

		public BigDecimal getValor() {
			return valor;
		}

		public void setValor(BigDecimal valor) {
			this.valor = valor;
		}

}
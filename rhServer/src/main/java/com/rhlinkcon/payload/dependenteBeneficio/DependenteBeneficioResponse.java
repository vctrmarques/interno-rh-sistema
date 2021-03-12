package com.rhlinkcon.payload.dependenteBeneficio;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.DependenteBeneficio;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.consignado.ConsignadoResponse;
import com.rhlinkcon.payload.dependente.DependenteResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DependenteBeneficioResponse extends DadoCadastralResponse {
	
	private Long id;
	
	@NotNull
	private ConsignadoResponse consignado;
	
	@NotNull
	private DependenteResponse dependente;
	
	@NotNull
	private BigDecimal valor;
		
	
	public DependenteBeneficioResponse(DependenteBeneficio dependenteBeneficio) {
		setDependente(dependenteBeneficio);
	}
	
	public DependenteBeneficioResponse(DependenteBeneficio dependenteBeneficio, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setDependente(dependenteBeneficio);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	public void setDependente(DependenteBeneficio dependenteBeneficio) {
		this.setId(dependenteBeneficio.getId());
		
		if(Objects.nonNull(dependenteBeneficio.getConsignado()))
			this.setConsignado(new ConsignadoResponse(dependenteBeneficio.getConsignado()));
		
		if(Objects.nonNull(dependenteBeneficio.getDependente()))
			this.setDependente(new DependenteResponse(dependenteBeneficio.getDependente()));		
		
		if(Objects.nonNull(dependenteBeneficio.getValor()))
			this.setValor(dependenteBeneficio.getValor());	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ConsignadoResponse getConsignado() {
		return consignado;
	}

	public void setConsignado(ConsignadoResponse consignado) {
		this.consignado = consignado;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public DependenteResponse getDependente() {
		return dependente;
	}

	public void setDependente(DependenteResponse dependente) {
		this.dependente = dependente;
	}
	
	
}

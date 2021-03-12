package com.rhlinkcon.payload.valeTransporte;

import java.time.Instant;

import com.rhlinkcon.model.ValeTransporte;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class ValeTransporteResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private Double valor;


	public ValeTransporteResponse(ValeTransporte valeTransporte) {
		this.setId(valeTransporte.getId());
		this.setCodigo(valeTransporte.getCodigo());
		this.setValor(valeTransporte.getValor());
	}

	public ValeTransporteResponse(ValeTransporte valeTransporte, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(valeTransporte.getId());
		this.setCodigo(valeTransporte.getCodigo());
		this.setValor(valeTransporte.getValor());
		
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}

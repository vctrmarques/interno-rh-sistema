package com.rhlinkcon.payload.tipoFerias;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.TipoFerias;
import com.rhlinkcon.payload.DadoCadastralResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoFeriasResponse extends DadoCadastralResponse {

	private Long id;

	private String descricao;
	
	private Integer abono;
	
	private Integer dias;
	
	private Integer saldo;
	
	private String decimoTerceiro;
	
	private String coletivo;
	
	private String licencaPremio;
	
	public TipoFeriasResponse(TipoFerias tipoFerias) {
		setTipoFerias(tipoFerias);
	}
	
	public TipoFeriasResponse(TipoFerias tipoFerias, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setTipoFerias(tipoFerias);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);		
	}
	
	private void setTipoFerias(TipoFerias tipoFerias) {
		this.setId(tipoFerias.getId());
		this.setDescricao(tipoFerias.getDescricao());
		this.setAbono(tipoFerias.getAbono());
		this.setDias(tipoFerias.getDias());
		this.setSaldo(tipoFerias.getSaldo());
		this.setDecimoTerceiro(tipoFerias.getDecimoTerceiro().getLabel());
		this.setColetivo(tipoFerias.getColetivo().getLabel());
		this.setLicencaPremio(tipoFerias.getLicencaPremio().getLabel());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Integer getAbono() {
		return abono;
	}

	public void setAbono(Integer abono) {
		this.abono = abono;
	}
	
	public Integer getDias() {
		return dias;
	}

	public void setDias(Integer dias) {
		this.dias =dias;
	}
	
	public Integer getSaldo() {
		return saldo;
	}

	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}
	
	public String getDecimoTerceiro() {
		return decimoTerceiro;
	}

	public void setDecimoTerceiro(String decimoTerceiro) {
		this.decimoTerceiro = decimoTerceiro;
	}
	
	public String getColetivo() {
		return coletivo;
	}

	public void setColetivo(String coletivo) {
		this.coletivo = coletivo;
	}
	
	public String getLicencaPremio() {
		return licencaPremio;
	}

	public void setLicencaPremio(String licencaPremio) {
		this.licencaPremio = licencaPremio;
	}

}

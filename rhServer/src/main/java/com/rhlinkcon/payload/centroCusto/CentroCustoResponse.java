package com.rhlinkcon.payload.centroCusto;

import java.time.Instant;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;

import com.rhlinkcon.model.CentroCusto;
import com.rhlinkcon.payload.DadoCadastralResponse;

public class CentroCustoResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricao;

	private Integer efetivo;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	private Integer nivel;

	private String tipoDebito;

	private String tipoCredito;

	private Integer contaCredito;

	private Integer contaDebito;

	private String tipoCentroCusto;
	
	private String cnpj;

	public CentroCustoResponse(CentroCusto centroCusto) {
		this.setId(centroCusto.getId());
		this.setCodigo(centroCusto.getCodigo());
		this.setDescricao(centroCusto.getDescricao());
		this.setEfetivo(centroCusto.getEfetivo());
		this.setNivel(centroCusto.getNivel());
		this.setTipoCredito(centroCusto.getTipoCredito());
		this.setTipoDebito(centroCusto.getTipoDebito());
		this.setContaCredito(centroCusto.getContaCredito());
		this.setContaDebito(centroCusto.getContaDebito());
		this.setCnpj(centroCusto.getCnpj());
		if (Objects.nonNull(centroCusto.getTipoCentroCusto()))
			this.setTipoCentroCusto(centroCusto.getTipoCentroCusto().getLabel());
	}

	public CentroCustoResponse(CentroCusto centroCusto, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor) {
		this.setId(centroCusto.getId());
		this.setCodigo(centroCusto.getCodigo());
		this.setDescricao(centroCusto.getDescricao());
		this.setEfetivo(centroCusto.getEfetivo());
		this.setNivel(centroCusto.getNivel());
		this.setTipoCredito(centroCusto.getTipoCredito());
		this.setTipoDebito(centroCusto.getTipoDebito());
		this.setContaCredito(centroCusto.getContaCredito());
		this.setContaDebito(centroCusto.getContaDebito());
		this.setCnpj(centroCusto.getCnpj());

		if (Objects.nonNull(centroCusto.getTipoCentroCusto()))
			this.setTipoCentroCusto(centroCusto.getTipoCentroCusto().getLabel());

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getEfetivo() {
		return efetivo;
	}

	public void setEfetivo(Integer efetivo) {
		this.efetivo = efetivo;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getTipoDebito() {
		return tipoDebito;
	}

	public void setTipoDebito(String tipoDebito) {
		this.tipoDebito = tipoDebito;
	}

	public String getTipoCredito() {
		return tipoCredito;
	}

	public void setTipoCredito(String tipoCredito) {
		this.tipoCredito = tipoCredito;
	}

	public Integer getContaCredito() {
		return contaCredito;
	}

	public void setContaCredito(Integer contaCredito) {
		this.contaCredito = contaCredito;
	}

	public Integer getContaDebito() {
		return contaDebito;
	}

	public void setContaDebito(Integer contaDebito) {
		this.contaDebito = contaDebito;
	}

	public String getTipoCentroCusto() {
		return tipoCentroCusto;
	}

	public void setTipoCentroCusto(String tipoCentroCusto) {
		this.tipoCentroCusto = tipoCentroCusto;
	}

}

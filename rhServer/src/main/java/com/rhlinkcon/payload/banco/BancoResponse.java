package com.rhlinkcon.payload.banco;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Agencia;
import com.rhlinkcon.model.Banco;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.agencia.AgenciaResponse;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

public class BancoResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String nome;

	private Integer agencia;

	private Integer digito;

	private String nomeAgencia;

	private String praca;

	private String uf;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String referencia;

	private boolean bloqueado;

	private Boolean principal;

	private List<AgenciaResponse> agencias;

	public BancoResponse() {
	}

	public BancoResponse(Banco banco) {
		this.setBloqueado(Utils.setBool(banco.isBloqueado()));
		this.setCodigo(banco.getCodigo());
		this.setNome(banco.getNome());
		this.setPrincipal(banco.getPrincipal());
		this.setId(banco.getId());
	}

	public BancoResponse(Banco banco, Projecao projecao) {

		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			setId(banco.getId());
			setNome(banco.getNome());
			setBloqueado(banco.isBloqueado());
			setCodigo(banco.getCodigo());
			setPrincipal(banco.getPrincipal());
			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {

				if (projecao.equals(Projecao.COMPLETA)) {
					setCriadoEm(banco.getCreatedAt());
					setAlteradoEm(banco.getUpdatedAt());

					agencias = new ArrayList<>();
					for (Agencia agencia : banco.getAgencias()) {
						agencias.add(new AgenciaResponse(agencia, Projecao.BASICA));
					}

				}
			}
		}
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getAgencia() {
		return agencia;
	}

	public void setAgencia(Integer agencia) {
		this.agencia = agencia;
	}

	public Integer getDigito() {
		return digito;
	}

	public void setDigito(Integer digito) {
		this.digito = digito;
	}

	public String getNomeAgencia() {
		return nomeAgencia;
	}

	public void setNomeAgencia(String nomeAgencia) {
		this.nomeAgencia = nomeAgencia;
	}

	public String getPraca() {
		return praca;
	}

	public void setPraca(String praca) {
		this.praca = praca;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public List<AgenciaResponse> getAgencias() {
		return agencias;
	}

	public void setAgencias(List<AgenciaResponse> agencias) {
		this.agencias = agencias;
	}

	public Boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

}

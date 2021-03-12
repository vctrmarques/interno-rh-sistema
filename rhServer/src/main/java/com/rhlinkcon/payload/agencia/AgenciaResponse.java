package com.rhlinkcon.payload.agencia;

import com.rhlinkcon.model.Agencia;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.banco.BancoResponse;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.payload.unidadeFederativa.UnidadeFederativaResponse;
import com.rhlinkcon.util.Projecao;

public class AgenciaResponse extends DadoCadastralResponse {

	private Long id;

	private Integer numero;

	private Integer digito;

	private String nome;

	private UnidadeFederativaResponse uf;
	
	private MunicipioResponse municipio;
	
	private boolean bloqueado;
	
	private BancoResponse banco;
	
	private Long bancoId;
	
	public AgenciaResponse() {
	}

	public AgenciaResponse(Agencia agencia, Projecao projecao) {

		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			setId(agencia.getId());
			setNome(agencia.getNome());
			setNumero(agencia.getNumero());
			setDigito(agencia.getDigito());
			setBloqueado(agencia.isBloqueado());
			setMunicipio(new MunicipioResponse(agencia.getMunicipio()));
				
			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
				setUf(new UnidadeFederativaResponse(agencia.getUf()));

				if (projecao.equals(Projecao.COMPLETA)) {
					setCriadoEm(agencia.getCreatedAt());
					setAlteradoEm(agencia.getUpdatedAt());
					setBanco(new BancoResponse(agencia.getBanco(), Projecao.BASICA));
					setBancoId(getBanco().getId());
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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Integer getDigito() {
		return digito;
	}

	public void setDigito(Integer digito) {
		this.digito = digito;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public UnidadeFederativaResponse getUf() {
		return uf;
	}

	public void setUf(UnidadeFederativaResponse uf) {
		this.uf = uf;
	}


	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public BancoResponse getBanco() {
		return banco;
	}

	public void setBanco(BancoResponse banco) {
		this.banco = banco;
	}

	public Long getBancoId() {
		return bancoId;
	}

	public void setBancoId(Long bancoId) {
		this.bancoId = bancoId;
	}

	public MunicipioResponse getMunicipio() {
		return municipio;
	}

	public void setMunicipio(MunicipioResponse municipio) {
		this.municipio = municipio;
	}


}

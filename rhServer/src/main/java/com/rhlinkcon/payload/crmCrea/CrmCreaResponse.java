package com.rhlinkcon.payload.crmCrea;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.rhlinkcon.model.Convenio;
import com.rhlinkcon.model.CrmCrea;
import com.rhlinkcon.model.CrmCreaEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.convenio.ConvenioResponse;
import com.rhlinkcon.util.Projecao;

public class CrmCreaResponse extends DadoCadastralResponse {

	private Long id;

	private String nomeConveniado;

	private String numeroCrmCrea;
	
	@Enumerated(EnumType.STRING)
	private CrmCreaEnum tipo;

	private boolean coordenadorPcmso;

	private boolean responsavelLtcat;

	private List<ConvenioResponse> convenios;

	public CrmCreaResponse() {
	}

	public CrmCreaResponse(CrmCrea crmCrea, Projecao projecao) {

		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			setId(crmCrea.getId());
			setNomeConveniado(crmCrea.getNomeConveniado());
			setNumeroCrmCrea(crmCrea.getNumeroCrmCrea());
			setCoordenadorPcmso(crmCrea.isCoordenadorPcmso());
			setResponsavelLtcat(crmCrea.isResponsavelLtcat());
			setTipo(crmCrea.getTipo());
			
			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {

				if (projecao.equals(Projecao.COMPLETA)) {
					setCriadoEm(crmCrea.getCreatedAt());
					setAlteradoEm(crmCrea.getUpdatedAt());
					setTipo(crmCrea.getTipo());

					convenios = new ArrayList<>();
					for (Convenio convenio : crmCrea.getConvenios()) {
						convenios.add(new ConvenioResponse(convenio));
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

	public String getNomeConveniado() {
		return nomeConveniado;
	}

	public void setNomeConveniado(String nomeConveniado) {
		this.nomeConveniado = nomeConveniado;
	}

	public String getNumeroCrmCrea() {
		return numeroCrmCrea;
	}

	public void setNumeroCrmCrea(String numeroCrmCrea) {
		this.numeroCrmCrea = numeroCrmCrea;
	}

	public boolean isCoordenadorPcmso() {
		return coordenadorPcmso;
	}

	public void setCoordenadorPcmso(boolean coordenadorPcmso) {
		this.coordenadorPcmso = coordenadorPcmso;
	}

	public boolean isResponsavelLtcat() {
		return responsavelLtcat;
	}

	public void setResponsavelLtcat(boolean responsavelLtcat) {
		this.responsavelLtcat = responsavelLtcat;
	}

	public List<ConvenioResponse> getConvenios() {
		return convenios;
	}

	public void setConvenios(List<ConvenioResponse> convenios) {
		this.convenios = convenios;
	}

	public CrmCreaEnum getTipo() {
		return tipo;
	}
	
	public void setTipo(CrmCreaEnum tipo) {
		this.tipo = tipo;
	}
	
}

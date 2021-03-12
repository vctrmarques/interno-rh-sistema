package com.rhlinkcon.payload.definicaoOrganicoLotacaoCargo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.DefinicaoOrganicoLotacaoCargo;
import com.rhlinkcon.payload.DadoCadastralResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefinicaoOrganicoLotacaoCargoResponse{
	
	private Long empresaFilialId;
	
	private Long lotacaoId;
	
	private Long cargoId;
	
	private String nome;
	
	private Long quantPermitido;
	
	public DefinicaoOrganicoLotacaoCargoResponse(DefinicaoOrganicoLotacaoCargo definicaoOrganicoLotacaoCargo) {
		this.setEmpresaFilialId(definicaoOrganicoLotacaoCargo.getEmpresaFilialId());
		this.setLotacaoId(definicaoOrganicoLotacaoCargo.getLotacaoId());
		this.setCargoId(definicaoOrganicoLotacaoCargo.getCargoId());
		this.setQuantPermitido(definicaoOrganicoLotacaoCargo.getQuantPermitido());
	}

	public Long getEmpresaFilialId() {
		return empresaFilialId;
	}

	public void setEmpresaFilialId(Long empresaFilialId) {
		this.empresaFilialId = empresaFilialId;
	}

	public Long getLotacaoId() {
		return lotacaoId;
	}

	public void setLotacaoId(Long lotacaoId) {
		this.lotacaoId = lotacaoId;
	}

	public Long getCargoId() {
		return cargoId;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getQuantPermitido() {
		return quantPermitido;
	}

	public void setQuantPermitido(Long quantPermitido) {
		this.quantPermitido = quantPermitido;
	}
	
}

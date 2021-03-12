package com.rhlinkcon.payload.definicaoOrganicoLotacaoCargo;

public class DefinicaoOrganicoLotacaoCargoRequest {

	private Long id;
	
	private Long empresaFilialId;
	
	private Long lotacaoId;
	
	private Long cargoId;
	
	private Long quantPermitido;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getQuantPermitido() {
		return quantPermitido;
	}

	public void setQuantPermitido(Long quantPermitido) {
		this.quantPermitido = quantPermitido;
	}
	
}
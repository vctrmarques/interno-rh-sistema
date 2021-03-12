package com.rhlinkcon.payload.definicaoOrganicoLotacaoFuncao;

public class DefinicaoOrganicoLotacaoFuncaoRequest {

	private Long id;
	
	private Long empresaFilialId;
	
	private Long lotacaoId;
	
	private Long funcaoId;
	
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

	public Long getFuncaoId() {
		return funcaoId;
	}

	public void setFuncaoId(Long funcaoId) {
		this.funcaoId = funcaoId;
	}

	public Long getQuantPermitido() {
		return quantPermitido;
	}

	public void setQuantPermitido(Long quantPermitido) {
		this.quantPermitido = quantPermitido;
	}
	
}
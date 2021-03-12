package com.rhlinkcon.payload.tranferenciaFuncionario;



public class TransferenciaFuncionarioRequest {

	private Long id;

	private Long funcionarioId;
	
	private Long lotacaoAnteriorId;
	
	private Long empresaAnteriorId;

	private Long lotacaoId;

	private Long empresaId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Long getLotacaoId() {
		return lotacaoId;
	}

	public void setLotacaoId(Long lotacaoId) {
		this.lotacaoId = lotacaoId;
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}

	public Long getLotacaoAnteriorId() {
		return lotacaoAnteriorId;
	}

	public void setLotacaoAnteriorId(Long lotacaoAnteriorId) {
		this.lotacaoAnteriorId = lotacaoAnteriorId;
	}

	public Long getEmpresaAnteriorId() {
		return empresaAnteriorId;
	}

	public void setEmpresaAnteriorId(Long empresaAnteriorId) {
		this.empresaAnteriorId = empresaAnteriorId;
	}
}

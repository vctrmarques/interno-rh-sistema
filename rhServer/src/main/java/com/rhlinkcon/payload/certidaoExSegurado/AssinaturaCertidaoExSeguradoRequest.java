package com.rhlinkcon.payload.certidaoExSegurado;

public class AssinaturaCertidaoExSeguradoRequest {

	private Long id;

	private Long funcionarioId;
	
	private Long certidaoExSeguradoId;
	
	private String funcaoAssinaturaCertidao;
	
	private String abaAssinaturaCertidao;

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

	public Long getCertidaoExSeguradoId() {
		return certidaoExSeguradoId;
	}

	public void setCertidaoExSeguradoId(Long certidaoExSeguradoId) {
		this.certidaoExSeguradoId = certidaoExSeguradoId;
	}

	public String getFuncaoAssinaturaCertidao() {
		return funcaoAssinaturaCertidao;
	}

	public void setFuncaoAssinaturaCertidao(String funcaoAssinaturaCertidao) {
		this.funcaoAssinaturaCertidao = funcaoAssinaturaCertidao;
	}

	public String getAbaAssinaturaCertidao() {
		return abaAssinaturaCertidao;
	}

	public void setAbaAssinaturaCertidao(String abaAssinaturaCertidao) {
		this.abaAssinaturaCertidao = abaAssinaturaCertidao;
	}
	
	
	
}

package com.rhlinkcon.payload.certidaoCompensacaoAssinatura;

public class CertidaoCompensacaoAssinaturaRequest {
	
	private Long id;
	
	private Long certidaoCompensacaoId;
	
	private Long funcionarioId;
	
	private String funcao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCertidaoCompensacaoId() {
		return certidaoCompensacaoId;
	}

	public void setCertidaoCompensacaoId(Long certidaoCompensacaoId) {
		this.certidaoCompensacaoId = certidaoCompensacaoId;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	
	
}

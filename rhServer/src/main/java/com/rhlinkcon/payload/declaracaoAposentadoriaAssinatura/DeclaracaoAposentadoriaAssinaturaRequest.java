package com.rhlinkcon.payload.declaracaoAposentadoriaAssinatura;

public class DeclaracaoAposentadoriaAssinaturaRequest {

	private Long id;
	
	private Long declaracaoAposentadoriaId;
	
	private Long funcionarioId;
	
	private String funcao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeclaracaoAposentadoriaId() {
		return declaracaoAposentadoriaId;
	}

	public void setDeclaracaoAposentadoriaId(Long declaracaoAposentadoriaId) {
		this.declaracaoAposentadoriaId = declaracaoAposentadoriaId;
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

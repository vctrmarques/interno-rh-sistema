package com.rhlinkcon.payload.declaracaoAposentadoriaAssinatura;

import com.rhlinkcon.model.DeclaracaoAposentadoriaAssinatura;
import com.rhlinkcon.payload.declaracaoAposentadoria.DeclaracaoAposentadoriaResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.util.Projecao;

public class DeclaracaoAposentadoriaAssinaturaResponse {

	private Long id;
	
	private DeclaracaoAposentadoriaResponse declaracaoAposentadoria;
	
	private FuncionarioResponse funcionario;
	
	private String funcao;

	public DeclaracaoAposentadoriaAssinaturaResponse(DeclaracaoAposentadoriaAssinatura obj) {
		this.id = obj.getId();
		this.declaracaoAposentadoria = new DeclaracaoAposentadoriaResponse(obj.getDeclaracaoAposentadoria(), Projecao.BASICA);
		this.funcionario = new FuncionarioResponse(obj.getFuncionario(), Projecao.MEDIA);
		this.funcao = obj.getFuncao().getLabel();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DeclaracaoAposentadoriaResponse getDeclaracaoAposentadoria() {
		return declaracaoAposentadoria;
	}

	public void setDeclaracaoAposentadoria(DeclaracaoAposentadoriaResponse declaracaoAposentadoria) {
		this.declaracaoAposentadoria = declaracaoAposentadoria;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}
	
}

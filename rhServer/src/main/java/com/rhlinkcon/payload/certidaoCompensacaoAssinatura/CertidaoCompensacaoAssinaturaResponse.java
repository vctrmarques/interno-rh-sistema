package com.rhlinkcon.payload.certidaoCompensacaoAssinatura;

import com.rhlinkcon.model.CertidaoCompensacaoAssinatura;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.certidaoCompensacao.CertidaoCompensacaoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.util.Projecao;

public class CertidaoCompensacaoAssinaturaResponse extends DadoCadastralResponse {

	private Long id;
	
	private CertidaoCompensacaoResponse certidaoCompensacao;
	
	private FuncionarioResponse funcionario;
	
	private String funcao;
	
	public CertidaoCompensacaoAssinaturaResponse(CertidaoCompensacaoAssinatura obj) {
		this.id = obj.getId();
		this.certidaoCompensacao = new CertidaoCompensacaoResponse(obj.getCertidaoCompensacao(), Projecao.BASICA);
		this.funcionario = new FuncionarioResponse(obj.getFuncionario(), Projecao.MEDIA);
		this.funcao = obj.getFuncao().getLabel();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CertidaoCompensacaoResponse getCertidaoCompensacao() {
		return certidaoCompensacao;
	}

	public void setCertidaoCompensacao(CertidaoCompensacaoResponse certidaoCompensacao) {
		this.certidaoCompensacao = certidaoCompensacao;
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

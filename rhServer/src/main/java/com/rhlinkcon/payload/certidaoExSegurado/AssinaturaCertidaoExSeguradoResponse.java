package com.rhlinkcon.payload.certidaoExSegurado;

import com.rhlinkcon.model.AssinaturaCertidaoExSegurado;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.util.Projecao;

public class AssinaturaCertidaoExSeguradoResponse {
	
	private Long id;

	private FuncionarioResponse funcionario;
	
	private Long funcionarioId;
	
	private CertidaoExSeguradoResponse certidaoExSegurado;
	
	private String funcaoAssinaturaCertidao;
	
	private String abaAssinaturaCertidao;
	
	public AssinaturaCertidaoExSeguradoResponse(AssinaturaCertidaoExSegurado obj) {
		setId(obj.getId());
		setFuncionario(new FuncionarioResponse(obj.getFuncionario(), Projecao.BASICA));
		setFuncionarioId(obj.getFuncionario().getId());
		setCertidaoExSegurado(new CertidaoExSeguradoResponse(obj.getCertidaoExSegurado(), Projecao.BASICA));
		setFuncaoAssinaturaCertidao(obj.getFuncaoAssinaturaCertidao().getLabel());
		setAbaAssinaturaCertidao(obj.getAbaAssinaturaCertidao().name());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public CertidaoExSeguradoResponse getCertidaoExSegurado() {
		return certidaoExSegurado;
	}

	public void setCertidaoExSegurado(CertidaoExSeguradoResponse certidaoExSegurado) {
		this.certidaoExSegurado = certidaoExSegurado;
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

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}
	
	
}

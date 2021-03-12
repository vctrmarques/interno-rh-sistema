package com.rhlinkcon.payload.declaracaoExServidor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.rhlinkcon.model.DeclaracaoExServidor;
import com.rhlinkcon.model.StatusDeclaracaoExServidorEnum;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.util.Projecao;

public class DeclaracaoExServidorResponse extends DadoCadastralResponse{

	private Long id;

	private FuncionarioResponse funcionario;
	
	private FuncionarioResponse responsavel;
	
	private FuncionarioResponse dirigente;
	
	private String status;
	
	private List<DeclaracaoExServidorDadosFuncionaisResponse> dadosFuncionais;
	
	private boolean rascunho;
	
	public DeclaracaoExServidorResponse(DeclaracaoExServidor obj, Projecao projecao) {
		
		setId(obj.getId());
		
		setCriadoEm(obj.getCreatedAt());
		
		if(projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			if(Objects.nonNull(obj.getFuncionario()))
				setFuncionario(new FuncionarioResponse(obj.getFuncionario(), Projecao.CERTIDAO_EXSEGURADO));
			
			if(Objects.nonNull(obj.getResponsavel()))
				setResponsavel(new FuncionarioResponse(obj.getResponsavel(), Projecao.BASICA));
			
			if(Objects.nonNull(obj.getDirigente()))
				setDirigente(new FuncionarioResponse(obj.getDirigente(), Projecao.CARGO));
			
			if(Objects.nonNull(obj.getStatus())) {
				setStatus(obj.getStatus().getLabel());
				setRascunho(StatusDeclaracaoExServidorEnum.getEnumByString(getStatus()).equals(StatusDeclaracaoExServidorEnum.RASCUNHO));
			}
		}
		if(projecao.equals(Projecao.COMPLETA)) {
			if (obj.getDadosFuncionais() != null && !obj.getDadosFuncionais().isEmpty())
				this.dadosFuncionais = obj.getDadosFuncionais().stream().map(a -> new DeclaracaoExServidorDadosFuncionaisResponse(a))
				.collect(Collectors.toList());
		}
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

	public FuncionarioResponse getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(FuncionarioResponse responsavel) {
		this.responsavel = responsavel;
	}

	public FuncionarioResponse getDirigente() {
		return dirigente;
	}

	public void setDirigente(FuncionarioResponse dirigente) {
		this.dirigente = dirigente;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<DeclaracaoExServidorDadosFuncionaisResponse> getDadosFuncionais() {
		return dadosFuncionais;
	}

	public void setDadosFuncionais(List<DeclaracaoExServidorDadosFuncionaisResponse> dadosFuncionais) {
		this.dadosFuncionais = dadosFuncionais;
	}

	public boolean isRascunho() {
		return rascunho;
	}

	public void setRascunho(boolean rascunho) {
		this.rascunho = rascunho;
	}
	
	
	
}

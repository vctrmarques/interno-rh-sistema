package com.rhlinkcon.payload.contribuicaoSindical;

import java.time.Instant;

import com.rhlinkcon.model.ContribuicaoSindical;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.sindicato.SindicatoResponse;
import com.rhlinkcon.util.Projecao;

public class ContribuicaoSindicalResponse extends DadoCadastralResponse{

	private Long id;

	private FuncionarioResponse funcionario;

	private Integer ano;

	private SindicatoResponse sindicato;

	private boolean permiteDesconto;

	private AnexoResponse anexo;

	public ContribuicaoSindicalResponse() {

	}
	public ContribuicaoSindicalResponse(ContribuicaoSindical contribuicaoSindical, Projecao projecao) {
		if(projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)  ) {
			this.setId(contribuicaoSindical.getId());
			this.setFuncionario(new FuncionarioResponse(contribuicaoSindical.getFuncionario()));
			this.setSindicato(new SindicatoResponse(contribuicaoSindical.getSindicato()));
			this.setAno(contribuicaoSindical.getAno());
			this.setPermiteDesconto(contribuicaoSindical.getPermiteDesconto());
			if( projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
				this.setAnexo(new AnexoResponse(contribuicaoSindical.getAnexo()));
			}
		}
	}
	
	public ContribuicaoSindicalResponse(ContribuicaoSindical contribuicaoSindical, Instant criadoEm, String criadoPor, Instant alteradoEm,
			String alteradoPor, Projecao projecao) {
		
		if(projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)  ) {
			this.setId(contribuicaoSindical.getId());
			this.setFuncionario(new FuncionarioResponse(contribuicaoSindical.getFuncionario()));
			this.setSindicato(new SindicatoResponse(contribuicaoSindical.getSindicato()));
			this.setAno(contribuicaoSindical.getAno());
			this.setPermiteDesconto(contribuicaoSindical.getPermiteDesconto());
			if( projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
				this.setAnexo(new AnexoResponse(contribuicaoSindical.getAnexo()));
			}
		}

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
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

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public boolean isPermiteDesconto() {
		return permiteDesconto;
	}

	public void setPermiteDesconto(boolean permiteDesconto) {
		this.permiteDesconto = permiteDesconto;
	}

	public AnexoResponse getAnexo() {
		return anexo;
	}

	public void setAnexo(AnexoResponse anexo) {
		this.anexo = anexo;
	}

	public SindicatoResponse getSindicato() {
		return sindicato;
	}

	public void setSindicato(SindicatoResponse sindicato) {
		this.sindicato = sindicato;
	}

}

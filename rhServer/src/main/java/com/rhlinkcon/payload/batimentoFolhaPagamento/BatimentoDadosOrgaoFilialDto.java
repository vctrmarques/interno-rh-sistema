package com.rhlinkcon.payload.batimentoFolhaPagamento;

import java.util.Objects;

import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.util.Utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatimentoDadosOrgaoFilialDto {

	private String codigoOrgao;
	
	private String nomeOrgao;
	
	private String cnpjOrgao;
	
	private String codigoFilial;
	
	private String nomeFilial;
	
	private String situacaoFilial;
	
	
	public BatimentoDadosOrgaoFilialDto(EmpresaFilial orgao, EmpresaFilialResponse filial) {
		this.codigoOrgao = Objects.nonNull(orgao.getCodigoEmpregador()) ? orgao.getCodigoEmpregador().toString() : "";
		this.nomeOrgao = orgao.getNomeFilial();
		this.cnpjOrgao = Utils.formatarCnpj(orgao.getCnpj());
		this.codigoFilial = Objects.nonNull(filial.getCodigoEmpregador()) ? filial.getCodigoEmpregador().toString() : "";
		this.nomeFilial = filial.getNomeFilial();
		this.situacaoFilial = filial.getTipoFilial();
	}
	
}
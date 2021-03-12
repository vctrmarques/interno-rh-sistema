package com.rhlinkcon.payload.dirf;

import com.rhlinkcon.model.EmpresaFilial;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirfEmpresaFilialDto {

	private Long id;
	
	private String sigla;
	
	private String nome;
	
	private Integer codigo;
	
	private String cnpj;
	
	public DirfEmpresaFilialDto() {}
	
	public DirfEmpresaFilialDto(EmpresaFilial obj) {
		this.id = obj.getId();
		this.sigla = obj.getSigla();
		this.nome = obj.getNomeFilial();
		this.codigo = obj.getCodigoEmpregador();
		this.cnpj = obj.getCnpj();
	}
}

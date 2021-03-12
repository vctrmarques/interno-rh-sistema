package com.rhlinkcon.payload.funcionario;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class FuncionarioDto {

	private Long id;
	
	private String nome;

	private Integer matricula;

	private String lotacao;
	
	private String tipoSituacaoFuncional;

}

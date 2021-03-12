package com.rhlinkcon.filtro;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContrachequeFiltro {
	private Long folhaId;
	private String nome;
	private String lotacao;
	private String filial;
	private String situacao;
	private String situacaoFuncional;

}

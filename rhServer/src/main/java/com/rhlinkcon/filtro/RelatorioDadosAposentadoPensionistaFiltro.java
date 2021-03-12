package com.rhlinkcon.filtro;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelatorioDadosAposentadoPensionistaFiltro {

	private Long filial;
	
	private List<Long> lotacoesId;
	
	private String situacao;
	
	private String status;
	
	//Atributos aposentado
	private List<String> atributosDadoPessoalAposentado;
	private List<String> atributosDadoProfissionalAposentado;
	private List<String> atributosDocPessoalAposentado;
	private List<String> atributosDocNomeacaoAposentado;
	private List<String> atributosContatoEnderecoAposentado;
	private List<String> atributosBancoPessoalAposentado;
	private List<String> atributosDadoBancarioAposentado;
	
	//Atributos pensionista
	private List<String> atributosDadoPessoalPensionista;
	private List<String> atributosDadoExSeguradoPensionista;
	private List<String> atributosDadoDependentePensionista;
	private List<String> atributosDocPessoalPensionista;
	private List<String> atributosContatoEnderecoPensionista;
	private List<String> atributosDadoBeneficioPensionista;
	private List<String> atributosDadoContaCreditoPensionista;
	private List<String> atributosDadoIsencaoPensionista;
	
	
}

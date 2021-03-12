package com.rhlinkcon.util;

import java.text.Normalizer;

import org.apache.commons.lang.StringUtils;

public class CampoAlfaNumerico {
	private final String valor;
	private final int tamanho;

	public CampoAlfaNumerico(String valor, int tamanho) {
	  this.valor = valor;
	  this.tamanho = tamanho;
	}
	  
	public CampoAlfaNumerico(int valor, int tamanho) {
	  this.valor = String.valueOf(valor);
	  this.tamanho = tamanho;
	}

	@Override
	public String toString() {
	  String valorComTamanhoPermitido = corrigeTamanhoDoCampo();
	  
	  String semAcentosOuCedilha = Normalizer.normalize(valorComTamanhoPermitido,
	      Normalizer.Form.NFD);
	  semAcentosOuCedilha = semAcentosOuCedilha.replaceAll("[^\\p{ASCII}]", "");
	  return StringUtils.rightPad(semAcentosOuCedilha.toUpperCase(), tamanho);
	}

	private String corrigeTamanhoDoCampo() {
	  if(valor.length() > tamanho) {
	    return valor.substring(0, tamanho).trim();
	  } else {
	    return valor;
	  }
	}
}

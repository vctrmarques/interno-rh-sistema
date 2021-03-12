package com.rhlinkcon.util;

import org.jrimum.utilix.text.Strings;

public class CampoNumerico {

	private final String valor;
	private final int tamanho;

	public CampoNumerico(String valor, int tamanho) {
		String numero = String.valueOf(valor);
		numero = numero.replace(".", "");
		numero = numero.replace(",", "");
		numero = numero.replace("-", "");
		numero = numero.replace("/", "");
		this.valor = numero;
		this.tamanho = tamanho;
	}

	public CampoNumerico(int valor, int tamanho) {
		this.valor = String.valueOf(valor);
		this.tamanho = tamanho;
	}

	public CampoNumerico(Double valor, int tamanho) {
		String numero = String.valueOf(valor);
		numero = numero.replace(".", "");
		numero = numero.replace(",", "");
		numero.trim();
		this.valor = numero;
		this.tamanho = tamanho;
	}

	@Override
	public String toString() {
		return Strings.fillWithZeroLeft(valor, tamanho);
	}

}

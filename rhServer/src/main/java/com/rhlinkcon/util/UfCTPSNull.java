package com.rhlinkcon.util;

import com.rhlinkcon.model.UnidadeFederativa;

public class UfCTPSNull {

	public static UnidadeFederativa retornarUnidadeFederativaNula() {
		UnidadeFederativa uf = new UnidadeFederativa();
		uf.setSigla("XX");
		uf.setEstado("XXX"); 
		
		return uf;
	}
}

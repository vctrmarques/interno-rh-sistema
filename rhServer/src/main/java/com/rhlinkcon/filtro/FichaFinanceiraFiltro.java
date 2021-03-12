package com.rhlinkcon.filtro;

import java.time.Instant;

public class FichaFinanceiraFiltro {

	public Integer ano;
	
	public Instant periodoInicial;
	
	public Instant periodoFinal;

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Instant getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Instant periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Instant getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Instant periodoFinal) {
		this.periodoFinal = periodoFinal;
	}
	
}

package com.rhlinkcon.payload.simuladorAposentadoria;

import java.time.Period;

public class TempoTotal {

	private int anos;

	private int meses;

	private int dias;

	private long totalDias;

	public TempoTotal() {
	}

	public TempoTotal(int anos, int meses, int dias) {
		this.anos = anos;
		this.meses = meses;
		this.dias = dias;
		this.totalDias = anos * 365 + meses * 30 + dias;
	}

	public TempoTotal(Period period, long totalDias) {
		this.anos = period.getYears();
		this.meses = period.getMonths();
		this.dias = period.getDays();
		this.totalDias = totalDias;

	}

	public int getAnos() {
		return anos;
	}

	public void setAnos(int anos) {
		this.anos = anos;
	}

	public int getMeses() {
		return meses;
	}

	public void setMeses(int meses) {
		this.meses = meses;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public long getTotalDias() {
		return totalDias;
	}

	public void setTotalDias(long totalDias) {
		this.totalDias = totalDias;
	}

}

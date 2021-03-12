package com.rhlinkcon.payload.simuladorAposentadoria;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

public class UtilsSimulador {

	public static TempoTotal periodoContribuicao(Date inicio, Date fim) {

		LocalDate fimLocalDate = fim.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1);

		LocalDate inicioLocalDate = inicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		Period period = Period.between(inicioLocalDate, fimLocalDate);
		long p2 = ChronoUnit.DAYS.between(inicioLocalDate, fimLocalDate);
//		System.out.println("You are " + period.getYears() + " years, " + period.getMonths() + " months, and "
//				+ period.getDays() + " days old. (" + p2 + " days total)");

		return new TempoTotal(period, p2);

	}

	public static TempoTotal periodoContribuicaoSomar(TempoTotal tempoTotal, TempoTotal tempoTotalAdicionar) {

		long totalDias1 = 0;
		long totalDias2 = 0;

		if (Objects.nonNull(tempoTotal))
			totalDias1 = tempoTotal.getTotalDias();

		if (Objects.nonNull(tempoTotalAdicionar))
			totalDias2 = tempoTotalAdicionar.getTotalDias();

		return getTempoTotal(totalDias1 + totalDias2);

	}

	public static TempoTotal periodoContribuicaoSubtrair(TempoTotal tempoTotal, TempoTotal tempoTotalRemover) {

		long totalDias1 = 0;
		long totalDias2 = 0;

		if (Objects.nonNull(tempoTotal))
			totalDias1 = tempoTotal.getTotalDias();

		if (Objects.nonNull(tempoTotalRemover))
			totalDias2 = tempoTotalRemover.getTotalDias();

		return getTempoTotal(totalDias1 - totalDias2);

	}

	public static TempoTotal getTempoTotal(long dias) {

		TempoTotal tempoTotalResult = new TempoTotal();
		tempoTotalResult.setTotalDias(dias);

		if (dias >= 365) {
			long anos = dias / 365;
			tempoTotalResult.setAnos((int) anos);
			dias = dias % 365;
		}

		if (dias >= 30) {
			long meses = dias / 30;
			tempoTotalResult.setMeses((int) meses);
			dias = dias % 30;
		}

		if (dias > 0) {
			tempoTotalResult.setDias((int) dias);
		}

		return tempoTotalResult;

	}

	public static int carregarAnosEmDias(int anos) {
		return (int) Math.floor(anos * 365.25);
	}

}

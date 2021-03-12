package com.rhlinkcon.service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rhlinkcon.model.DiaUtil;
import com.rhlinkcon.payload.diaUtil.DiaUtilRequest;
import com.rhlinkcon.payload.diaUtil.DiaUtilResponse;
import com.rhlinkcon.payload.diaUtil.DtoDiaUtil;
import com.rhlinkcon.payload.diaUtil.DtoDiaUtilConsulta;
import com.rhlinkcon.repository.DiaUtilRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class DiaUtilService {

	@Autowired
	private DiaUtilRepository diaUtilRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	public DiaUtilResponse getAllDiasUteis(String ano) {

		List<DtoDiaUtilConsulta> diasUteis = diaUtilRepository.diasUteisDoAno(ano);

		HashMap<String, DtoDiaUtil> dadosDoMes = new HashMap<String, DtoDiaUtil>();

		if (Utils.checkList(diasUteis)) {
			
			YearMonth anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("1"));

			int diasNoMes = anoMesObjeto.lengthOfMonth();

			int[] diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);
			DtoDiaUtil objetoJaneiro = new DtoDiaUtil();

			objetoJaneiro.setSegunda(diasCalculados[0]);
			objetoJaneiro.setTerca(diasCalculados[1]);
			objetoJaneiro.setQuarta(diasCalculados[2]);
			objetoJaneiro.setQuinta(diasCalculados[3]);
			objetoJaneiro.setSexta(diasCalculados[4]);
			objetoJaneiro.setSabado(diasCalculados[5]);
			objetoJaneiro.setDomingo(diasCalculados[6]);
			objetoJaneiro.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("0", objetoJaneiro);
			
			anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("2"));
			
			DtoDiaUtil objetoFevereiro = new DtoDiaUtil();
			
			diasNoMes = anoMesObjeto.lengthOfMonth();
			diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);

			objetoFevereiro.setSegunda(diasCalculados[0]);
			objetoFevereiro.setTerca(diasCalculados[1]);
			objetoFevereiro.setQuarta(diasCalculados[2]);
			objetoFevereiro.setQuinta(diasCalculados[3]);
			objetoFevereiro.setSexta(diasCalculados[4]);
			objetoFevereiro.setSabado(diasCalculados[5]);
			objetoFevereiro.setDomingo(diasCalculados[6]);
			objetoFevereiro.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("1", objetoFevereiro);		
			
			anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("3"));
			
			DtoDiaUtil objetoMarco = new DtoDiaUtil();
			
			diasNoMes = anoMesObjeto.lengthOfMonth();
			diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);

			objetoMarco.setSegunda(diasCalculados[0]);
			objetoMarco.setTerca(diasCalculados[1]);
			objetoMarco.setQuarta(diasCalculados[2]);
			objetoMarco.setQuinta(diasCalculados[3]);
			objetoMarco.setSexta(diasCalculados[4]);
			objetoMarco.setSabado(diasCalculados[5]);
			objetoMarco.setDomingo(diasCalculados[6]);
			objetoMarco.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("2", objetoMarco);
			
			anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("4"));
			
			DtoDiaUtil objetoAbril = new DtoDiaUtil();
			
			diasNoMes = anoMesObjeto.lengthOfMonth();
			diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);

			objetoAbril.setSegunda(diasCalculados[0]);
			objetoAbril.setTerca(diasCalculados[1]);
			objetoAbril.setQuarta(diasCalculados[2]);
			objetoAbril.setQuinta(diasCalculados[3]);
			objetoAbril.setSexta(diasCalculados[4]);
			objetoAbril.setSabado(diasCalculados[5]);
			objetoAbril.setDomingo(diasCalculados[6]);
			objetoAbril.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("3", objetoAbril);
			
			anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("5"));
			
			DtoDiaUtil objetoMaio = new DtoDiaUtil();
			
			diasNoMes = anoMesObjeto.lengthOfMonth();
			diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);

			objetoMaio.setSegunda(diasCalculados[0]);
			objetoMaio.setTerca(diasCalculados[1]);
			objetoMaio.setQuarta(diasCalculados[2]);
			objetoMaio.setQuinta(diasCalculados[3]);
			objetoMaio.setSexta(diasCalculados[4]);
			objetoMaio.setSabado(diasCalculados[5]);
			objetoMaio.setDomingo(diasCalculados[6]);
			objetoMaio.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("4", objetoMaio);
			
			anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("6"));
			
			DtoDiaUtil objetoJunho = new DtoDiaUtil();
			
			diasNoMes = anoMesObjeto.lengthOfMonth();
			diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);

			objetoJunho.setSegunda(diasCalculados[0]);
			objetoJunho.setTerca(diasCalculados[1]);
			objetoJunho.setQuarta(diasCalculados[2]);
			objetoJunho.setQuinta(diasCalculados[3]);
			objetoJunho.setSexta(diasCalculados[4]);
			objetoJunho.setSabado(diasCalculados[5]);
			objetoJunho.setDomingo(diasCalculados[6]);
			objetoJunho.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("5", objetoJunho);
			
			anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("7"));
			
			DtoDiaUtil objetoJulho = new DtoDiaUtil();
			
			diasNoMes = anoMesObjeto.lengthOfMonth();
			diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);

			objetoJulho.setSegunda(diasCalculados[0]);
			objetoJulho.setTerca(diasCalculados[1]);
			objetoJulho.setQuarta(diasCalculados[2]);
			objetoJulho.setQuinta(diasCalculados[3]);
			objetoJulho.setSexta(diasCalculados[4]);
			objetoJulho.setSabado(diasCalculados[5]);
			objetoJulho.setDomingo(diasCalculados[6]);
			objetoJulho.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("6", objetoJulho);	
			
			anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("8"));
			
			DtoDiaUtil objetoAgosto = new DtoDiaUtil();
			
			diasNoMes = anoMesObjeto.lengthOfMonth();
			diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);

			objetoAgosto.setSegunda(diasCalculados[0]);
			objetoAgosto.setTerca(diasCalculados[1]);
			objetoAgosto.setQuarta(diasCalculados[2]);
			objetoAgosto.setQuinta(diasCalculados[3]);
			objetoAgosto.setSexta(diasCalculados[4]);
			objetoAgosto.setSabado(diasCalculados[5]);
			objetoAgosto.setDomingo(diasCalculados[6]);
			objetoAgosto.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("7", objetoAgosto);
			
			anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("9"));
			
			DtoDiaUtil objetoSetembro = new DtoDiaUtil();
			
			diasNoMes = anoMesObjeto.lengthOfMonth();
			diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);

			objetoSetembro.setSegunda(diasCalculados[0]);
			objetoSetembro.setTerca(diasCalculados[1]);
			objetoSetembro.setQuarta(diasCalculados[2]);
			objetoSetembro.setQuinta(diasCalculados[3]);
			objetoSetembro.setSexta(diasCalculados[4]);
			objetoSetembro.setSabado(diasCalculados[5]);
			objetoSetembro.setDomingo(diasCalculados[6]);
			objetoSetembro.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("8", objetoSetembro);
			
			anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("10"));
			
			DtoDiaUtil objetoOutubro = new DtoDiaUtil();
			
			diasNoMes = anoMesObjeto.lengthOfMonth();
			diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);

			objetoOutubro.setSegunda(diasCalculados[0]);
			objetoOutubro.setTerca(diasCalculados[1]);
			objetoOutubro.setQuarta(diasCalculados[2]);
			objetoOutubro.setQuinta(diasCalculados[3]);
			objetoOutubro.setSexta(diasCalculados[4]);
			objetoOutubro.setSabado(diasCalculados[5]);
			objetoOutubro.setDomingo(diasCalculados[6]);
			objetoOutubro.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("9", objetoOutubro);
			
			anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("11"));
			
			DtoDiaUtil objetoNovembro = new DtoDiaUtil();
			
			diasNoMes = anoMesObjeto.lengthOfMonth();
			diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);

			objetoNovembro.setSegunda(diasCalculados[0]);
			objetoNovembro.setTerca(diasCalculados[1]);
			objetoNovembro.setQuarta(diasCalculados[2]);
			objetoNovembro.setQuinta(diasCalculados[3]);
			objetoNovembro.setSexta(diasCalculados[4]);
			objetoNovembro.setSabado(diasCalculados[5]);
			objetoNovembro.setDomingo(diasCalculados[6]);
			objetoNovembro.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("10", objetoNovembro);
			
			anoMesObjeto = YearMonth.of(new Integer(diasUteis.get(0).getAno()),
					getMesDoAno("12"));
			
			DtoDiaUtil objetoDezembro = new DtoDiaUtil();
			
			diasNoMes = anoMesObjeto.lengthOfMonth();
			diasCalculados = getQuantidadeNoMes(anoMesObjeto, diasNoMes);

			objetoDezembro.setSegunda(diasCalculados[0]);
			objetoDezembro.setTerca(diasCalculados[1]);
			objetoDezembro.setQuarta(diasCalculados[2]);
			objetoDezembro.setQuinta(diasCalculados[3]);
			objetoDezembro.setSexta(diasCalculados[4]);
			objetoDezembro.setSabado(diasCalculados[5]);
			objetoDezembro.setDomingo(diasCalculados[6]);
			objetoDezembro.setMes(String.format("%02d",anoMesObjeto.getMonthValue()));
			dadosDoMes.put("11", objetoDezembro);			

			List<DtoDiaUtilConsulta> janeiro = diasUteis.stream().filter(o -> o.getMes().equals("01"))
					.collect(Collectors.toList());
			for (int i = 0; i < janeiro.size(); i++) {
				switch (janeiro.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoJaneiro.getSegunda() - ((int) janeiro.get(i).getTotalDiaDaSemana());
					objetoJaneiro.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoJaneiro.getTerca() - ((int) janeiro.get(i).getTotalDiaDaSemana());
					objetoJaneiro.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoJaneiro.getQuarta() - ((int) janeiro.get(i).getTotalDiaDaSemana());
					objetoJaneiro.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoJaneiro.getQuinta() - ((int) janeiro.get(i).getTotalDiaDaSemana());
					objetoJaneiro.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoJaneiro.getSexta() - ((int) janeiro.get(i).getTotalDiaDaSemana());
					objetoJaneiro.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoJaneiro.getSabado() - ((int) janeiro.get(i).getTotalDiaDaSemana());
					objetoJaneiro.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoJaneiro.getDomingo() - ((int) janeiro.get(i).getTotalDiaDaSemana());
					objetoJaneiro.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("0", objetoJaneiro);
			
			List<DtoDiaUtilConsulta> fevereiro = diasUteis.stream().filter(o -> o.getMes().equals("02"))
					.collect(Collectors.toList());
			for (int i = 0; i < fevereiro.size(); i++) {
				switch (fevereiro.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoFevereiro.getSegunda() - ((int) fevereiro.get(i).getTotalDiaDaSemana());
					objetoFevereiro.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoFevereiro.getTerca() - ((int) fevereiro.get(i).getTotalDiaDaSemana());
					objetoFevereiro.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoFevereiro.getQuarta() - ((int) fevereiro.get(i).getTotalDiaDaSemana());
					objetoFevereiro.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoFevereiro.getQuinta() - ((int) fevereiro.get(i).getTotalDiaDaSemana());
					objetoFevereiro.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoFevereiro.getSexta() - ((int) fevereiro.get(i).getTotalDiaDaSemana());
					objetoFevereiro.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoFevereiro.getSabado() - ((int) fevereiro.get(i).getTotalDiaDaSemana());
					objetoFevereiro.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoFevereiro.getDomingo() - ((int) fevereiro.get(i).getTotalDiaDaSemana());
					objetoFevereiro.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("1", objetoFevereiro);
			
			List<DtoDiaUtilConsulta> marco = diasUteis.stream().filter(o -> o.getMes().equals("03"))
					.collect(Collectors.toList());
			for (int i = 0; i < marco.size(); i++) {
				switch (marco.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoMarco.getSegunda() - ((int) marco.get(i).getTotalDiaDaSemana());
					objetoMarco.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoMarco.getTerca() - ((int) marco.get(i).getTotalDiaDaSemana());
					objetoMarco.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoMarco.getQuarta() - ((int) marco.get(i).getTotalDiaDaSemana());
					objetoMarco.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoMarco.getQuinta() - ((int) marco.get(i).getTotalDiaDaSemana());
					objetoMarco.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoMarco.getSexta() - ((int) marco.get(i).getTotalDiaDaSemana());
					objetoMarco.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoMarco.getSabado() - ((int) marco.get(i).getTotalDiaDaSemana());
					objetoMarco.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoMarco.getDomingo() - ((int) marco.get(i).getTotalDiaDaSemana());
					objetoMarco.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("2", objetoMarco);
			
			List<DtoDiaUtilConsulta> abril = diasUteis.stream().filter(o -> o.getMes().equals("04"))
					.collect(Collectors.toList());
			for (int i = 0; i < abril.size(); i++) {
				switch (abril.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoAbril.getSegunda() - ((int) abril.get(i).getTotalDiaDaSemana());
					objetoAbril.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoAbril.getTerca() - ((int) abril.get(i).getTotalDiaDaSemana());
					objetoAbril.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoAbril.getQuarta() - ((int) abril.get(i).getTotalDiaDaSemana());
					objetoAbril.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoAbril.getQuinta() - ((int) abril.get(i).getTotalDiaDaSemana());
					objetoAbril.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoAbril.getSexta() - ((int) abril.get(i).getTotalDiaDaSemana());
					objetoAbril.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoAbril.getSabado() - ((int) abril.get(i).getTotalDiaDaSemana());
					objetoAbril.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoAbril.getDomingo() - ((int) abril.get(i).getTotalDiaDaSemana());
					objetoAbril.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("3", objetoAbril);	
			
			List<DtoDiaUtilConsulta> maio = diasUteis.stream().filter(o -> o.getMes().equals("05"))
					.collect(Collectors.toList());
			for (int i = 0; i < maio.size(); i++) {
				switch (maio.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoMaio.getSegunda() - ((int) maio.get(i).getTotalDiaDaSemana());
					objetoMaio.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoMaio.getTerca() - ((int) maio.get(i).getTotalDiaDaSemana());
					objetoMaio.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoMaio.getQuarta() - ((int) maio.get(i).getTotalDiaDaSemana());
					objetoMaio.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoMaio.getQuinta() - ((int) maio.get(i).getTotalDiaDaSemana());
					objetoMaio.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoMaio.getSexta() - ((int) maio.get(i).getTotalDiaDaSemana());
					objetoMaio.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoMaio.getSabado() - ((int) maio.get(i).getTotalDiaDaSemana());
					objetoMaio.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoMaio.getDomingo() - ((int) maio.get(i).getTotalDiaDaSemana());
					objetoMaio.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("4", objetoMaio);	
			
			List<DtoDiaUtilConsulta> junho = diasUteis.stream().filter(o -> o.getMes().equals("06"))
					.collect(Collectors.toList());
			for (int i = 0; i < junho.size(); i++) {
				switch (junho.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoJunho.getSegunda() - ((int) junho.get(i).getTotalDiaDaSemana());
					objetoJunho.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoJunho.getTerca() - ((int) junho.get(i).getTotalDiaDaSemana());
					objetoJunho.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoJunho.getQuarta() - ((int) junho.get(i).getTotalDiaDaSemana());
					objetoJunho.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoJunho.getQuinta() - ((int) junho.get(i).getTotalDiaDaSemana());
					objetoJunho.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoJunho.getSexta() - ((int) junho.get(i).getTotalDiaDaSemana());
					objetoJunho.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoJunho.getSabado() - ((int) junho.get(i).getTotalDiaDaSemana());
					objetoJunho.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoJunho.getDomingo() - ((int) junho.get(i).getTotalDiaDaSemana());
					objetoJunho.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("5", objetoJunho);	
			
			List<DtoDiaUtilConsulta> julho = diasUteis.stream().filter(o -> o.getMes().equals("07"))
					.collect(Collectors.toList());
			for (int i = 0; i < julho.size(); i++) {
				switch (julho.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoJulho.getSegunda() - ((int) julho.get(i).getTotalDiaDaSemana());
					objetoJulho.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoJulho.getTerca() - ((int) julho.get(i).getTotalDiaDaSemana());
					objetoJulho.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoJulho.getQuarta() - ((int) julho.get(i).getTotalDiaDaSemana());
					objetoJulho.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoJulho.getQuinta() - ((int) julho.get(i).getTotalDiaDaSemana());
					objetoJulho.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoJulho.getSexta() - ((int) julho.get(i).getTotalDiaDaSemana());
					objetoJulho.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoJulho.getSabado() - ((int) julho.get(i).getTotalDiaDaSemana());
					objetoJulho.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoJulho.getDomingo() - ((int) julho.get(i).getTotalDiaDaSemana());
					objetoJulho.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("6", objetoJulho);	
			
			List<DtoDiaUtilConsulta> agosto = diasUteis.stream().filter(o -> o.getMes().equals("08"))
					.collect(Collectors.toList());
			for (int i = 0; i < agosto.size(); i++) {
				switch (agosto.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoAgosto.getSegunda() - ((int) agosto.get(i).getTotalDiaDaSemana());
					objetoAgosto.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoAgosto.getTerca() - ((int) agosto.get(i).getTotalDiaDaSemana());
					objetoAgosto.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoAgosto.getQuarta() - ((int) agosto.get(i).getTotalDiaDaSemana());
					objetoAgosto.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoAgosto.getQuinta() - ((int) agosto.get(i).getTotalDiaDaSemana());
					objetoAgosto.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoAgosto.getSexta() - ((int) agosto.get(i).getTotalDiaDaSemana());
					objetoAgosto.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoAgosto.getSabado() - ((int) agosto.get(i).getTotalDiaDaSemana());
					objetoAgosto.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoAgosto.getDomingo() - ((int) agosto.get(i).getTotalDiaDaSemana());
					objetoAgosto.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("7", objetoAgosto);	
			
			List<DtoDiaUtilConsulta> setembro = diasUteis.stream().filter(o -> o.getMes().equals("09"))
					.collect(Collectors.toList());
			for (int i = 0; i < setembro.size(); i++) {
				switch (setembro.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoSetembro.getSegunda() - ((int) setembro.get(i).getTotalDiaDaSemana());
					objetoSetembro.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoSetembro.getTerca() - ((int) setembro.get(i).getTotalDiaDaSemana());
					objetoSetembro.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoSetembro.getQuarta() - ((int) setembro.get(i).getTotalDiaDaSemana());
					objetoSetembro.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoSetembro.getQuinta() - ((int) setembro.get(i).getTotalDiaDaSemana());
					objetoSetembro.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoSetembro.getSexta() - ((int) setembro.get(i).getTotalDiaDaSemana());
					objetoSetembro.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoSetembro.getSabado() - ((int) setembro.get(i).getTotalDiaDaSemana());
					objetoSetembro.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoSetembro.getDomingo() - ((int) setembro.get(i).getTotalDiaDaSemana());
					objetoSetembro.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("8", objetoSetembro);
			
			List<DtoDiaUtilConsulta> outubro = diasUteis.stream().filter(o -> o.getMes().equals("10"))
					.collect(Collectors.toList());
			for (int i = 0; i < outubro.size(); i++) {
				switch (outubro.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoOutubro.getSegunda() - ((int) outubro.get(i).getTotalDiaDaSemana());
					objetoOutubro.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoOutubro.getTerca() - ((int) outubro.get(i).getTotalDiaDaSemana());
					objetoOutubro.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoOutubro.getQuarta() - ((int) outubro.get(i).getTotalDiaDaSemana());
					objetoOutubro.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoOutubro.getQuinta() - ((int) outubro.get(i).getTotalDiaDaSemana());
					objetoOutubro.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoOutubro.getSexta() - ((int) outubro.get(i).getTotalDiaDaSemana());
					objetoOutubro.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoOutubro.getSabado() - ((int) outubro.get(i).getTotalDiaDaSemana());
					objetoOutubro.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoOutubro.getDomingo() - ((int) outubro.get(i).getTotalDiaDaSemana());
					objetoOutubro.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("9", objetoOutubro);		
			
			List<DtoDiaUtilConsulta> novembro = diasUteis.stream().filter(o -> o.getMes().equals("11"))
					.collect(Collectors.toList());
			for (int i = 0; i < novembro.size(); i++) {
				switch (novembro.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoNovembro.getSegunda() - ((int) novembro.get(i).getTotalDiaDaSemana());
					objetoNovembro.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoNovembro.getTerca() - ((int) novembro.get(i).getTotalDiaDaSemana());
					objetoNovembro.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoNovembro.getQuarta() - ((int) novembro.get(i).getTotalDiaDaSemana());
					objetoNovembro.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoNovembro.getQuinta() - ((int) novembro.get(i).getTotalDiaDaSemana());
					objetoNovembro.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoNovembro.getSexta() - ((int) novembro.get(i).getTotalDiaDaSemana());
					objetoNovembro.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoNovembro.getSabado() - ((int) novembro.get(i).getTotalDiaDaSemana());
					objetoNovembro.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoNovembro.getDomingo() - ((int) novembro.get(i).getTotalDiaDaSemana());
					objetoNovembro.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("10", objetoNovembro);
			
			List<DtoDiaUtilConsulta> dezembro = diasUteis.stream().filter(o -> o.getMes().equals("12"))
					.collect(Collectors.toList());
			for (int i = 0; i < dezembro.size(); i++) {
				switch (dezembro.get(i).getDiaDaSemana()) {

				case "SEGUNDA-FEIRA":
					int totalSegunda = objetoDezembro.getSegunda() - ((int) dezembro.get(i).getTotalDiaDaSemana());
					objetoDezembro.setSegunda(totalSegunda);
					break;
				case "TERÇA-FEIRA":
					int totalTerca = objetoDezembro.getTerca() - ((int) dezembro.get(i).getTotalDiaDaSemana());
					objetoDezembro.setTerca(totalTerca);
					break;
				case "QUARTA-FEIRA":
					int totalQuarta = objetoDezembro.getQuarta() - ((int) dezembro.get(i).getTotalDiaDaSemana());
					objetoDezembro.setQuarta(totalQuarta);
					break;
				case "QUINTA-FEIRA":
					int totalQuinta = objetoDezembro.getQuinta() - ((int) dezembro.get(i).getTotalDiaDaSemana());
					objetoDezembro.setQuinta(totalQuinta);
					break;
				case "SEXTA-FEIRA":
					int totalSexta = objetoDezembro.getSexta() - ((int) dezembro.get(i).getTotalDiaDaSemana());
					objetoDezembro.setSexta(totalSexta);
					break;
				case "SÁBADO":
					int totalSabado = objetoDezembro.getSabado() - ((int) dezembro.get(i).getTotalDiaDaSemana());
					objetoDezembro.setSabado(totalSabado);
					break;
				case "DOMINGO":
					int totalDomingo = objetoDezembro.getDomingo() - ((int) dezembro.get(i).getTotalDiaDaSemana());
					objetoDezembro.setDomingo(totalDomingo);
					break;
				}
			}
			
			dadosDoMes.put("11", objetoDezembro);				

		}

		DiaUtilResponse diaUtilResponse = new DiaUtilResponse();
		
		if(!diasUteis.isEmpty()) {
			diaUtilResponse.setAno(diasUteis.get(0).getAno());
		}
		
		diaUtilResponse.setDadosDosMeses(dadosDoMes);

		return diaUtilResponse;

	}
	
	
	public List<DiaUtil> getDiasUteis() {
		
		List<DiaUtil> diasUteis = diaUtilRepository.findAll();
		
		return diasUteis;
	}
	
	public List<DiaUtil> getDiasUteisPorMes(String ano, String mes) {
		
		List<DiaUtil> diasUteis = diaUtilRepository.findByAnoAndMes(ano, mes);
		
		return diasUteis;
	}	

	public void createDiaUtil(DiaUtilRequest diaUtilRequest) {
		
		if (Utils.checkList(diaUtilRequest.getArrayListDiasUteis())) {
			
			for (LocalDate dataDiaUtil : diaUtilRequest.getArrayListDiasUteis()) {
				DiaUtil diaUtil = new DiaUtil();

				LocalDate diaMesAno = LocalDate.from(dataDiaUtil);
				Integer dia = diaMesAno.getDayOfMonth();
				Integer mes = diaMesAno.getMonthValue();
				Integer ano = diaMesAno.getYear();
				String diaDaSemana = getDiaDaSemanaEmPortugues(diaMesAno.getDayOfWeek().name());

				diaUtil.setDia(String.format("%02d", dia));
				diaUtil.setMes(String.format("%02d", mes));
				diaUtil.setAno(ano.toString());
				diaUtil.setDiaDaSemana(diaDaSemana);

				diaUtilRepository.save(diaUtil);
			}
		}

	}

	public void deleteDiaUtil(String diaUtilAno) {

		diaUtilRepository.deleteByAnoIgnoreCaseContaining(diaUtilAno);
	}
	
	public ResponseEntity<?> deleteAllDiasUteis() {

		diaUtilRepository.deleteAll();
		
		return ResponseEntity.ok().build();
	}
	
	public void deleteDiasUteisPorMes(String ano, String mes) {

		diaUtilRepository.deleteByAnoAndMes(ano, mes);
	}		

	private String getDiaDaSemanaEmPortugues(String nome) {
		Map<String, String> ArrayDiasDaSemana = new HashMap<String, String>();

		ArrayDiasDaSemana.put("SUNDAY", new String("DOMINGO"));
		ArrayDiasDaSemana.put("MONDAY", new String("SEGUNDA-FEIRA"));
		ArrayDiasDaSemana.put("THURSDAY", new String("QUINTA-FEIRA"));
		ArrayDiasDaSemana.put("WEDNESDAY", new String("QUARTA-FEIRA"));
		ArrayDiasDaSemana.put("SATURDAY", new String("SÁBADO"));
		ArrayDiasDaSemana.put("FRIDAY", new String("SEXTA-FEIRA"));
		ArrayDiasDaSemana.put("TUESDAY", new String("TERÇA-FEIRA"));

		return ArrayDiasDaSemana.get(nome);
	}

	private Month getMesDoAno(String mes) {
		Map<String, Month> ArrayMesDoAno = new HashMap<String, Month>();

		ArrayMesDoAno.put("1", Month.JANUARY);
		ArrayMesDoAno.put("2", Month.FEBRUARY);
		ArrayMesDoAno.put("3", Month.MARCH);
		ArrayMesDoAno.put("4", Month.APRIL);
		ArrayMesDoAno.put("5", Month.MAY);
		ArrayMesDoAno.put("6", Month.JUNE);
		ArrayMesDoAno.put("7", Month.JULY);
		ArrayMesDoAno.put("8", Month.AUGUST);
		ArrayMesDoAno.put("9", Month.SEPTEMBER);
		ArrayMesDoAno.put("10", Month.OCTOBER);
		ArrayMesDoAno.put("11", Month.NOVEMBER);
		ArrayMesDoAno.put("12", Month.DECEMBER);

		return ArrayMesDoAno.get(mes);
	}

	private int[] getQuantidadeNoMes(YearMonth anoMesObjeto, int diasNoMes) {

		int[] dadosRetornados = new int[7];

		int countSegunda = 0;
		int countTerca = 0;
		int countQuarta = 0;
		int countQuinta = 0;
		int countSexta = 0;
		int countSabado = 0;
		int countDomingo = 0;

		for (int i = 1; i <= diasNoMes; i++) {

			LocalDate data = anoMesObjeto.atDay(i);

			switch (data.getDayOfWeek().toString()) {
			case "MONDAY":
				countSegunda++;
				break;
			case "TUESDAY":
				countTerca++;
				break;
			case "WEDNESDAY":
				countQuarta++;
				break;
			case "THURSDAY":
				countQuinta++;
				break;
			case "FRIDAY":
				countSexta++;
				break;
			case "SATURDAY":
				countSabado++;
				break;
			case "SUNDAY":
				countDomingo++;
				break;
			}
		}

		dadosRetornados[0] = countSegunda;
		dadosRetornados[1] = countTerca;
		dadosRetornados[2] = countQuarta;
		dadosRetornados[3] = countQuinta;
		dadosRetornados[4] = countSexta;
		dadosRetornados[5] = countSabado;
		dadosRetornados[6] = countDomingo;

		return dadosRetornados;
	}

}

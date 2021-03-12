package com.rhlinkcon.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import javax.swing.text.MaskFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.model.FundoEnum;
import com.rhlinkcon.payload.generico.ApiResponse;
import com.rhlinkcon.payload.generico.PagedRequest;

public class Utils {
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

	public static void validatePageNumberAndSize(int page, int size) {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (size > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
	}

	public static Pageable generatePegeableGeneric(PagedRequest pagedRequest) {
		if (pagedRequest.getPage() < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (pagedRequest.getSize() > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
		// ordenação
		// TODO analisar caso a entidade não possua o atributo createdAt, atualmente
		// pode ocasionar erro!
		String orderBy = pagedRequest.getOrder() == null || pagedRequest.getOrder().isEmpty() ? "createdAt"
				: pagedRequest.getOrder();
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		return PageRequest.of(pagedRequest.getPage(), pagedRequest.getSize(), direction, orderBy);
	}

	public static Pageable generatePegeableNoOrderGeneric(PagedRequest pagedRequest) {
		if (pagedRequest.getPage() < 0) {
			throw new BadRequestException("Page number cannot be less than zero.");
		}

		if (pagedRequest.getSize() > AppConstants.MAX_PAGE_SIZE) {
			throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
		}
		return PageRequest.of(pagedRequest.getPage(), pagedRequest.getSize());
	}

	public static ResponseEntity<?> created(Boolean sucess, String message) {
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(sucess, message));
	}

	public static ResponseEntity<?> created(Boolean sucess, String message, Object obj) {
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(sucess, message, obj));
	}

	public static ResponseEntity<?> ok(Boolean sucess, String message, Object obj) {
		return ResponseEntity.ok().body(new ApiResponse(sucess, message, obj));
	}

	public static ResponseEntity<?> ok(Boolean sucess, String message) {
		return ResponseEntity.ok().body(new ApiResponse(sucess, message));
	}

	public static ResponseEntity<?> badRequest(Boolean sucess, String message) {
		return ResponseEntity.badRequest().body(new ApiResponse(sucess, message));
	}

	public static ResponseEntity<?> forbidden(Boolean success, String message) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(success, message));
	}

	public static ResponseEntity<?> deleted(Boolean success, String message) {
		return ResponseEntity.status(200).body(new ApiResponse(success, message));
	}

	public static Instant setFinalDay(Instant instant) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

		return dateTime.withHour(23).withMinute(59).withSecond(59).atZone(ZoneId.systemDefault()).toInstant();
	}

	public static Instant setInitialDay(Instant instant) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

		return dateTime.withHour(00).withMinute(00).withSecond(00).atZone(ZoneId.systemDefault()).toInstant();
	}

	public static List<Order> criarOrdenacao(Pageable pageable, CriteriaBuilder builder, Root<?> root) {
		List<Order> orderList = new ArrayList<Order>();

		String orderBy = pageable.getSort().toString();
		String orderBySplit[] = orderBy.split(": ");

		if (orderBySplit[1].equals("ASC")) {
			if (orderBySplit[0].contains(".")) {
				String orderSubObjBySplit[] = orderBySplit[0].split("\\.");
				orderList.add(builder.asc(root.join(orderSubObjBySplit[0]).get(orderSubObjBySplit[1])));
			} else {
				orderList.add(builder.asc(root.get(orderBySplit[0])));
			}
		} else {
			if (orderBySplit[0].contains(".")) {
				String orderSubObjBySplit[] = orderBySplit[0].split("\\.");
				orderList.add(builder.desc(root.join(orderSubObjBySplit[0]).get(orderSubObjBySplit[1])));
			} else {
				orderList.add(builder.desc(root.get(orderBySplit[0])));
			}
		}
		return orderList;
	}

	public static List<Order> criarOrdenacao(String order, CriteriaBuilder builder, Root<?> root) {
		List<Order> orderList = new ArrayList<Order>();

		Direction direction = Sort.Direction.ASC;
		if (order.startsWith("-")) {
			order = order.replace("-", "");
			direction = Sort.Direction.DESC;
		}

		String orderBy = order;

		if (direction.isAscending()) {
			if (orderBy.contains(".")) {
				String orderSubObjBySplit[] = orderBy.split("\\.");
				orderList.add(builder.asc(root.join(orderSubObjBySplit[0]).get(orderSubObjBySplit[1])));
			} else {
				orderList.add(builder.asc(root.get(orderBy)));
			}
		} else {
			if (orderBy.contains(".")) {
				String orderSubObjBySplit[] = orderBy.split("\\.");
				orderList.add(builder.desc(root.join(orderSubObjBySplit[0]).get(orderSubObjBySplit[1])));
			} else {
				orderList.add(builder.desc(root.get(orderBy)));
			}
		}
		return orderList;
	}

	public static void adicionarPaginacao(TypedQuery<?> typedQuery, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		typedQuery.setFirstResult(primeiroRegistroDaPagina);
		typedQuery.setMaxResults(totalRegistrosPorPagina);

	}

	public static Instant stringDateToStartInstant(String dateStr) {
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

			Date dataInicial = formato.parse(dateStr);

			Instant dataInicialInst = dataInicial.toInstant().atZone(ZoneOffset.UTC).withHour(0).withMinute(0)
					.withSecond(0).toInstant();
			return dataInicialInst;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Instant stringDateToEndInstant(String dateStr) {
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

			Date dataFinal = formato.parse(dateStr);

			Instant dataFinalInst = dataFinal.toInstant().atZone(ZoneOffset.UTC).withHour(23).withMinute(59)
					.withSecond(59).toInstant();
			return dataFinalInst;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean checkNotEmpty(String str) {
		return checkStr(str) && !str.trim().isEmpty();
	}

	public static boolean checkStr(String str) {
		try {
			if (str == null || str.isEmpty())
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean setBool(Boolean bool) {
		try {
			if (bool == null || bool == false)
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean checkList(List<?> list) {
		try {
			if (list == null || list.isEmpty())
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean checkSetList(Set<?> setList) {
		try {
			if (setList == null || setList.isEmpty())
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static int calculateAge(Date birthDate) {

		LocalDate data1 = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate data2 = LocalDate.now();

		Period period = Period.between(data1, data2);
		return period.getYears();
	}

	public static int calculateAgeMonth(Date birthDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(birthDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		LocalDate data1 = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate data2 = LocalDate.now();

		Period period = Period.between(data1, data2);
		return period.getYears();
	}

	public static boolean checkNumber(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	public static PageRequest getPageRequest(Integer page, Integer size, String order) {
		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		return PageRequest.of(page, size, direction, "createdAt");
	}

	public static List<Long> decodeLongList(String s) {
		return Arrays.asList(s.split(",")).stream().map(x -> Long.valueOf(x)).collect(Collectors.toList());
	}

	public static Double roundValue(Double value) {
		if (Objects.nonNull(value)) {
			return new BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		} else {
			return 0.0;
		}
	}

	/**
	 * Método responsável por zerar horas, minutos, segundos e milisegundos de uma
	 * {@link Date}.<br>
	 * 
	 * @since 31/10/2019
	 * @param data
	 * @return {@link Date}
	 */
	public static Date getApenasData(Date data) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(data.getTime());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.AM_PM, Calendar.AM);
		return calendar.getTime();
	}

	/**
	 * Método responsável por zerar horas, minutos, segundos e milisegundos de uma
	 * {@link Date}.<br>
	 * 
	 * @since 31/10/2019
	 * @param data
	 * @return {@link Date}
	 */
	public static Date getApenasDataFinal(Date data) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(data.getTime());
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	/**
	 * Método responsável por trazer o mês como String {@link Date}.<br>
	 * 
	 * @since 31/07/20120
	 * @param data
	 * @return result
	 */
	public static String getMesCompetenciaString(Integer mes) {
		String result = "";

		switch (mes) {
		case 1:
			result = "Janeiro";
			break;
		case 2:
			result = "Fevereiro";
			break;
		case 3:
			result = "Março";
			break;
		case 4:
			result = "Abril";
			break;
		case 5:
			result = "Maio";
			break;
		case 6:
			result = "Junho";
			break;
		case 7:
			result = "Julho";
			break;
		case 8:
			result = "Agosto";
			break;
		case 9:
			result = "Setembro";
			break;
		case 10:
			result = "Outubro";
			break;
		case 11:
			result = "Novembro";
			break;
		case 12:
			result = "Dezembro";
			break;
		default:
			break;
		}

		return result;
	}

	/***
	 * Método que preenche os atributos do objeto @param to com os atributos de
	 * mesmo nome do objeto @param from. a lista de nome representa a lista dos
	 * campos que deve ser verificada para ser alterada.
	 * 
	 * @param from
	 * @param to
	 * @param fields
	 */
	public static void setValueFields(Object from, Object to, String... fields) {
		for (String fieldName : fields) {
			try {
				Field fieldFrom = from.getClass().getDeclaredField(fieldName);
				Object valueFrom = fieldFrom.get(from);
				if (!Objects.isNull(valueFrom)) {
					Field fieldTo = to.getClass().getDeclaredField(fieldName);
					fieldTo.setAccessible(true);
					fieldTo.set(to, valueFrom);
				}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			}

		}
	}

	public static String formatarCnpj(String cnpj) {
		try {
			MaskFormatter mask = new MaskFormatter("###.###.###/####-##");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(cnpj);
		} catch (ParseException ex) {
			return cnpj;
		}
	}

	public static String formatarCpf(String cpf) {
		try {
			MaskFormatter mask = new MaskFormatter("###.###.###-##");
			mask.setValueContainsLiteralCharacters(false);
			return mask.valueToString(cpf);
		} catch (ParseException ex) {
			return cpf;
		}
	}

	public static String formatarMoedaReal(Double valor) {
		valor = roundValue(valor);
		BigDecimal value = new BigDecimal(valor);
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		return nf.format(value);
	}

	/*
	 * Método que pega a data de nascimento e data de admissão do funcionário e
	 * verficica se o mesmo pertence ao fundo FUNFIN ou FUNPREV
	 * 
	 * @param dataNascimento
	 * 
	 * @param dataAdmissao
	 * 
	 * @return FundoEnum
	 * 
	 */
	public static FundoEnum getFilialPensao(Instant dataNascimento, Instant dataAdmissao) {

		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		boolean resultado = false;

		try {
			Instant dataNascidos = Utils.getApenasData(sd.parse("31/12/1954")).toInstant();
			Instant dataAdmitidos = Utils.getApenasData(sd.parse("31/04/2002")).toInstant();
			int resultadoNascidos = dataNascimento.compareTo(dataNascidos);
			int resultadoAdmitido = dataAdmissao.compareTo(dataAdmitidos);
			if (resultadoNascidos > 0 && resultadoAdmitido <= 0) {
				resultado = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return resultado ? FundoEnum.FUNFIN : FundoEnum.FUNPREV;

	}

	public static Integer getMes(Instant date) {
		LocalDateTime ldt = LocalDateTime.ofInstant(date, ZoneId.systemDefault());

		return ldt.getMonthValue();
	}

	public static Integer getAno(Instant date) {
		LocalDateTime ldt = LocalDateTime.ofInstant(date, ZoneId.systemDefault());

		return ldt.getYear();
	}

	// Function to convert snake case
	// to camel case
	public static String snakeToCamel(String str) {
		// Capitalize first letter of string
		str = str.toLowerCase();
		str = str.substring(0, 1).toUpperCase() + str.substring(1);

		// Convert to StringBuilder
		StringBuilder builder = new StringBuilder(str);

		// Traverse the string character by
		// character and remove underscore
		// and capitalize next letter
		for (int i = 0; i < builder.length(); i++) {

			// Check char is underscore
			if (builder.charAt(i) == ' ') {
				builder.replace(i + 1, i + 2, String.valueOf(Character.toUpperCase(builder.charAt(i + 1))));
			}
		}

		// Return in String type
		return builder.toString();
	}

	public static List<String> periodoString(Instant dateInicial, Instant dateFinal) {
		List<String> listPeriodoString = new ArrayList<String>();
		int anoInicial = getAno(dateInicial);
		int anoFinal = getAno(dateFinal);
		int mesInicial = getMes(dateInicial);
		int mesFinal = getMes(dateFinal);
		for (int tempAno = anoInicial; tempAno <= anoFinal; tempAno++) {
			if (tempAno == anoFinal) {
				for (int tempMes = mesInicial; tempMes <= mesFinal; tempMes++) {
					listPeriodoString.add(String.valueOf(tempMes) + "/" + String.valueOf(anoFinal));
				}
			} else {
				for (int tempMes = mesInicial; tempMes <= 12; tempMes++) {
					listPeriodoString.add(String.valueOf(tempMes) + "/" + String.valueOf(tempAno));
				}
				mesInicial = 1;
			}
		}

		return listPeriodoString;
	}

	public static List<String> periodoString(Integer ano) {
		List<String> listPeriodoString = new ArrayList<String>();
		int mesInicial = 1;
		int mesFinal = 12;
		for (int tempMes = mesInicial; tempMes <= mesFinal; tempMes++) {
			listPeriodoString.add(String.valueOf(tempMes) + "/" + String.valueOf(ano));
		}

		return listPeriodoString;
	}

	public static String getHashStringLocalDateTimeNow() {
		LocalDateTime localDateTime = LocalDateTime.now();
		return Integer.toString(localDateTime.getYear()) + Integer.toString(localDateTime.getMonthValue())
				+ Integer.toString(localDateTime.getDayOfMonth()) + Integer.toString(localDateTime.getHour())
				+ Integer.toString(localDateTime.getMinute()) + Integer.toString(localDateTime.getSecond());
	}

	public static String getUsuarioLogado() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();
	}
}

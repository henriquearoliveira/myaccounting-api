package br.com.contability.utilitario;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

public class CaixaDeFerramentas {
	
	/**
	 * @param keyExtractor
	 * @return
	 * 
	 *  O MAPA SÓ USA AQUI DENTRO, ELE É USADO PRA CONSEGUIR DESTINGUIR SE ADICIONA OU NÃO
	 *  ASSIM QUE ELE CONSEGUE FILTRAR COM O STREAM
	 *  
	 *  OBJECT ESTAVA NO LUGAR DE R
	 */
	public static <T, R> Predicate<T> distinctByKey(Function<? super T, R> keyExtractor) {
	    Map<R,Boolean> seen = new ConcurrentHashMap<>();
	    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
	/**
	 * @param valor
	 * @return
	 */
	public static BigDecimal alteraValorParaPositivo(BigDecimal valor) {
		
		if (valor.signum() == -1) {
			return valor.negate();
		}
		
		return valor;
	}
	
	/**
	 * @param seconds
	 * 
	 * SLEEP DA THREAD
	 */
	public void aguardaTempoThread(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param valorConversao
	 * @return
	 */
	public static BigDecimal converteStringToBidDecimal(String valorConversao) {
		
		StringBuffer s = new StringBuffer(valorConversao);
		
		String valorFormatada = s.reverse().toString().substring(2).contains(",") 
				? convertePontosAndVirgulas(valorConversao)
				: valorConversao;
				
		BigDecimal bigDecimal = new BigDecimal(valorFormatada);

		return bigDecimal;
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static LocalDate converteDateToLocalDate(Date date) {
		
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	/**
	 * @param valorConversao
	 * @return
	 */
	private static String convertePontosAndVirgulas(String valorConversao) {
		return valorConversao.replaceAll("\\.", "").replaceAll("\\,", ".");
	}
	
	/**
	 * @param dateToFormat
	 * @return
	 */
	public static LocalDate calendarFromStringMesAnoDate(String dateToFormat) {

		Date date = new Date();

		try {
			date = new SimpleDateFormat("MMMMM yyyy", Locale.US).parse(dateToFormat);
			;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return localDate;
	}
	
	/**
	 * @param dateToFormat
	 * @return
	 */
	public static LocalDate calendarFromStringDiaMesAnoDate(String dateToFormat) {

		LocalDate localDate = LocalDate.parse(dateToFormat);
		
		return localDate;
	}

	/**
	 * @param dateTimeCloudinary
	 * @return LocalDateTime
	 */
	public static LocalDateTime stringToLocalDateTime(String dateTime) {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return LocalDateTime.parse(dateTime.replace("T", " ").replace("Z", ""), format);

	}
	
	/**
	 * @param dateTimeCloudinary
	 * @return LocalDateTime
	 */
	public static LocalDate stringToLocalDateEXCEL(String date) {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return LocalDate.parse(date, format);

	}
	
	/**
	 * @param dateTimeCloudinary
	 * @return LocalDateTime
	 */
	public static LocalDate stringToLocalDateLibreOffice(String dateLibreOffice) {

		DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
		Date date = null;
		try {
			date = (Date)formatter.parse(dateLibreOffice);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println("LocalDate: " + converteDateToLocalDate(date));
		
		return converteDateToLocalDate(date);

	}

	/**
	 * @param quatidadeCaracteres
	 * @return codigo
	 */
	public static String geraCodigo(int quatidadeCaracteres) {

		String codigo = UUID.randomUUID().toString().replaceAll("-", "").substring(0, (quatidadeCaracteres));

		return codigo;

	}

	/**
	 * @param request
	 * @return String PARA VINCULAR A CONFIRMAÇÃO DO LOGIN DE UM NOVO USUARIO
	 */
	public static String configureURLdesired(HttpServletRequest request, String urlParametersDesired) {
		return request.getRequestURL().toString().replace(request.getRequestURI(), urlParametersDesired);
	}

}

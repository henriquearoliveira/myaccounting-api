package br.com.contability.business;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public enum Mes {
	
	JANEIRO("Janeiro", 1), FEVEREIRO("Fevereiro", 2), MARCO("Março", 3),
	ABRIL("Abril", 4), MAIO("Maio", 5), JUNHO("Junho", 6),
	JULHO("Julho", 7), AGOSTO("Agosto", 8), SETEMBRO("Setembro", 9),
	OUTUBRO("Outubro", 10), NOVEMBRO("Novembro", 11), DEZEMBRO("Dezembro", 12);
	
	private String descricao;
	
	@SuppressWarnings("unused")
	private int valor;
	
	Mes(String descricao, int valor) {
		this.descricao = descricao;
		this.valor = valor;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public int getValor() {
		return valor;
	}
	
	public static String getBy(int intValor) {
		
		Mes[] meses = Mes.values();
		
		String mesCorreto = null;
		
		for (Mes mes : meses) {
			
			if (mes.getValor() == intValor) {
				mesCorreto = mes.getDescricao();
			} 
				
		}
		
		return mesCorreto;
		
	}
	
	@SuppressWarnings("static-access")
	public static List<String> getMesesString() {
		
		Semestre semestre = getSemestre();

		if (semestre.PRIMEIRO == Semestre.PRIMEIRO) {
			return Arrays.asList(JANEIRO.descricao, FEVEREIRO.descricao,
					MARCO.descricao, ABRIL.descricao, MAIO.descricao, JUNHO.descricao);
		} else {
			return Arrays.asList(JULHO.descricao, AGOSTO.descricao, SETEMBRO.descricao,
					OUTUBRO.descricao, NOVEMBRO.descricao, DEZEMBRO.descricao);
		}
		
	}
	
	@SuppressWarnings("static-access")
	public static List<Mes> getMeses() {
		
		Semestre semestre = getSemestre();

		if (semestre.PRIMEIRO == Semestre.PRIMEIRO) {
			return Arrays.asList(JANEIRO, FEVEREIRO, MARCO, ABRIL, MAIO, JUNHO);
		} else {
			return Arrays.asList(JULHO, AGOSTO, SETEMBRO, OUTUBRO, NOVEMBRO, DEZEMBRO);
		}
		
	}
	
	public static Semestre getSemestre() {
		
		int valorMes = LocalDate.now().getMonthValue();
		
		List<Integer> mes = Arrays.asList(1,2,3,4,5,6);
		
		if (mes.contains(valorMes)) {
			return Semestre.PRIMEIRO;
		} else 
			return Semestre.SEGUNDO;
		
	}
	
	public enum Semestre {
		
		PRIMEIRO, SEGUNDO;
		
	}

}

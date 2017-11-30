package br.com.contability.relatorios;

import org.springframework.web.servlet.ModelAndView;

public interface Relatorio {
	
	public void putParam(String nameParam, Object valueParam);
	
	public ModelAndView criaRelatorio(String nomeRelatorio);

}

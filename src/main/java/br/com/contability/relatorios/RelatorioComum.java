package br.com.contability.relatorios;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RelatorioComum implements Relatorio {

	@Autowired
	private DataSource dataSource;
	
	private Map<String, Object> parametros = new HashMap<>();
	
	@Override
	public void putParam(String nameParam, Object valueParam){
		
		parametros.put(nameParam, valueParam); 
		
	}
	
	@Override
	public ModelAndView criaRelatorio(String nomeRelatorio){
		parametros.put("datasource", dataSource);
		parametros.put("format", "pdf");
		ModelAndView mv = new ModelAndView(nomeRelatorio, parametros);
		return mv;
	}
	
}

package br.com.contability.business.services;

import java.io.File;
import java.util.List;

import br.com.contability.comum.BeanIdentificavel;
import br.com.contability.comum.IServices;

public interface LeitorPlanilhaStrategy<T extends BeanIdentificavel, E extends IServices<T>> {
	
	public List<T> configuraFileToObject(File file);

}

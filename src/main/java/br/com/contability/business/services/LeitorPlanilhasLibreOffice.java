package br.com.contability.business.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import br.com.contability.comum.BeanIdentificavel;
import br.com.contability.comum.IServices;

public class LeitorPlanilhasLibreOffice<T extends BeanIdentificavel, E extends IServices<T>>
		implements LeitorPlanilhaStrategy<T, E> {

	private E servicesAbstract;
	
	/**
	 * @param services
	 */
	public LeitorPlanilhasLibreOffice(E services) {
		
		this.servicesAbstract = services;
		
	}

	@Override
	public List<T> configuraFileToObject(File file) {

		List<T> objetos = new ArrayList<>();

		Sheet sheet;

		try {

			sheet = SpreadSheet.createFromFile(file).getSheet(0);

			servicesAbstract.preencheObjetoPlanilhaLibreOffice(objetos, sheet);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return objetos;

	}
}
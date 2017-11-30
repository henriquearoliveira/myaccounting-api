package br.com.contability.business.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.contability.comum.BeanIdentificavel;
import br.com.contability.comum.IServices;
import br.com.contability.exceptions.ObjetoInexistenteException;

public class LeitorPlanilhasExcel<T extends BeanIdentificavel, E extends IServices<T>>
		implements LeitorPlanilhaStrategy<T, E> {

	private E servicesAbstract;

	private PlanilhaFilesServices planilhaServices = new PlanilhaFilesServices();
	
	public LeitorPlanilhasExcel(E services) {
		this.servicesAbstract = services;
	}

	@Override
	public List<T> configuraFileToObject(File file) {

		List<T> objetos = new ArrayList<>();

		FileInputStream fileInputStream = null;
		
		fileInputStream = planilhaServices.criaInputStream(file, fileInputStream);

		Sheet firstSheet = criaFileSheetConformeExtensao(file, fileInputStream);

		Iterator<Row> iteratorRow = firstSheet.iterator();

		servicesAbstract.preencheObjetoPlanilhaExcel(objetos, iteratorRow);

		return objetos;

	}

	private Sheet criaFileSheetConformeExtensao(File file, FileInputStream fileInputStream) {

		String nameFile = file.getName();

		String extensao = nameFile.substring(nameFile.lastIndexOf(".") + 1, nameFile.length());

		Workbook workbook = null;

		if (extensao.equals("xls")) {

			workbook = criaWorkBookXLS(fileInputStream, workbook);

		} else if (extensao.equals("xlsx")) {

			workbook = criaWorkBookXLSX(fileInputStream, workbook);

		} else {
			throw new ObjetoInexistenteException();
		}

		Sheet firstSheet = workbook.getSheetAt(0);

		return firstSheet;

	}

	private Workbook criaWorkBookXLSX(FileInputStream fileInputStream, Workbook workbook) {
		try {
			workbook = new XSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private Workbook criaWorkBookXLS(FileInputStream fileInputStream, Workbook workbook) {
		try {
			workbook = new HSSFWorkbook(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
	}

}

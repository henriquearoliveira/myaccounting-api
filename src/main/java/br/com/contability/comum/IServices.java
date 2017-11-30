package br.com.contability.comum;

import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.jopendocument.dom.spreadsheet.Sheet;

public interface IServices<T extends BeanIdentificavel> {
	
	default T atualiza(T objeto, Boolean webOrApi){return null;};

	default T get(Long id, Boolean webOrApi){return null;}
	
	default T insere(T objeto, Boolean webOrApi){return null;}
	
	default void remove(Long id, Boolean webOrApi){}
	
	default void verificaExistencia(T objeto, Boolean webOrApi){
		get(objeto.getId(), webOrApi);
	}
	
	default void preencheObjetoPlanilhaExcel(List<T> objetos, Iterator<Row> iteratorRow){}
	
	default void preencheObjetoPlanilhaLibreOffice(List<T> objetos, Sheet sheet){}
	
}

package br.com.contability.comum;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface IResources<T extends BeanIdentificavel> {
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	default ResponseEntity<Void> atualiza(Long id, T objeto){
		return ResponseEntity.notFound().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	default ResponseEntity<T> get(Long id){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	default ResponseEntity<Void> insere(T objeto){
		return ResponseEntity.notFound().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	default ResponseEntity<Void> remove(Long id){
		return ResponseEntity.notFound().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	default ResponseEntity<List<T>> seleciona(){
		return null;
	}

}

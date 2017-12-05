package br.com.contability.business.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.contability.business.Usuario;
import br.com.contability.business.services.UsuarioServices;

@Controller
@RequestMapping("/usuario")
public class UsuarioResources {
	
	@Autowired
	private UsuarioServices usuarioServices;
	
	@GetMapping("{id}")
	public ResponseEntity<Usuario> get(@PathVariable Long id) {
		
		System.out.println("Cheguei, hehe : " + id);
		
		return ResponseEntity.ok(usuarioServices.get(id, null));
	}

}

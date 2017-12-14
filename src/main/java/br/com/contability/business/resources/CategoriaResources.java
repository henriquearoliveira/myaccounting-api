package br.com.contability.business.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.contability.business.Categoria;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.CategoriaServices;
import br.com.contability.comum.AuthenticationAbstract;

@Controller
@RequestMapping("/categoria")
public class CategoriaResources {
	
	@Autowired
	private CategoriaServices categoriaServices;
	
	@Autowired
	private AuthenticationAbstract auth;
	
	@GetMapping()
	public ResponseEntity<Void> novo() {
		auth.getAutenticacao();
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{idCategoria}")
	public ResponseEntity<Void> get(Model model, Categoria categoria, @PathVariable Object idCategoria) { // tem que haver no método para ele mapear depois
		
		Usuario usuario = auth.getAutenticacao();
		
		categoriaServices.getCategoria(idCategoria, usuario);
		
		return ResponseEntity.noContent().build();

		
	}
	
	@GetMapping("/json/{idCategoria}")
	public ResponseEntity<String> get(@PathVariable Object idCategoria) {
		
		Usuario usuario = auth.getAutenticacao();
		
		return ResponseEntity.ok(categoriaServices.get(idCategoria, usuario));
		
	}

	@PostMapping
	public ResponseEntity<Void> salvar(@Valid @RequestBody Categoria categoria) {
		
		/*Usuario usuario = auth.getAutenticacao();
		
		categoriaServices.gravarCategoria(categoria, usuario);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
				.buildAndExpand(categoria.getId()).toUri();

		return ResponseEntity.created(uri).build();*/
		
		System.out.println(categoria.getDescricao());
		System.out.println(categoria.getTipoDeCategoria());
		System.out.println(categoria.getObservacao());
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping(value = "/lista")
	public ResponseEntity<List<Categoria>> lista(Model model) {
		
		Usuario usuario = auth.getAutenticacao();
		
		return ResponseEntity.ok(categoriaServices.seleciona(usuario));
		
	}
	
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		auth.getAutenticacao();
		
		categoriaServices.removeCategoria(id);
		
		return ResponseEntity.ok().build();
		
	}

}

package br.com.contability.business.resources;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contability.business.Cadastro;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.CadastrarServices;
import br.com.contability.business.services.UsuarioServices;

@Controller
@RequestMapping("/cadastrar")
public class CadastrarResources {
	
	@Autowired
	private CadastrarServices cadastrarServices;
	
	@Autowired
	private UsuarioServices usuarioServices;

	@GetMapping
	public ResponseEntity<Void> novo(@RequestBody Cadastro cadastro){
		
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping()
	public ResponseEntity<Void> cadastrar(@Valid @RequestBody Cadastro cadastro, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
		if(result.hasErrors())
			novo(cadastro);
		
		Usuario usuario = usuarioServices.insere(cadastro);
		
		cadastrarServices.confirmaUsuario(cadastro, usuario, request);
		
		attributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso, por favor visualize o email enviado"
				+ "para confirmar o cadastro");
		

		return ResponseEntity.noContent().build();
	}
	
}

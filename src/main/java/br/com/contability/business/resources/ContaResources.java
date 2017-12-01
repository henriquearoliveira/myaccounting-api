package br.com.contability.business.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.contability.business.Conta;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.ContaServices;
import br.com.contability.comum.AuthenticationAbstract;

@Controller
@RequestMapping("/conta")
public class ContaResources {
	
	@Autowired
	private AuthenticationAbstract auth;
	
	@Autowired
	private ContaServices contaServices;

	@GetMapping()
	public ResponseEntity<Void> novo(){
		
		auth.getAutenticacao();
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{idConta}")
	public ResponseEntity<Void> get(@PathVariable Object idConta){
		
		Usuario usuario = auth.getAutenticacao();
		
		contaServices.getConta(usuario, idConta);
		return ResponseEntity.noContent().build();
		
	}
	
	@PostMapping()
	public ResponseEntity<Void> novo(@Valid Conta conta){ // TESTAR O VALID
		
		Usuario usuario = auth.getAutenticacao();
		
		contaServices.gravaConta(usuario, conta);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("lista")
	public ResponseEntity<Void> lista(Model model){
		
		Usuario usuario = auth.getAutenticacao();
		
		List<Conta> contas = contaServices.seleciona(usuario);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/remover/{id}")
	public ResponseEntity<Void> remove(@PathVariable Long id){
		
		Usuario usuario = auth.getAutenticacao();
		
		contaServices.removeConta(usuario, id);
		
		return ResponseEntity.ok().build();
	}
	
}

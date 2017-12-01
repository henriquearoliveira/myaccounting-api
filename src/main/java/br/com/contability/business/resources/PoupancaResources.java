package br.com.contability.business.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.contability.business.Usuario;
import br.com.contability.business.services.ContaServices;
import br.com.contability.comum.AuthenticationAbstract;

@Controller
@RequestMapping("/poupanca")
public class PoupancaResources {
	
	@Autowired
	private AuthenticationAbstract auth;
	
	@Autowired
	private ContaServices contaServices;
	
	@GetMapping("/lista")
	public ResponseEntity<Void> lista(@RequestParam("idConta") Long idConta) {
		
		Usuario usuario = auth.getAutenticacao();
		
//		ModelAndView mv = new ModelAndView("poupanca/Listagem");
		
		contaServices.getConta(usuario, idConta);
		
		return ResponseEntity.noContent().build();
		
	}

}

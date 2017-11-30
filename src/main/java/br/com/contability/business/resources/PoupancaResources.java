package br.com.contability.business.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.contability.business.Usuario;
import br.com.contability.business.services.ContaServices;
import br.com.contability.comum.AuthenticationAbstract;
import br.com.contability.comum.ModelConstruct;

@Controller
@RequestMapping("/poupanca")
public class PoupancaResources {
	
	@Autowired
	private AuthenticationAbstract auth;
	
	@Autowired
	private ContaServices contaServices;
	
	@GetMapping("/lista")
	public ModelAndView lista(Model model, @RequestParam("idConta") Long idConta) {
		ModelConstruct.setAttributes(model, "activeLiPoupanca", "activeListagem");
		
		Usuario usuario = auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("poupanca/Listagem");
		
		return contaServices.getConta(usuario, mv, idConta);
		
	}

}

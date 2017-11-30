package br.com.contability.business.resources;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contability.business.Cadastro;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.CadastrarServices;
import br.com.contability.business.services.UsuarioServices;
import br.com.contability.comum.StringPaginasAndRedirect;

@Controller
@RequestMapping("/cadastrar")
public class CadastrarResources {
	
	@Autowired
	private CadastrarServices cadastrarServices;
	
	@Autowired
	private UsuarioServices usuarioServices;

	@GetMapping
	public ModelAndView novo(Cadastro cadastro){
		
		ModelAndView mv = new ModelAndView("Cadastrar");
		
		return mv;
	}
	
	@PostMapping()
	public ModelAndView cadastrar(@Valid Cadastro cadastro, BindingResult result, RedirectAttributes attributes, HttpServletRequest request){
		if(result.hasErrors())
			novo(cadastro);
		
		Usuario usuario = usuarioServices.insere(cadastro);
		
		cadastrarServices.confirmaUsuario(cadastro, usuario, request);
		
		attributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso, por favor visualize o email enviado"
				+ "para confirmar o cadastro");

		return new ModelAndView(StringPaginasAndRedirect.LOGIN);
	}
	
}

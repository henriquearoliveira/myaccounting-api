package br.com.contability.business.resources;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contability.business.EsqueceuSenha;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.CodigoUsuarioServices;
import br.com.contability.business.services.UsuarioServices;
import br.com.contability.utilitario.CaixaDeFerramentas;
import br.com.contability.utilitario.email.EmailParameters;
import br.com.contability.utilitario.email.IEnviaEmail;

@Controller
@RequestMapping("/esqueceusenha")
public class EsqueceuSenhaResources {
	
	@Autowired
	private CodigoUsuarioServices services;
	
	@Autowired
	private IEnviaEmail enviaEmail;
	
	@Autowired
	private UsuarioServices usuarioServices;
	
	@GetMapping
	public ModelAndView requisitaEmail(EsqueceuSenha esqueceuSenha){
		return new ModelAndView("EsqueceuSenha");
	}
	
	@PostMapping
	public ModelAndView confirmaEmail(@Valid EsqueceuSenha esqueceuSenha, RedirectAttributes redirect, HttpServletRequest request){
		
		Usuario usuario = usuarioServices.getPelo(esqueceuSenha.getEmail(), "/esqueceusenha");
		
		String codigo = CaixaDeFerramentas.geraCodigo(20);
		services.insereCodigoUsuario(usuario, codigo);
		
		EmailParameters email = new EmailParameters.EmailParametersBuilder()
				.setAssunto("Ativação de cadastro no My Accounting")
				.setPara(esqueceuSenha.getEmail())
				.setCodigo(codigo)
				.setUrl(CaixaDeFerramentas.configureURLdesired(request, "?email="+esqueceuSenha.getEmail()+"&codigo="))
				.setTemplateEmail("mail/RecuperaSenha")
				.build();

		enviaEmail.envia(email);
		
		return new ModelAndView("AlteraSenha");
	}

}

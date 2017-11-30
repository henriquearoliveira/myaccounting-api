package br.com.contability.business.resources;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contability.business.Usuario;
import br.com.contability.business.services.PerfilServices;
import br.com.contability.comum.AuthenticationAbstract;
import br.com.contability.comum.StringPaginasAndRedirect;

@Controller
@RequestMapping("/perfil")
public class PerfilResources {

	@Autowired
	private AuthenticationAbstract auth;

	@Autowired
	private PerfilServices perfilServices;

	@GetMapping
	public ModelAndView perfil(Usuario usuario) { // Model model : MODEL QUANDO QUER UMA MENSAGEM E
		// OBJETO QUANDO PRECISA DE ALGO
		// MODELATTRIBUTE PODE SER UTILIZADO NA VIEW OU SE PASSANDO O ID ELE BUSCA NO
		// BANCO O OBJETO

		ModelAndView mv = new ModelAndView("perfil/Perfil");
		mv.addObject("usuario", auth.getAutenticacao());

		return mv;

	}

	@PostMapping
	public ModelAndView perfilEdit(Usuario usuario, @RequestParam(value = "file", required = false) MultipartFile file,
			BindingResult result, RedirectAttributes attributes, HttpSession session) {

		if (result.hasErrors())
			return perfil(usuario);

		perfilServices.atualizaPerfil(usuario, file, session);

		attributes.addFlashAttribute("mensagem", "Perfil editado com sucesso.");

		return new ModelAndView(StringPaginasAndRedirect.PERFIL);
	}

}

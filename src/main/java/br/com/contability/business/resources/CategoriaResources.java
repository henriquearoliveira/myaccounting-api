package br.com.contability.business.resources;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.contability.business.Categoria;
import br.com.contability.business.TipoDeCategoria;
import br.com.contability.business.Usuario;
import br.com.contability.business.services.CategoriaServices;
import br.com.contability.comum.AuthenticationAbstract;
import br.com.contability.comum.ModelConstruct;
import br.com.contability.comum.StringPaginasAndRedirect;

@Controller
@RequestMapping("/categoria")
public class CategoriaResources {
	
	@Autowired
	private CategoriaServices categoriaServices;
	
	@Autowired
	private AuthenticationAbstract auth;
	
	@GetMapping()
	public ModelAndView novo(Model model, Categoria categoria) { // tem que haver no método para ele mapear depois
		ModelConstruct.setAttributes(model,"activeLi", "activeNovo");
	
		auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("categoria/Categoria");
		mv.addObject("tipoDeCategorias", Arrays.asList(TipoDeCategoria.RECEITA, TipoDeCategoria.DESPESA));
		
		return mv;
	}
	
	@GetMapping("/{idCategoria}")
	public ModelAndView get(Model model, Categoria categoria, @PathVariable Object idCategoria) { // tem que haver no método para ele mapear depois
		ModelConstruct.setAttributes(model,"activeLi", "activeNovo");
		
		Usuario usuario = auth.getAutenticacao();
		
		ModelAndView mv = new ModelAndView("categoria/Categoria");
		return categoriaServices.getCategoria(idCategoria, mv, usuario);
		
	}
	
	@GetMapping("/json/{idCategoria}")
	public ResponseEntity<String> get(@PathVariable Object idCategoria) {
		
		Usuario usuario = auth.getAutenticacao();
		
		return ResponseEntity.ok(categoriaServices.get(idCategoria, usuario));
		
	}

	@PostMapping
	public ModelAndView salvar(@Valid Categoria categoria, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		Usuario usuario = auth.getAutenticacao();
		
		if(bindingResult.hasErrors())
			return novo(model, categoria);
		
		categoriaServices.gravarCategoria(categoria, usuario);
		
		redirectAttributes.addFlashAttribute("mensagem", "Categoria salvo com sucesso.");
		
		return new ModelAndView(StringPaginasAndRedirect.CATEGORIA);
	}

	@GetMapping(value = "/lista")
	public ModelAndView lista(Model model) {
		ModelConstruct.setAttributes(model,"activeLi", "activeListagem");
		Usuario usuario = auth.getAutenticacao();
		
		List<Categoria> categorias = categoriaServices.seleciona(usuario);
		
		ModelAndView mv = new ModelAndView("categoria/Listagem");

		mv.addObject("categorias", categorias);
		
		return mv;
	}
	
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		auth.getAutenticacao();
		
		categoriaServices.removeCategoria(id);
		
		return ResponseEntity.ok().build();
		
	}

}

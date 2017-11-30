package br.com.contability.comum;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teste")
public class Teste {

	// aprendizagem lambda
	// teste 1
	/*http://www.devmedia.com.br/como-usar-funcoes-lambda-em-java/32826
	@RequestMapping("/novo")
	public ModelAndView novo(Model model){
		
		ModelAndView mv = new ModelAndView("/categoria/Categoria");
		model.addAttribute("activeLi","active treeview");
		model.addAttribute("activeLiSubMenu","active");
		
		List<Usuario> usuarios = new ArrayList<>();
		
		usuarios.forEach(action);
		
		return mv;
	}
	
	@RequestMapping("/teste")
	public ModelAndView novoo(Model model){
		

		testandoComLambda(10, i -> true);
		
		return null;
	}
	
	public void testandoComLambda(Integer inteiro, Predicate<Integer> predicate){
		
		if(predicate.test(inteiro))
			System.out.println("Deu bom + " + inteiro);
			
		
	}
	
	// teste 2
	@RequestMapping("/teste")
	public ModelAndView novoo(Model model){
		
		Usuario usuario = new Usuario();
		
		usuario.setEmail("email teste lambda");
		

		Usuario teste = testandoComLambda(usuario, i -> i);
		
		System.out.println(teste.getEmail());
		
		return null;
	}
	
	public <T, U> Usuario testandoComLambda(T usuario, Function<? super T, ? extends U> teste){
		
		return (Usuario) teste.apply(usuario);
		
	}
	
	// teste 3
	@RequestMapping("/teste")
	public ModelAndView novoo(Model model){
		
		Usuario usuario = new Usuario();
		usuario.setEmail("email teste lambda");
		
		teste<Usuario> teste = new teste<Usuario>();
		teste.of(usuario);
		
		Usuario testee = teste.testandoComLambda(i -> i);
		System.out.println(testee.getEmail());
		
		return null;
	}
	*/

}

//classe teste
// Do teste com lambda
/*

class teste<T> {
	
	T objeto;
	
	public void of(T objeto){
		this.objeto = objeto;
	}
	
	public <U> U testandoComLambda(Function<? super T, ? extends U> teste){
		
		return teste.apply(objeto);
		
	}
	
}*/

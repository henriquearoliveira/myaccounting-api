package br.com.contability.comum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ValorIncorreto {
	
	@Autowired
	private RedirectAttributesAbstract redirectAttributesAbstract;

	public ValorIncorreto() {
	}
	
	/**
	 * @param redirectAttributesAbstract
	 * @param textoMensagem
	 * @return redirect
	 */
	public ModelAndView defineRedirecionamentoComMensagem(String textoMensagemErro, String textoMensagemSucesso, String redirecionamento){
		if (textoMensagemErro == null){
			redirectAttributesAbstract.getRedirectAttributes().addFlashAttribute("mensagem", textoMensagemSucesso);
		} else{
			redirectAttributesAbstract.getRedirectAttributes().addFlashAttribute("erro", textoMensagemErro);
		}
		return new ModelAndView(redirecionamento);
		
	}

}

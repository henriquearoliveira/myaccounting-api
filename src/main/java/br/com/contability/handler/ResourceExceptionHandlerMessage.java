package br.com.contability.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import br.com.contability.exceptions.ObjetoExistenteExceptionMessage;
import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;
import br.com.contability.exceptions.ObjetoNaoAutorizadoMessage;

@ControllerAdvice
public class ResourceExceptionHandlerMessage {
	
	@ExceptionHandler(ObjetoExistenteExceptionMessage.class)
	public RedirectView objetoExistenteExceptionMessage(ObjetoExistenteExceptionMessage e, HttpServletRequest request) {

		RedirectView rw = new RedirectView(e.getRedirect());
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    if (outputFlashMap != null){
	        outputFlashMap.put("erro", e.getMessage());
	    }
	    
	    return rw;

	}
	
	@ExceptionHandler(ObjetoInexistenteExceptionMessage.class)
	public RedirectView objetoInexistenteExceptionMessage(ObjetoInexistenteExceptionMessage e, HttpServletRequest request) {
		
		RedirectView rw = new RedirectView(e.getRedirect());
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    if (outputFlashMap != null){
	        outputFlashMap.put("erro", e.getMessage());
	    }
	    
	    return rw;

	}
	
	@ExceptionHandler(ObjetoNaoAutorizadoMessage.class)
	public RedirectView objetoNaoAutorizadoExceptionMessage(ObjetoNaoAutorizadoMessage e, HttpServletRequest request) {
		
		RedirectView rw = new RedirectView(e.getRedirect());
	    FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
	    if (outputFlashMap != null){
	        outputFlashMap.put("erro", e.getMessage());
	    }
	    
	    return rw;

	}
	
}

package br.com.contability.comum;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public class RedirectAttributesAbstract {
	
	private static RedirectAttributesAbstract instance = null;
	
	public RedirectAttributesAbstract getInstance() {
		
		if (instance == null)
			instance = new RedirectAttributesAbstract();
		return instance;
	}
	
	private RedirectAttributesAbstract() {
	}
	
	private RedirectAttributes redirectAttributes;
	
	public RedirectAttributes getRedirectAttributes() {
		return redirectAttributes;
	}
	
	public void setRedirectAttributes(RedirectAttributes redirectAttributes) {
		this.redirectAttributes = redirectAttributes;
	}

}

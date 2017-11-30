package br.com.contability.comum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.contability.business.Usuario;
import br.com.contability.business.services.UsuarioServices;

@Component
public class AuthenticationAbstract {
	
	@Autowired
	private UsuarioServices usuarioServices;
	
	/**
	 * @return nameAuthentication
	 */
	public Usuario getAutenticacao(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
		
		if(email.equals("anonymousUser"))
			return null;
		
		Usuario usuario = usuarioServices.getPelo(email, "/login");
		
		return usuario;
	}

}

package br.com.contability.business.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.contability.business.UploadImage;
import br.com.contability.business.Usuario;
import br.com.contability.comum.SessaoServices;

@Component
public class PerfilServices {
	
	@Autowired
	private SessaoServices sessaoServices;

	@Autowired
	private UploadImageServices services;

	@Autowired
	private UsuarioServices usuarioServices;

	public void atualizaPerfil(Usuario usuario, MultipartFile file, HttpSession session) {
		
		usuarioServices.preenche(usuario);

		if (file.getSize() != 0) {
			updateWithImage(usuario, file);
		} else
			justupdate(usuario);
		
		sessaoServices.configuraSessionComUsuario(usuario, session);
		
		

	}

	private void justupdate(Usuario usuario) {

		usuarioServices.atualiza(usuario, false);

	}

	private void updateWithImage(Usuario usuario, MultipartFile file) {

		UploadImage uploadImage = services.uploadImageCloudinary(file);

		UploadImage uploadRetorno = services.insere(uploadImage, false);

		usuario.setUploadImage(uploadRetorno);

		usuarioServices.atualiza(usuario, false);
	}

}

package br.com.contability.business.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.contability.business.AlteraSenha;
import br.com.contability.business.services.CodigoUsuarioServices;

@Controller
@RequestMapping("/alterasenha")
public class AlteraSenhaResources {
	
	@Autowired
	private CodigoUsuarioServices services;

	@GetMapping
	public ResponseEntity<Void> solicitaAlterarSenha(@RequestParam("email") String email, @RequestParam("codigo") String codigo) {

		// CONFIGURO DESSA FORMA DEVIDO SER NECESSÁRIO SETAR O EMAIL E CÓDIGO
		// CASO CONTRARIO BASTARIA NOS PARAMETROS DO MÉTODO COLOCAR UM
		// AlteraSenha alteraSenhas
		AlteraSenha alteraSenha = new AlteraSenha();
		alteraSenha.setEmail(email);
		alteraSenha.setCodigo(codigo);

		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<Void> alteraSenha(@Valid @RequestBody AlteraSenha alteraSenha){
		
		services.alteraSenha(alteraSenha);
		
		return ResponseEntity.noContent().build();
	}

}

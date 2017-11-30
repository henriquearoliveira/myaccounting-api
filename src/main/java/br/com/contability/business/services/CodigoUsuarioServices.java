package br.com.contability.business.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.contability.business.AlteraSenha;
import br.com.contability.business.CodigoUsuario;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.CodigoUsuarioRepository;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.comum.ShaPasswordEncoder;

@Service
public class CodigoUsuarioServices extends ServicesAbstract<CodigoUsuario, CodigoUsuarioRepository> {
	
	@Autowired
	private UsuarioServices usuarioServices;

	/**
	 * @param usuario
	 * @param codigo
	 */
	public void insereCodigoUsuario(Usuario usuario, String codigo) {

		verificaCodigosAtivos(usuario);
		verificaCodigoJaExistente(usuario, codigo);

		CodigoUsuario codigoUsuario = geraCodigoUsuario(codigo, usuario);

		super.insere(codigoUsuario, null);

	}
	
	/**
	 * @param codigo
	 * @param usuario
	 * @return FACILITA A REUTILIZAÇÃO
	 */
	public CodigoUsuario geraCodigoUsuario(String codigo, Usuario usuario){
		
		CodigoUsuario codigoUsuario = new CodigoUsuario();
		codigoUsuario.setCodigo(codigo);
		codigoUsuario.setUsuario(usuario);
		
		return codigoUsuario;
	}

	/**
	 * @param usuario
	 * @param codigo
	 * 
	 * SE O CÓDIGO JÁ EXISTIR NA TABELA, É GERADO OUTRO ATÉ A COERENCIA CORRETA.
	 */
	private void verificaCodigoJaExistente(Usuario usuario, String codigo) {
		
		Optional<CodigoUsuario> codigoUsuario = super.getJpa().verificaJaExistente(codigo);
		
		codigoUsuario.ifPresent(i -> {
			insereCodigoUsuario(usuario, codigo);
		});
		
	}

	/**
	 * @param usuario
	 * @param codigo
	 * 
	 *            VERIFICA SE O USUARIO NÃO ESTÁ COM O CÓDIGO ATIVO
	 */
	private void verificaCodigosAtivos(Usuario usuario) {

		Optional<CodigoUsuario> codigoUsuario = super.getJpa().verificaCodigoAtivo(usuario);
		
		codigoUsuario.ifPresent(i -> {
			remove(i.getId(), null);
		});

	}

	/**
	 * @param codigo
	 */
	public void confirmaCodigoUsuario(String codigo) {
		
		Usuario usuario = usuarioServices.getUsuarioPelo(codigo);
		usuario.setAtivo(true);
		
		usuarioServices.atualiza(usuario, null);
		
		super.atualiza(setFalseCodigoUsuario(usuario, codigo), null);
		
	}
	
	/**
	 * @param usuario
	 * @param codigo
	 * @return
	 */
	private CodigoUsuario setFalseCodigoUsuario(Usuario usuario, String codigo){
		
		CodigoUsuario codigoUsuario = super.getJpa().get(usuario, codigo).get();
		codigoUsuario.setAtivo(false);
		
		return codigoUsuario;
		
	}

	/**
	 * @param alteraSenha
	 */
	public void alteraSenha(AlteraSenha alteraSenha) {
		Usuario usuario = usuarioServices.getPelo(alteraSenha.getEmail(), "/alterasenha?email=" + alteraSenha.getEmail()
													+ "&codigo=" + alteraSenha.getCodigo());
		
		usuario.setSenha(ShaPasswordEncoder.getSha512Securit(alteraSenha.getPassword()));

		usuarioServices.atualiza(usuario, null);
		setFalseCodigoUsuario(usuario, alteraSenha.getCodigo());
	}

}

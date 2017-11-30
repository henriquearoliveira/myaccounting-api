package br.com.contability.business.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.contability.business.CodigoUsuario;
import br.com.contability.business.Usuario;

public interface CodigoUsuarioRepository extends JpaRepository<CodigoUsuario, Long> {

	@Query("SELECT c FROM CodigoUsuario c WHERE c.usuario = ?1 AND c.ativo = TRUE")
	public Optional<CodigoUsuario> verificaCodigoAtivo(Usuario usuario);

	@Query("SELECT c FROM CodigoUsuario c WHERE c.codigo = ?1")
	public Optional<CodigoUsuario> verificaJaExistente(String codigo);
	
	@Query("SELECT c FROM CodigoUsuario c WHERE c.usuario = ?1 AND c.codigo = ?2 AND c.ativo = TRUE")
	public Optional<CodigoUsuario> get(Usuario usuario, String codigo);

}

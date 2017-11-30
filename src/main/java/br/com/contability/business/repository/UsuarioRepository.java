package br.com.contability.business.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.contability.business.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("SELECT u FROM Usuario u WHERE u.email = ?1")
	public Optional<Usuario> getPelo(String email);

	@Query("SELECT u FROM Usuario u"
			+ " JOIN u.codigoUsuarios cu"
			+ " WHERE cu.codigo = ?1 AND cu.ativo = TRUE")
	public Optional<Usuario> getUsuarioPelo(String codigo);

}

package br.com.contability.business.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.contability.business.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	@Query("SELECT c FROM Categoria c WHERE c.usuario.id = ?1")
	public List<Categoria> seleciona(Long idUsuario);

	@Query("SELECT c FROM Categoria c WHERE c.id = ?1 AND c.usuario.id = ?2")
	public Optional<Categoria> getCategorias(Long id, Long idUsuario);

	@Query("SELECT c FROM Categoria c JOIN c.lancamentos ln WHERE ln.id = ?1")
	public Categoria getPeloLancamento(Long idLancamento);

	@Query("SELECT c FROM Categoria c WHERE c.descricao LIKE ?1 AND c.usuario.id = ?2")
	public Optional<Categoria> getCategoriaProximoMes(String string, Long idUsuario);

	@Query("SELECT c FROM Categoria c WHERE c.descricao LIKE ?1 AND c.usuario.id = ?2")
	public Optional<Categoria> getCategoriaDeposito(String string, Long id);

}

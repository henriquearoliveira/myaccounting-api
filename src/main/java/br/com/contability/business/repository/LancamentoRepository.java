package br.com.contability.business.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.contability.business.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	/*@Query("SELECT l FROM Lancamento l WHERE l.usuario.id = ?1"
			+ " AND MONTH(l.dataHoraLancamento) <= ?3 AND YEAR(l.dataHoraLancamento) = ?4"
			+ " AND CASE WHEN (?2 = 0) THEN l.id = ?2 ELSE l.conta.id = ?2 END")
	public List<Lancamento> selecionaLancamentos(Long usuario, Long idConta, int month, int year);*/
	
	@Query(value = "SELECT lancamento.* FROM lancamento"
			+ " INNER JOIN usuario ON lancamento.usuario_id = usuario.id" 
			+ " LEFT JOIN conta ON lancamento.conta_id = conta.id"
			+ " WHERE lancamento.usuario_id = ?1 AND MONTH(lancamento.data_hora_lancamento) <= ?3" 
			+ " AND YEAR(lancamento.data_hora_lancamento) <= ?4"
			+ " AND IF(?2 = 0 OR ?2 IS NULL, lancamento.id IS NOT NULL, conta.id = ?2);", nativeQuery = true)
	public List<Lancamento> selecionaLancamentos(Long usuario, Long idConta, int month, int year);
	
	@Query("SELECT l FROM Lancamento l WHERE l.usuario.id = ?1 AND l.id = ?2")
	public Optional<Lancamento> getLancamento(Long idUsuario, Long idLancamento);

	@Query("SELECT DISTINCT(DATE(l.dataHoraCadastro)) FROM Lancamento l WHERE l.usuario.id = ?1 AND YEAR(l.dataHoraCadastro) = YEAR(NOW())")
	public List<Date> selecionaMesesDosLancamentosAnoAtual(Long idUsuario);

	@Query("SELECT l FROM Lancamento l WHERE l.usuario.id = ?1 AND YEAR(l.dataHoraCadastro) = YEAR(NOW())")
	public List<Lancamento> selecionaLancamentosAnoAtual(Long idUsuario);

	@Query(value = "SELECT lancamento.* FROM lancamento"
			+ " INNER JOIN usuario ON lancamento.usuario_id = usuario.id" 
			+ " INNER JOIN conta ON lancamento.conta_id = conta.id"
			+ " WHERE lancamento.usuario_id = ?1 AND lancamento.data_hora_vencimento <= ?2"
			+ " AND lancamento.pago IS FALSE"
			+ " AND IF(?3 = 0, lancamento.id IS NOT NULL, conta.id = ?3);", nativeQuery = true)
	public List<Lancamento> selecionaVencidos(Long idUsuario, LocalDate dataVencimento, Long idConta);
	
	@Query("SELECT l FROM Lancamento l WHERE l.usuario.id = ?1 AND l.dataHoraVencimento < ?2 AND l.pago IS FALSE")
	public List<Lancamento> selecionaVencidosTodasContas(Long idUsuario, LocalDate dataVencimento);

	@Query("SELECT l FROM Lancamento l WHERE l.usuario.id = ?1 AND l.dataHoraVencimento = ?2 AND l.pago IS FALSE")
	public List<Lancamento> selecionaVencidosDa(Long id, LocalDate dataVencimento);
	
	/*@Query("SELECT l FROM Lancamento l WHERE l.usuario.id = ?1 AND MONTH(l.dataHoraLancamento) = ?2 AND YEAR(l.dataHoraLancamento) =?3 ")
	public List<Lancamento> selecionaLancamentosPuroStream(Long id, int month, int year);*/

	/*@Query(value = "SELECT (SELECT COALESCE(SUM(lanPositivo.valor_lancamento),0) FROM lancamento AS lanPositivo"
			+ " INNER JOIN categoria ON lanPositivo.categoria_id = categoria.id"
			+ " WHERE lanPositivo.usuario_id = ?1 AND categoria.tipo_de_categoria = 'RECEITA'"
			+ " AND MONTH(lanPositivo.data_hora_lancamento) = ?2 AND YEAR(lanPositivo.data_hora_lancamento) = ?3"
			+ " ) - (SELECT COALESCE(SUM(lanNegativo.valor_lancamento),0) FROM lancamento AS lanNegativo"
			+ " INNER JOIN categoria ON lanNegativo.categoria_id = categoria.id"
			+ " WHERE lanNegativo.usuario_id = ?1 AND categoria.tipo_de_categoria = 'DESPESA'"
			+ " AND MONTH(lanNegativo.data_hora_lancamento) = ?2 AND YEAR(lanNegativo.data_hora_lancamento) = ?3"
			+ " AND lanNegativo.pago = true"
			+ " ) AS resultado"
			+ " FROM lancamento WHERE usuario_id = ?1 AND MONTH(data_hora_lancamento) = ?2 AND YEAR(data_hora_lancamento) = ?3"
			+ " GROUP BY resultado", nativeQuery = true)
	public BigDecimal getSaldo(Long idUsuario, int month, int year);
	
	@Query(value = "SELECT (SELECT COALESCE(SUM(lanPositivo.valor_lancamento),0) FROM lancamento AS lanPositivo"
			+ " INNER JOIN categoria ON lanPositivo.categoria_id = categoria.id"
			+ " WHERE lanPositivo.usuario_id = ?1 AND categoria.tipo_de_categoria = 'RECEITA'"
			+ " AND MONTH(lanPositivo.data_hora_lancamento) = ?2 AND YEAR(lanPositivo.data_hora_lancamento) = ?3"
			+ " ) - (SELECT COALESCE(SUM(lanNegativo.valor_lancamento),0) FROM lancamento AS lanNegativo"
			+ " INNER JOIN categoria ON lanNegativo.categoria_id = categoria.id"
			+ " WHERE lanNegativo.usuario_id = ?1 AND categoria.tipo_de_categoria = 'DESPESA'"
			+ " AND MONTH(lanNegativo.data_hora_lancamento) = ?2 AND YEAR(lanNegativo.data_hora_lancamento) = ?3"
			+ " ) AS resultado"
			+ " FROM lancamento WHERE usuario_id = ?1 AND MONTH(data_hora_lancamento) = ?2 AND YEAR(data_hora_lancamento) = ?3"
			+ " GROUP BY resultado", nativeQuery = true)
	public BigDecimal getSaldoProvavel(Long id, int monthValue, int year);*/

}

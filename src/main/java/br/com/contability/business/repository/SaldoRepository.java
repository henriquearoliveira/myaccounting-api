package br.com.contability.business.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.contability.business.Saldo;

public interface SaldoRepository extends JpaRepository<Saldo, Long> {

	@Query("SELECT s.saldoAtual FROM Saldo s WHERE s.usuario.id = ?1 AND MONTH(s.dataHoraLancamento) = ?2 AND YEAR(dataHoraLancamento) = ?3")
	public BigDecimal getSaldoDo(Long idUsuario, int mes, int ano);
	
	@Query("SELECT s.saldoProvavel FROM Saldo s WHERE s.usuario.id = ?1 AND MONTH(s.dataHoraLancamento) = ?2 AND YEAR(dataHoraLancamento) = ?3")
	public BigDecimal getSaldoProvavelDo(Long id, int monthValue, int year);
	
	@Query("SELECT s FROM Saldo s WHERE s.usuario.id = ?1 AND MONTH(s.dataHoraLancamento) = ?2 AND YEAR(dataHoraLancamento) = ?3")
	public Saldo getSaldo(Long idUsuario, int mes, int ano);

	

}

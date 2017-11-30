package br.com.contability.business.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.contability.business.Lancamento;
import br.com.contability.business.Mes;
import br.com.contability.business.TipoDeCategoria;
import br.com.contability.business.Usuario;

@Component
public class IndexServices {
	
	@Autowired
	private ContaServices contaServices;

	@Autowired
	private LancamentoServices lancamentoServices;

	// VARIÁVEL RESPONSÁVEL PELA ECONOMIA DE PROCESSAMENTO DO BANCO
	private List<Lancamento> lancamentos = null;
	
	List<Integer> mesesLocalDateTime = null;
	
	public List<String> getBalanceteMeses(Usuario usuario) {

		lancamentos = lancamentoServices.selecionaLancamentosAnoAtual(usuario);

		// SELECIONO APENAS OS MESES DO ANO QUE HÁ LANÇAMENTO SEM REPETIÇÕES
		mesesLocalDateTime = lancamentos.stream().map(l -> l.getDataHoraCadastro().getMonth().getValue())
				.distinct().collect(Collectors.toList());

		// BUSCO O MES REFERENTE
		List<String> meses = new ArrayList<>();

		mesesLocalDateTime.forEach(m -> {
			meses.add(Mes.getBy(m));
		});

		return meses;
	}

	public List<Double> getBalanceteReceitas() {
		
		Map<Integer, Double> receitas = new HashMap<>();
		
		mesesLocalDateTime.forEach(m -> {
			
			Stream<Lancamento> lan = lancamentos.stream().filter(l ->  l.getDataHoraLancamento().getMonth().getValue() == m
					&& l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.RECEITA);
			
			Double valorTotal = lan.mapToDouble(l -> l.getValorLancamento().doubleValue()).sum();
			
			receitas.put(m, valorTotal);
		});
		
		return new ArrayList<>(receitas.values());
	}
	
	public List<Double> getBalanceteDespesas() {
		
		Map<Integer, Double> despesas = new HashMap<>();
		
		mesesLocalDateTime.forEach(m -> {
			
			Stream<Lancamento> lan = lancamentos.stream().filter(l ->  l.getDataHoraLancamento().getMonth().getValue() == m
					&& l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.DESPESA);
			
			Double valorTotal = lan.mapToDouble(l -> l.getValorLancamento().doubleValue()).sum();
			
			despesas.put(m, valorTotal);
		});
		
		return new ArrayList<>(despesas.values());
	}

	public Double getReceitasMes() {
		
		return lancamentos.stream().filter(l ->  l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.RECEITA
				&& l.getDataHoraLancamento().getMonth() == LocalDate.now().getMonth())
				.mapToDouble(l -> l.getValorLancamento().doubleValue()).sum();

	}
	
	public Double getDespesasMes() {
		
		return lancamentos.stream().filter(l ->  l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.DESPESA
				&& l.getDataHoraLancamento().getMonth() == LocalDate.now().getMonth())
				.mapToDouble(l -> l.getValorLancamento().doubleValue()).sum();

	}

	public Object getSaldoMes() {
		
		return lancamentos.stream().filter(l -> l.getDataHoraLancamento().getMonth() == LocalDate.now().getMonth())
				.mapToDouble(l -> l.getCategoria().getTipoDeCategoria() == TipoDeCategoria.RECEITA ?
				l.getValorLancamento().doubleValue() : -l.getValorLancamento().doubleValue()).sum();
		
	}

	public Integer getContas(Usuario usuario) {
		
		return contaServices.selecionaNumeroContasDo(usuario);
	}

}

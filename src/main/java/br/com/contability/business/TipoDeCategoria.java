package br.com.contability.business;

import com.fasterxml.jackson.annotation.JsonCreator;

import br.com.contability.exceptions.ObjetoInexistenteException;

public enum TipoDeCategoria {

	DESPESA("Despesa"/*, new Usuario()*/),
	RECEITA("Receita"/*, new Categoria()*/),
	DEPOSITO("Deposito"),
	SAQUE("Saque");

	private String descricao;

//	private BeanIdentificavel beanIdentificavel;

	private TipoDeCategoria(String descricao/*, BeanIdentificavel beanIdentificavel*/) {

		this.descricao = descricao;
//		this.beanIdentificavel = beanIdentificavel;

	}

	public String getDescricao() {
		return descricao;
	}
	
	@JsonCreator
	public static TipoDeCategoria create(String value) {
		
		if (value == null)
			throw new ObjetoInexistenteException("Valor do Enum não Encontrado");

		for (TipoDeCategoria tipoDeCategoria : values()) {
			
			if (value.equals(tipoDeCategoria.getDescricao())){
				return tipoDeCategoria;
			} else if (value.equals(tipoDeCategoria.name())) {
				return tipoDeCategoria;
			}
		}
		
		throw new ObjetoInexistenteException("Tipo de categoria não encontrado");
		
	}

	/*public BeanIdentificavel getBeanIdentificavel() {
		return beanIdentificavel;
	}

	*//**
	 * @param tipoDeCategoriaParameter
	 * 
	 * IDENTIFICA PELO TIPO DE BEAN IDENTIFICAVEL
	 * 
	 * @return
	 *//*
	public BeanIdentificavel organiza(TipoDeCategoria tipoDeCategoriaParameter) {

		TipoDeCategoria[] tipos = TipoDeCategoria.values();

		for (TipoDeCategoria tipoDeCategoria : tipos) {

			if (tipoDeCategoria.beanIdentificavel.equals(tipoDeCategoriaParameter.getBeanIdentificavel())) {

				return tipoDeCategoria.getBeanIdentificavel();

			}

		}

		return null;
	}*/

}

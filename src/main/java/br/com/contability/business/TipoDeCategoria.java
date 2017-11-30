package br.com.contability.business;

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

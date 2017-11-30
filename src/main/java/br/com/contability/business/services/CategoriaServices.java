package br.com.contability.business.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import br.com.contability.business.Categoria;
import br.com.contability.business.TipoDeCategoria;
import br.com.contability.business.Usuario;
import br.com.contability.business.repository.CategoriaRepository;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.exceptions.ObjetoInexistenteException;
import br.com.contability.exceptions.ObjetoInexistenteExceptionMessage;

@Service
public class CategoriaServices extends ServicesAbstract<Categoria, CategoriaRepository> {
	
	@Autowired
	private TrataParametrosServices parametroServices;
	
	/**
	 * @param id
	 * @param mv
	 * @param usuario
	 * @return
	 */
	public ModelAndView getCategoria(Object id, ModelAndView mv, Usuario usuario) {
		
		Long idCategoria = parametroServices.trataParametroLongMessage(id, "/categoria");
		
		Optional<Categoria> categoria = super.getJpa().getCategorias(idCategoria, usuario.getId());

		categoria.orElseThrow(() -> new ObjetoInexistenteExceptionMessage("/categoria", "Categoria não encontrada"));
		
		mv.addObject("categoria", categoria.get());
		mv.addObject("tipoDeCategorias", categoria.get().getTipoDeCategoria());

		return mv;
	}

	/**
	 * @param categoria
	 * @param usuario
	 */
	public void gravarCategoria(Categoria categoria, Usuario usuario) {

		categoria.setUsuario(usuario);
		
		if (categoria.getId() == null) {
			super.insere(categoria, null);
		} else {
			super.atualiza(categoria, null);
		}
	}

	/**
	 * @param usuario
	 * @return
	 */
	public List<Categoria> seleciona(Usuario usuario) {

		List<Categoria> categorias = super.getJpa().seleciona(usuario.getId());

		return categorias;
	}

	/*public ModelAndView re/move(Long id, Model model) {

		ModelAndView mv = new ModelAndView("/categoria/Categoria");

		if (id == null || id == 0) 
			return valorIncorreto.defineRedirecionamentoComMensagem("O obrigado não pode ser vazio", null, StringCaminhoPaginas.CATEGORIA);

		Categoria categoria = super.get(id);

		if (categoria == null)
			return valorIncorreto.defineRedirecionamentoComMensagem("O objeto não existe", null, StringCaminhoPaginas.CATEGORIA);

		super.remove(categoria.getId());

		return mv;

	}*/

	/**
	 * @param id
	 */
	public void removeCategoria(Long id) {

		if (id == null) {
			throw new ObjetoInexistenteException();
		}

		super.remove(id, null);

	}

	/**
	 * @param idLancamento
	 * @return Categoria
	 */
	public Categoria getPeloLancamento(Long idLancamento) {
		return super.getJpa().getPeloLancamento(idLancamento);
	}
	
	/* ----------------- API ------------------- */
	
	public String get(Object id, Usuario usuario) {
		
		Long idCategoria = parametroServices.trataParametroLongAPI(id);
		
		Optional<Categoria> categoria = super.getJpa().getCategorias(idCategoria, usuario.getId());
		
		categoria.orElseThrow(() -> new ObjetoInexistenteException("Categoria inexistente"));
		
		return categoria.get().getTipoDeCategoria().name();
	}

	/**
	 * @param usuario
	 * @param localDateTime
	 * @return RETORNA A CATEGORIA JÁ CADASTRADA OU INSERE A CATEGORIA NECESSÁRIA
	 */
	/*public Categoria categoriaProximoMes(Usuario usuario, LocalDateTime localDateTime) {
		
		Optional<Categoria> categoria = super.getJpa()
				.getCategoriaProximoMes("Lançamento referente mes passado", usuario.getId());
		
		return categoria.orElseGet(() -> novaCategoriaProximoMes(usuario, localDateTime));
	}

	private Categoria novaCategoriaProximoMes(Usuario usuario, LocalDateTime localDateTime) {
		
		Categoria categoria = new Categoria();
		categoria.setDataHoraCadastro(localDateTime);
		categoria.setDescricao("Lançamento referente mês passado");
		categoria.setObservacao("Crédito referente mês passado");
		categoria.setTipoDeCategoria(TipoDeCategoria.RECEITA);
		categoria.setUsuario(usuario);
		
		return this.insere(categoria, null);
	}*/

	public Categoria categoriaDeposito(Usuario usuario, LocalDateTime localDateTime) {
		
		Optional<Categoria> categoria = super.getJpa()
				.getCategoriaDeposito("Deposito", usuario.getId());
		
		return categoria.orElseGet(() -> novaCategoriaDeposito(usuario, localDateTime));
		
		
		
	}

	private Categoria novaCategoriaDeposito(Usuario usuario, LocalDateTime localDateTime) {
		
		Categoria categoria = new Categoria();
		categoria.setDataHoraCadastro(localDateTime);
		categoria.setDescricao("Depósito");
		categoria.setObservacao("Depósito em conta");
		categoria.setTipoDeCategoria(TipoDeCategoria.DEPOSITO);
		categoria.setUsuario(usuario);
		
		return this.insere(categoria, null);
	}

}

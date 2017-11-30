package br.com.contability.comum;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.contability.exceptions.ObjetoComDependenciaException;
import br.com.contability.exceptions.ObjetoExistenteException;
import br.com.contability.exceptions.ObjetoExistenteExceptionModel;
import br.com.contability.exceptions.ObjetoInexistenteException;

public class ServicesAbstract<T extends BeanIdentificavel, E extends JpaRepository<T, Long>> implements IServices<T> {

	@Autowired
	private E jpa;
	
	@Override
	public T atualiza(T objeto, Boolean isApiProgram) {
		Optional<Boolean> optional = Optional.ofNullable(isApiProgram);
		
		verificaExistencia(objeto, isApiProgram);
		LocalDateTime dataCadastro = get(objeto.getId(), isApiProgram).getDataHoraCadastro();
		objeto.setDataHoraAtualizacao(LocalDateTime.now());
		objeto.setDataHoraCadastro(dataCadastro);

		try {
			return jpa.save(objeto);
		} catch (DataIntegrityViolationException e) {
			optional.orElseThrow(() -> new ObjetoExistenteExceptionModel("O objeto não é possível ser atualizado."));
			throw new ObjetoExistenteException("O objeto não é possível ser atualizado.");
		}
	}

	@Override
	public T get(Long id, Boolean isApiProgram) {
		Optional<Boolean> optional = Optional.ofNullable(isApiProgram);

		T objeto = jpa.findOne(id);

		if (objeto == null){
			optional.orElseThrow(() -> new ObjetoExistenteExceptionModel("O objeto requisitado não existe."));
			
			throw new ObjetoInexistenteException("O objeto requisitado não existe.");
			
		}

		return objeto;
	}

	@Override
	public T insere(T objeto, Boolean isApiProgram) {
		Optional<Boolean> optional = Optional.ofNullable(isApiProgram);

		objeto.setId(null);
		objeto.setDataHoraCadastro(LocalDateTime.now());

		try {

			return jpa.save(objeto);

		} catch (DataIntegrityViolationException e) {
			optional.orElseThrow(() -> new ObjetoExistenteExceptionModel("Objeto já existente."));
			throw new ObjetoExistenteException("Objeto já existente.");
		}

	}

	@Override
	public void remove(Long id, Boolean isApiProgram) {
		T objeto = get(id, isApiProgram);
		Optional<Boolean> optional = Optional.ofNullable(isApiProgram);

		try {
			jpa.delete(objeto);
		} catch (DataIntegrityViolationException e) {
			optional.orElseThrow(() -> new ObjetoExistenteExceptionModel("Objeto com dependencias."));
			throw new ObjetoComDependenciaException("Objeto com dependencias.");
		}

	}

	public E getJpa() {
		return jpa;
	}

}
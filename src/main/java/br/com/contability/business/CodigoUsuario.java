package br.com.contability.business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.contability.comum.BeanIdentificavel;

@Entity
@Table(indexes = { @Index(name = "index_codigo_usuario", columnList = "id", unique = false),
		@Index(name = "unique_codigo_usuario", columnList = "codigo,usuario_id", unique = true) })
public class CodigoUsuario extends BeanIdentificavel {
	
	@JsonIgnore
	@ManyToOne(optional = false)
	private Usuario usuario;
	
	@Column(length = 20, nullable = false)
	@NotEmpty(message = "O codigo não pode ser vazio")
	@NotNull(message = "O codigo não pode ser null")
	private String codigo;
	
	@Column(nullable = false)
	@NotNull(message = "O status não pode ser null")
	private boolean ativo = true;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public boolean isAtivo() {
		return ativo;
	}
	
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}

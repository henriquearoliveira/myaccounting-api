package br.com.contability.business;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.contability.comum.BeanIdentificavel;

@Entity
@Table(name = "conta", indexes = { @Index(name = "index_conta", columnList = "id", unique = false)})
public class Conta extends BeanIdentificavel{
	
	@JsonIgnore
	@ManyToOne(optional = false)
	private Usuario usuario;
	
	@Column(length = 100, nullable = false)
	@NotEmpty(message = "A descricao não pode ser vazio")
	@NotNull(message = "A descricao não pode ser null")
	private String descricao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "conta", targetEntity = Lancamento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Lancamento> lancamentos;
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}
	
	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

}

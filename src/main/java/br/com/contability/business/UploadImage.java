package br.com.contability.business;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.contability.comum.BeanIdentificavel;

@Entity
@Table(indexes = { @Index(name = "index_upload_image", columnList = "id", unique = false),
		@Index(name = "unique_public_id", columnList = "publicid", unique = true) })
public class UploadImage extends BeanIdentificavel {

	@Column(nullable = false, unique = true)
	@NotEmpty(message = "O id publico não pode ser vazio")
	@NotNull(message = "O id publico não pode ser null")
	private String publicID;

	@Column(nullable = false, unique = true)
	@NotEmpty(message = "A assinatura não pode ser vazio")
	@NotNull(message = "A assinatura não pode ser null")
	private String signature;

	@Column(nullable = false, unique = true)
	@NotNull(message = "A largura não pode ser null")
	private Long width;

	@Column(nullable = false, unique = true)
	@NotNull(message = "A altura não pode ser null")
	private Long height;

	@Column
	private String format;

	@Column
	private String resourceType;

	@Column
	private LocalDateTime createAt;

	@Column
	private Long bytes;

	@Column(nullable = false, unique = true)
	@NotEmpty(message = "A url não pode ser vazio")
	@NotNull(message = "A url não pode ser null")
	private String url;

	@Column(nullable = false, unique = true)
	@NotEmpty(message = "A url segura não pode ser vazio")
	@NotNull(message = "A url segura não pode ser null")
	private String secureUrl;

	@JsonIgnore
	@OneToMany(mappedBy = "uploadImage", targetEntity = Usuario.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Usuario> usuarios;

	public String getPublicID() {
		return publicID;
	}

	public void setPublicID(String publicID) {
		this.publicID = publicID;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public Long getBytes() {
		return bytes;
	}

	public void setBytes(Long bytes) {
		this.bytes = bytes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSecureUrl() {
		return secureUrl;
	}

	public void setSecureUrl(String secureUrl) {
		this.secureUrl = secureUrl;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}

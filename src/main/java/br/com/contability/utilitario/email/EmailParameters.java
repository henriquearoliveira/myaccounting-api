package br.com.contability.utilitario.email;

public class EmailParameters {
	
	private String assunto, para, codigo, url, templateEmail;
	
	private EmailParameters(EmailParametersBuilder builder) {
		
		this.assunto = builder.assunto;
		this.para = builder.para;
		this.codigo = builder.codigo;
		this.url = builder.url;
		this.templateEmail = builder.templateEmail;
		
	}
	
	public String getAssunto() {
		return assunto;
	}

	public String getPara() {
		return para;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getUrl() {
		return url;
	}

	public String getTemplateEmail() {
		return templateEmail;
	}

	public static class EmailParametersBuilder {
		
		private String assunto, para, codigo, url, templateEmail;

		public EmailParametersBuilder setAssunto(String assunto) {
			this.assunto = assunto;
			return this;
		}

		public EmailParametersBuilder setPara(String para) {
			this.para = para;
			return this;
		}

		public EmailParametersBuilder setCodigo(String codigo) {
			this.codigo = codigo;
			return this;
		}

		public EmailParametersBuilder setUrl(String url) {
			this.url = url;
			return this;
		}

		public EmailParametersBuilder setTemplateEmail(String templateEmail) {
			this.templateEmail = templateEmail;
			return this;
		}
		
		public EmailParameters build(){
			return new EmailParameters(this);
		}
		
	}
	
}

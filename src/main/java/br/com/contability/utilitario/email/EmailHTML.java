package br.com.contability.utilitario.email;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import br.com.contability.exceptions.ObjetoNaoAutorizadoException;

@Component
public class EmailHTML implements IEnviaEmail {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private SpringTemplateEngine thymeleaf;
	
	@Override
	public void envia(EmailParameters emailParameters) {
		
		Locale locale = new Locale("pt", "BR");
		
		Context ctx = new Context(locale);
		ctx.setVariable("codigo", emailParameters.getCodigo());
		ctx.setVariable("urlPagina", emailParameters.getUrl()); //"http://localhost:9090"
		MimeMessage mensagem = mailSender.createMimeMessage();
		
		try {
			
			MimeMessageHelper mensagemAjuda = new MimeMessageHelper(mensagem, false, "UTF-8");
			mensagemAjuda.setSubject(emailParameters.getAssunto());
			mensagemAjuda.setFrom("henrique_skatebord@hotmail.com");
			mensagemAjuda.setTo(emailParameters.getPara());
			String corpoHtml = thymeleaf.process(emailParameters.getTemplateEmail(), ctx);
			mensagemAjuda.setText(corpoHtml, true);
			
			mailSender.send(mensagem);
			
		} catch (MessagingException e) {
			throw new ObjetoNaoAutorizadoException(e.getMessage());
		}
		
	}

}

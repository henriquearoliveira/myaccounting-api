package br.com.contability.business.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.contability.exceptions.ObjetoNaoAutorizadoMessage;

@Component
public class ConfiguraArquivosServices {
	
	/**
	 * @param file
	 * @param imagem
	 * @return
	 */
	public File configuraArquivo(MultipartFile file) {

		File imagem = null;

		try {
			imagem = Files.createTempFile("temp", file.getOriginalFilename()).toFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			file.transferTo(imagem);
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
			throw new ObjetoNaoAutorizadoMessage();
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new ObjetoNaoAutorizadoMessage();
		}
		return imagem;
	}

}

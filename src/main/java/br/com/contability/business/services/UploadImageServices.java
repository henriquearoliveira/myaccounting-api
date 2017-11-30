package br.com.contability.business.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import br.com.contability.business.UploadImage;
import br.com.contability.business.repository.UploadImageRepository;
import br.com.contability.comum.ServicesAbstract;
import br.com.contability.exceptions.ObjetoNaoAutorizadoMessage;
import br.com.contability.utilitario.CaixaDeFerramentas;

@Service
public class UploadImageServices extends ServicesAbstract<UploadImage, UploadImageRepository> {

	private final String cloudName = "dznde0ht5";
	private final String apiKey = "538862271882227";
	private final String apiSecret = "UKxwkpSI-SQp2gzZ65AMzCj8Vkc";
	
	@Autowired
	private ConfiguraArquivosServices arquivosServices;

	public UploadImage uploadImageCloudinary(MultipartFile file) {

		// OBTEM A AUTENTICAÇÃO NECESSÁRIA PARA O UPLOAD
		Map<String, String> configuracao = getAuthentication();

		Cloudinary cloudinary = new Cloudinary(configuracao);

		File imagem = arquivosServices.configuraArquivo(file);

		Map<String, Object> resultado = uploadImagem(cloudinary, imagem);

		return converterToUploadImage(resultado);

		/*
		 * KEYSET É O CONJUNTO DE KEY, JÁ O ENTRYSET É O CONJUNTO DE KEY X VALOR for
		 * (String strings : resultado.keySet()) { } resultado.keySet().forEach(action);
		 */

	}

	private UploadImage converterToUploadImage(Map<String, Object> resultado) {

		UploadImage uploadImage = new UploadImage();
		uploadImage.setPublicID((String) resultado.get("public_id"));
		uploadImage.setSignature((String) resultado.get("signature"));
		uploadImage.setWidth((long) resultado.get("width"));
		uploadImage.setHeight((long) resultado.get("height"));
		uploadImage.setFormat((String) resultado.get("format"));
		uploadImage.setResourceType((String) resultado.get("resource_type"));
		uploadImage.setCreateAt(CaixaDeFerramentas.stringToLocalDateTime((String) resultado.get("created_at")));
		uploadImage.setBytes((long) (resultado.get("bytes")));
		uploadImage.setUrl((String) resultado.get("url"));
		uploadImage.setSecureUrl((String) resultado.get("secure_url"));

		return uploadImage;

	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> uploadImagem(Cloudinary cloudinary, File imagem) {

		Map<String, Object> resultado = new HashMap<>();

		try {

			Map<String, Object> mapVazioCloudinary = new HashMap<>();
			resultado = cloudinary.uploader().upload(imagem, mapVazioCloudinary);

		} catch (IOException e) {

			e.printStackTrace();
			throw new ObjetoNaoAutorizadoMessage();

		}

		return resultado;
	}

	

	/**
	 * @return
	 */
	private Map<String, String> getAuthentication() {

		Map<String, String> configuracao = new HashMap<>();
		configuracao.put("cloud_name", cloudName);
		configuracao.put("api_key", apiKey);
		configuracao.put("api_secret", apiSecret);

		return configuracao;
	}

}

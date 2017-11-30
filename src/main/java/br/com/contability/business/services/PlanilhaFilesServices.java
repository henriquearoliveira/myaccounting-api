package br.com.contability.business.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class PlanilhaFilesServices {
	
	public FileInputStream criaInputStream(File file, FileInputStream fileInputStream) {
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return fileInputStream;
	}

}

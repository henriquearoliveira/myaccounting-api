package br.com.contability.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.contability.business.UploadImage;

public interface UploadImageRepository extends JpaRepository<UploadImage, Long> {

}

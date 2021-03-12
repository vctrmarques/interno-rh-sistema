package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CertidaoExServidorCargo;

@Repository
public interface CertidaoExServidorCargoRepository extends JpaRepository<CertidaoExServidorCargo, Long> {

	void deleteByCertidaoExServidorId(Long id);

}

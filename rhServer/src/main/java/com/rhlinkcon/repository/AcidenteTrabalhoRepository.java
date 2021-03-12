package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.AcidenteTrabalho;

@Repository
public interface AcidenteTrabalhoRepository extends JpaRepository<AcidenteTrabalho, Long> {

	Boolean existsByAviso(String aviso);

	Boolean existsByAvisoAndIdNot(String aviso, Long id);

}

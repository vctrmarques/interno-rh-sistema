package com.rhlinkcon.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.LicencaMedica;

@Repository
public interface LicencaMedicaRepository extends JpaRepository<LicencaMedica, Long> {
	
	Page<LicencaMedica> findAllByFuncionarioNomeIgnoreCaseContaining(String nomeFuncionario, Pageable pageable);
}

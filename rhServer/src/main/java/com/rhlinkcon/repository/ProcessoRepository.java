package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Processo;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
	Processo findAllByAnexosId(Long id);
	
	Page<Processo> findAllByFuncionarioNomeIgnoreCaseContaining(String nomeFuncionario, Pageable pageable);
}

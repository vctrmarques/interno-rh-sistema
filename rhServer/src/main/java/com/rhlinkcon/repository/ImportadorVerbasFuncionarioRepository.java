package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ImportadorVerbasFuncionario;

@Repository
public interface ImportadorVerbasFuncionarioRepository extends JpaRepository<ImportadorVerbasFuncionario, Long> {

	Page<ImportadorVerbasFuncionario> findByExcluido(boolean excluido, Pageable pageable);
}

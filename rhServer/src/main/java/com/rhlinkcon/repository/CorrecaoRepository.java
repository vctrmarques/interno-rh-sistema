package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Correcao;
import com.rhlinkcon.model.Verba;

@Repository
public interface CorrecaoRepository extends JpaRepository<Correcao, Long> {

	Page<Correcao> findByEmpresaNomeFilialIgnoreCaseContaining(String nomeFilial, Pageable pageable);
	
	Boolean existsByVerba(Verba verba);

}

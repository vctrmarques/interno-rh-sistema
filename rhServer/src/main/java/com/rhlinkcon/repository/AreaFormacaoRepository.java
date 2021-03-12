package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.AreaFormacao;

@Repository
public interface AreaFormacaoRepository extends JpaRepository<AreaFormacao, Long> {

	Page<AreaFormacao> findByAreaFormacaoIgnoreCaseContaining(String areaFormacao, Pageable pageable);
	
	List<AreaFormacao> findByAreaFormacaoIgnoreCaseContaining(String areaFormacao);

	Boolean existsByAreaFormacao(String areaFormacao);

	Boolean existsByAreaFormacaoAndIdNot(String areaFormacao, Long id);

}

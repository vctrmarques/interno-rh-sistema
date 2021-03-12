package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CrmCrea;
import com.rhlinkcon.model.CrmCreaEnum;

@Repository
public interface CrmCreaRepository extends JpaRepository<CrmCrea, Long> {

	Page<CrmCrea> findByNomeConveniadoIgnoreCaseContaining(String nomeConveniado, Pageable pageable);

	Boolean existsByNomeConveniado(String nomeConveniado);

	Boolean existsByNomeConveniadoAndIdNot(String nomeConveniado, Long id);
	
	List<CrmCrea> findAllByTipo(CrmCreaEnum tipo);

}

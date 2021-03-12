package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Consignado;

@Repository
public interface ConsignadoRepository extends JpaRepository<Consignado, Long> {

	Page<Consignado> findByContrato(String contrato, Pageable pageable);

	Boolean existsByDescricao(String descricao);
	
	Boolean existsByDescricaoAndIdNot(String descricao, Long id);
	
	List<Consignado> findByDescricaoIgnoreCaseContaining(String search);

}

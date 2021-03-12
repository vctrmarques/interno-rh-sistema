package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ValeTransporte;

@Repository
public interface ValeTransporteRepository extends JpaRepository<ValeTransporte, Long> {
	
	Page<ValeTransporte> findByCodigoIgnoreCaseContaining(String codigo, Pageable pageable);

	Boolean existsByCodigo(String codigo);

	Boolean existsByCodigoAndIdNot(String codigo, Long id);

}

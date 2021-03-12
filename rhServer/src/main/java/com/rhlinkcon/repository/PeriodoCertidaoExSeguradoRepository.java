package com.rhlinkcon.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.PeriodoCertidaoExSegurado;
@Repository
public interface PeriodoCertidaoExSeguradoRepository extends JpaRepository<PeriodoCertidaoExSegurado,Long> {

	void deleteByCertidaoExSeguradoId(Long id);
} 
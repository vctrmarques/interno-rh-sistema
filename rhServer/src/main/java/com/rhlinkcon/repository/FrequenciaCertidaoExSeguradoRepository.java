package com.rhlinkcon.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.FrequenciaCertidaoExSegurado;
@Repository
public interface FrequenciaCertidaoExSeguradoRepository extends JpaRepository<FrequenciaCertidaoExSegurado,Long> {

	void deleteByCertidaoExSeguradoId(Long id);
} 
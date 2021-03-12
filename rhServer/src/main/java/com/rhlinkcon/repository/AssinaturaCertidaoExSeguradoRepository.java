package com.rhlinkcon.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.AssinaturaCertidaoExSegurado;
@Repository
public interface AssinaturaCertidaoExSeguradoRepository extends JpaRepository<AssinaturaCertidaoExSegurado,Long> {

	void deleteByCertidaoExSeguradoId(Long id);
} 
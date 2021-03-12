package com.rhlinkcon.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.TempoEspecialCertidaoExSegurado;
@Repository
public interface TempoEspecialCertidaoExSeguradoRepository extends JpaRepository<TempoEspecialCertidaoExSegurado,Long> {
} 
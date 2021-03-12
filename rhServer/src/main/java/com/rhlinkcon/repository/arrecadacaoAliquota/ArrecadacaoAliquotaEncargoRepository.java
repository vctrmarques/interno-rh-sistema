package com.rhlinkcon.repository.arrecadacaoAliquota;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ArrecadacaoAliquotaEncargo;
import com.rhlinkcon.model.ArrecadacaoAliquotaEncargoEnum;

@Repository
public interface ArrecadacaoAliquotaEncargoRepository extends JpaRepository<ArrecadacaoAliquotaEncargo, Long>{

	boolean existsByArrecadacaoAliquotaPeriodoIdAndAliquotaEncargo(Long id, ArrecadacaoAliquotaEncargoEnum valueEnum);

	ArrecadacaoAliquotaEncargo findFirstByArrecadacaoAliquotaPeriodoIdAndAliquotaEncargo(Long id, ArrecadacaoAliquotaEncargoEnum valueEnum);

	List<ArrecadacaoAliquotaEncargo> findAllByArrecadacaoAliquotaPeriodoIdAndAliquotaEncargoNotIn(Long periodoId, List<ArrecadacaoAliquotaEncargoEnum> listaEnum);

}

package com.rhlinkcon.repository.arrecadacaoAliquota;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ArrecadacaoAliquotaPeriodo;

@Repository
public interface ArrecadacaoAliquotaRepository extends JpaRepository<ArrecadacaoAliquotaPeriodo, Long>, ArrecadacaoAliquotaCustom {

	boolean existsByInicioVigenciaBetweenOrFimVigenciaBetween(LocalDate inicioVigencia, LocalDate fimVigencia, LocalDate inicioVigencia2, LocalDate fimVigencia2);

	boolean existsByInicioVigenciaBetweenAndIdIsNotOrFimVigenciaBetweenAndIdIsNot(LocalDate inicioVigencia, LocalDate fimVigencia, Long id, LocalDate inicioVigencia2, LocalDate fimVigencia2,
			Long id2);

}

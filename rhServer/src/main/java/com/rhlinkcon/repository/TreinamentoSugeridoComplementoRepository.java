package com.rhlinkcon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rhlinkcon.model.TreinamentoSugerido;
import com.rhlinkcon.model.TreinamentoSugeridoComplemento;

public interface TreinamentoSugeridoComplementoRepository extends JpaRepository<TreinamentoSugeridoComplemento, Long>{

	Optional<TreinamentoSugeridoComplemento> findFirstByTreinamentoSugerido(TreinamentoSugerido ts);

}

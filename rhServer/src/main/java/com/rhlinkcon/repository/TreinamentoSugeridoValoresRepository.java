package com.rhlinkcon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rhlinkcon.model.TreinamentoSugerido;
import com.rhlinkcon.model.TreinamentoSugeridoValores;

public interface TreinamentoSugeridoValoresRepository extends JpaRepository<TreinamentoSugeridoValores, Long>{

	Optional<TreinamentoSugeridoValores> findFirstByTreinamentoSugerido(TreinamentoSugerido ts);

}

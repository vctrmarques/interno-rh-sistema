package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CodigoPagamentoGps;

@Repository
public interface CodigoPagamentoGpsRepository extends JpaRepository<CodigoPagamentoGps, Long> {

	List<CodigoPagamentoGps> findByCodigoOrDescricaoAllIgnoreCaseContaining(Integer codigo, String descricao);

	Page<CodigoPagamentoGps> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

}

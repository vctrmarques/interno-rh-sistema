package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.MotivoDesligamento;

@Repository
public interface MotivoDesligamentoRepository extends JpaRepository<MotivoDesligamento, Long> {

	List<MotivoDesligamento> findByCodigoOrDescricaoAllIgnoreCaseContaining(Integer codigo, String descricao);

	Page<MotivoDesligamento> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

}

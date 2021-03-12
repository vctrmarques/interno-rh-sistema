package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CodigoRecolhimento;

@Repository
public interface CodigoRecolhimentoRepository extends JpaRepository<CodigoRecolhimento, Long> {

	List<CodigoRecolhimento> findByCodigoOrDescricaoAllIgnoreCaseContaining(Integer codigo, String descricao);

	Page<CodigoRecolhimento> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

	Boolean existsByCodigo(Integer codigo);
}

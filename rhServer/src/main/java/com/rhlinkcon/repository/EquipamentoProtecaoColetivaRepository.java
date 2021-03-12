package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.EquipamentoProtecaoColetiva;

@Repository
public interface EquipamentoProtecaoColetivaRepository extends JpaRepository<EquipamentoProtecaoColetiva, Long> {

	Page<EquipamentoProtecaoColetiva> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	Boolean existsByDescricao(String descricao);
	
	Boolean existsByCodigo(String codigo);
	
	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

}
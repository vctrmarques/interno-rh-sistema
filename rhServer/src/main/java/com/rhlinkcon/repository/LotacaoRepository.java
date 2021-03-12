package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.Lotacao;

@Repository
public interface LotacaoRepository extends JpaRepository<Lotacao, Long> {

	Page<Lotacao> findByDescricaoIgnoreCaseContainingAndExcluidaIsFalse(String descricao, Pageable pageable);

	Boolean existsByDescricao(String descricao);
	
	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

	List<Lotacao> findByDescricaoIgnoreCaseContaining(String search);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM lotacao_cargo WHERE lotacao_id = :lotacao_id", nativeQuery = true)
	void deleteLotacaoCargoByLotacaoId(@Param("lotacao_id")Long lotacaoId);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM lotacao_funcao WHERE lotacao_id = :lotacao_id", nativeQuery = true)
	void deleteLotacaoFuncaoByLotacaoId(@Param("lotacao_id")Long lotacaoId);

}

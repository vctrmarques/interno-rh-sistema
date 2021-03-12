package com.rhlinkcon.repository.legislacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.legislacao.DetalhamentoNorma;
import com.rhlinkcon.payload.legislacao.DetalhamentoNormaResponse;

@Repository
public interface DetalhamentoNormaRepository extends JpaRepository<DetalhamentoNorma, Long> {

	@Query("SELECT new com.rhlinkcon.payload.legislacao.DetalhamentoNormaResponse(d) FROM DetalhamentoNorma d WHERE descricao LIKE %:descricao%")
	List<DetalhamentoNormaResponse> findComboCompleteDtoByDescricao(@Param("descricao") String descricao);

	@Query("SELECT new com.rhlinkcon.payload.legislacao.DetalhamentoNormaResponse(d) FROM DetalhamentoNorma d ")
	List<DetalhamentoNormaResponse> findComboCompleteDto();
}

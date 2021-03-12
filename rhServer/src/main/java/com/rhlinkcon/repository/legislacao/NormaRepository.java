package com.rhlinkcon.repository.legislacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.legislacao.Norma;
import com.rhlinkcon.payload.legislacao.NormaResponse;

@Repository
public interface NormaRepository extends JpaRepository<Norma, Long> {

	@Query("SELECT new com.rhlinkcon.payload.legislacao.NormaResponse(n) FROM Norma n WHERE n.descricao LIKE %:descricao%")
	List<NormaResponse> getNormaResponseListByDescricao(@Param("descricao") String descricao);

	@Query("SELECT new com.rhlinkcon.payload.legislacao.NormaResponse(n) FROM Norma n ")
	List<NormaResponse> getNormaResponseList();

}

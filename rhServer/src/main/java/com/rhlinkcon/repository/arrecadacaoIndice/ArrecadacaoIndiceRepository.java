package com.rhlinkcon.repository.arrecadacaoIndice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ArrecadacaoIndice;
import com.rhlinkcon.service.ArrecadacaoIndiceDto;

@Repository
public interface ArrecadacaoIndiceRepository extends JpaRepository<ArrecadacaoIndice, Long>, ArrecadacaoIndiceCustom {

	Boolean existsByIndice(String nome);
	
	@Query("select new com.rhlinkcon.service.ArrecadacaoIndiceDto(ai.id, ai.indice) "
			+ " from ArrecadacaoIndice ai "
			+ " where ai.indice LIKE %:indice% ")
	List<ArrecadacaoIndiceDto> findByIndice(@Param("indice") String indice);
	
}

package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Cnae;

@Repository
public interface CnaeRepository extends JpaRepository<Cnae, Long> {

	Page<Cnae> findByNomeSecaoIgnoreCaseContaining(String nome, Pageable pageable);
	
	@Query("SELECT c FROM Cnae c WHERE c.nomeClasse LIKE %:nome% OR CAST( c.codigoClasse as string ) LIKE %:codigo%")
	List<Cnae> searchByNomeSecaoOrCodigoClasse(@Param("nome")String nome, @Param("codigo")String codigo);
	
	List<Cnae> findByNomeSecaoIgnoreCaseContaining(String nome);
	
	List<Cnae> findByOrderByNomeClasse();

}
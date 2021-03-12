package com.rhlinkcon.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

	//List<Curso> findByNomeCursoOrCodigoMecAllIgnoreCaseContaining(String codigoMec, String nomeCurso);
	
	Page<Curso> findByNomeCursoOrCodigoMec(String nomeCurso, String codigoMec, Pageable pageable);
	
	@Query(value = "SELECT c.id FROM curso c WHERE c.id_area_formacao IN :areasFormacao", nativeQuery = true)
	List<BigInteger> findByListAreaFormacao(@Param("areasFormacao")List<Long> areasFormacao);
	
	@Query(value = "SELECT c.id FROM curso c WHERE c.id_grau_academico IN :grausAcademicos", nativeQuery = true)
	List<BigInteger> findByListGrauAcademico(@Param("grausAcademicos")List<Long> grausAcademicos);
	
	@Query(value = "SELECT c.id FROM curso c WHERE c.id_area_formacao IN :areasFormacao AND c.id_grau_academico IN :grausAcademicos", nativeQuery = true)
	List<BigInteger> findByListAreaFormacaoAndGrauAcademico(@Param("areasFormacao")List<Long> areasFormacao, @Param("grausAcademicos")List<Long> grausAcademicos);
	
	Page<Curso> findByNomeCursoIgnoreCaseContaining(String nomeCurso, Pageable pageable);
	
	Page<Curso> findByCodigoMecIgnoreCaseContaining(String codigoMec, Pageable pageable);

	Boolean existsByNomeCurso(String nomeCurso);
	
	Boolean existsByNomeCursoAndIdNot(String nomeCurso, Long id);
	
	Boolean existsByCodigoMec (String codigoMec);
	
	Boolean existsByCodigoMecAndIdNot (String codigoMec, Long id);

}

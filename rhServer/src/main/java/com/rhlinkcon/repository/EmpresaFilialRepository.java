package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.EmpresaFilial;

@Repository
public interface EmpresaFilialRepository extends JpaRepository<EmpresaFilial, Long> {

	@Query("SELECT ef FROM EmpresaFilial ef WHERE ef.sigla LIKE %:siglaNome% OR ef.nomeFilial LIKE %:siglaNome%")
	Page<EmpresaFilial> findBySiglaOrNomeFilialIgnoreCaseContaining(@Param("siglaNome") String siglaNome,
			Pageable pageable);

	@Query("SELECT ef FROM EmpresaFilial ef WHERE ef.empresaMatriz = :empresaMatriz AND (ef.sigla LIKE %:siglaNome% OR ef.nomeFilial LIKE %:siglaNome%)")
	Page<EmpresaFilial> findByEmpresaMatriz(@Param("empresaMatriz") Boolean empresaMatriz,
			@Param("siglaNome") String siglaNome, Pageable pageable);

	Page<EmpresaFilial> findByEmpresaMatriz(Boolean empresaMatriz, Pageable pageable);

	Boolean existsByNomeFilial(String nomeFilial);

	Boolean existsByNomeFilialAndIdNot(String nomeFilial, Long id);

	Boolean existsByEmpresaMatriz(Boolean empresaMatriz);

	Boolean existsByEmpresaMatrizAndIdNot(Boolean empresaMatriz, Long id);

	EmpresaFilial findFirstByEmpresaMatriz(boolean b);

	List<EmpresaFilial> findByIdIn(List<Long> listEmpresaFilialId);

	List<EmpresaFilial> findByEmpresaMatriz(Boolean empresaMatriz);

	@Query("SELECT ef FROM EmpresaFilial ef WHERE ef.sigla LIKE %:search% OR ef.nomeFilial LIKE %:search%")
	List<EmpresaFilial> findBySiglaOrNomeFilial(@Param("search") String search);

}

package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.DiaUtil;
import com.rhlinkcon.repository.diaUtil.DiaUtilRepositoryQuery;

@Repository
public interface DiaUtilRepository extends JpaRepository<DiaUtil, String>, DiaUtilRepositoryQuery {

	@Transactional
	@Modifying
	@Query("delete from DiaUtil d where d.ano = ?1")
	void deleteByAnoIgnoreCaseContaining(@Param("ano") String ano);

	@Transactional
	@Modifying
	@Query("delete from DiaUtil d where d.ano = ?1 and d.mes = ?2")
	void deleteByAnoAndMes(@Param("ano") String ano, @Param("mes") String mes);

	List<DiaUtil> findByAnoAndMes(String ano, String mes);

}
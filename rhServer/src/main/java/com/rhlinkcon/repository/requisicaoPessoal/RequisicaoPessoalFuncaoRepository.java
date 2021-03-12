package com.rhlinkcon.repository.requisicaoPessoal;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.RequisicaoPessoalFuncao;

@Repository
public interface RequisicaoPessoalFuncaoRepository extends JpaRepository<RequisicaoPessoalFuncao, Long> {
	List<RequisicaoPessoalFuncao> findAllByRequisicaoPessoalId(Long id);

	@Modifying
	@Transactional
	@Query("delete from RequisicaoPessoalFuncao r where r.requisicaoPessoal.id = :requisicaoPessoalId")
	void deleteByRequisicaoPessoalId(@Param("requisicaoPessoalId") Long requisicaoPessoalId);
}

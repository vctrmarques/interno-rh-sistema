package com.rhlinkcon.repository.requisicaoPessoal;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.RequisicaoPessoal;
import com.rhlinkcon.model.SituacaoRequisicaoPessoalEnum;

@Repository
public interface RequisicaoPessoalRepository extends JpaRepository<RequisicaoPessoal, Long> {

	Page<RequisicaoPessoal> findAllBySituacao(SituacaoRequisicaoPessoalEnum situacao, Pageable pageable);

	Page<RequisicaoPessoal> findAllById(Long processo, Pageable pageable);

	Page<RequisicaoPessoal> findAllBySituacaoAndCreatedBy(SituacaoRequisicaoPessoalEnum situacao, Long createdBy,
			Pageable pageable);

	Page<RequisicaoPessoal> findAllByIdAndCreatedBy(Long processo, Long createdBy, Pageable pageable);

	Page<RequisicaoPessoal> findAllByCreatedBy(Long createdBy, Pageable pageable);

	Page<RequisicaoPessoal> findAllBySituacaoIn(List<SituacaoRequisicaoPessoalEnum> situacoes, Pageable pageable);

	@Modifying
	@Transactional
	@Query("update RequisicaoPessoal r set r.situacao = :situacao where r.id = :id ")
	void updateRequisicaoPessoalSituacao(@Param("situacao") SituacaoRequisicaoPessoalEnum situacao,
			@Param("id") Long id);

	@Query("select r.solicitante.emailCorporativo from RequisicaoPessoal r where r.id = :id")
	String findEmailSolicitanteById(@Param("id") Long id);

}

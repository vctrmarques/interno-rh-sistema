package com.rhlinkcon.repository.requisicaoPessoal;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.RequisicaoPessoalCandidato;

@Repository
public interface RequisicaoPessoalCandidatoRepository extends JpaRepository<RequisicaoPessoalCandidato, Long> {

	List<RequisicaoPessoalCandidato> findAllByRequisicaoPessoalId(Long id);
}

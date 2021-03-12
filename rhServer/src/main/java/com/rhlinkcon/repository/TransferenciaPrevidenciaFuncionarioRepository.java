package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rhlinkcon.model.TransferenciaPrevidenciaFuncionario;

public interface TransferenciaPrevidenciaFuncionarioRepository
		extends JpaRepository<TransferenciaPrevidenciaFuncionario, Long> {

	Page<TransferenciaPrevidenciaFuncionario> findByFuncionarioNomeIgnoreCaseContaining(String nome, Pageable pageable);

	List<TransferenciaPrevidenciaFuncionario> findByFuncionarioNomeIgnoreCaseContainingAndHistoricoSituacaoFuncionalSituacaoFuncionalDescricaoIn(
			String search, List<String> values);

	@Query("select t from TransferenciaPrevidenciaFuncionario t where "
			+ "t.historicoSituacaoFuncional.situacaoFuncional.descricao in (:values) "
			+ "and (:nome is null or t.funcionario.nome like %:nome%) "
			+ "and (:matricula is null or (cast(t.funcionario.matricula as string) like cast(:matricula as string))) "
			+ "and (:cpf is null or t.funcionario.cpf like %:cpf%) "
			+ "and (:pis is null or t.funcionario.pisPasep like %:pis%)")
	List<TransferenciaPrevidenciaFuncionario> findAposentados(@Param("nome") String searchFuncionario,
			@Param("matricula") Integer searchMatricula, @Param("cpf") String searchCPF, @Param("pis") String searchPis,
			@Param("values") List<String> values);

}

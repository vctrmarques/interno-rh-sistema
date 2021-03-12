package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ContribuicaoSindical;
@Repository
public interface ContribuicaoSindicalRepository extends JpaRepository<ContribuicaoSindical, Long>{

	@Query("SELECT c FROM ContribuicaoSindical c")
	Page<ContribuicaoSindical> findAll(Pageable pageable);
	
	@Query("SELECT c FROM ContribuicaoSindical c INNER JOIN Funcionario f ON c.funcionario = f WHERE f.nome = :nome")
	Page<ContribuicaoSindical> findByNomeIgnoreCaseContaining(@Param("nome") String nome, Pageable pageable);
	
	@Query("SELECT c FROM ContribuicaoSindical c INNER JOIN Funcionario f ON c.funcionario = f WHERE f.matricula = :matricula")
	Page<ContribuicaoSindical> findByMatriculaIgnoreCaseContaining(@Param("matricula") Integer matricula, Pageable pageable);
	
	@Query("SELECT c FROM ContribuicaoSindical c INNER JOIN Funcionario f ON c.funcionario = f WHERE f.matricula = :matricula AND f.nome = :nome")
	Page<ContribuicaoSindical> findByMatriculaAndNomeIgnoreCaseContaining(@Param("nome") String nome, @Param("matricula") Integer matricula, Pageable pageable);
	
	@Query("SELECT cs FROM ContribuicaoSindical cs INNER JOIN Funcionario f ON cs.funcionario = f WHERE f.id = :funcionarioId")
	List<ContribuicaoSindical> findContribuicaoSindicalByFuncionario(@Param("funcionarioId") Long funcionarioId);
}

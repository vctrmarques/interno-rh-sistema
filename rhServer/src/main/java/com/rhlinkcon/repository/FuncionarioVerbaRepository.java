package com.rhlinkcon.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.FuncionarioVerba;
import com.rhlinkcon.model.Verba;

@Repository
public interface FuncionarioVerbaRepository extends JpaRepository<FuncionarioVerba, Long> {

	List<FuncionarioVerba> findAllByVerbaIdIn(List<Long> ids);

	@Query("select f from FuncionarioVerba f where f.funcionario.id = :funcionarioId and f.ativo = true "
			+ "and (f.recorrencia = 'FIXA' or f.parcelasPagas < f.parcelas ) ")
	List<FuncionarioVerba> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);

	FuncionarioVerba findByFuncionarioAndVerba(Funcionario funcionario, Verba verba);

	@Modifying
	@Transactional
	@Query("delete from FuncionarioVerba f where f.funcionario.id = :funcionarioId and f.verba.id = :verbaId ")
	void deleteByFuncionarioIdAndVerbaId(@Param("funcionarioId") Long funcionarioId, @Param("verbaId") Long verbaId);

	Boolean existsByFuncionarioAndVerbaAndAtivo(Funcionario funcionario, Verba verba, Boolean ativo);

	Boolean existsByVerba(Verba verba);

	Optional<List<FuncionarioVerba>> findByFuncionarioAndIdNotIn(Funcionario funcionario, List<Long> ids);

}

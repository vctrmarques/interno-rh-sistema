package com.rhlinkcon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Dependente;
import com.rhlinkcon.model.Funcionario;

@Repository
public interface DependenteRepository extends JpaRepository<Dependente, Long> {

	@Query("SELECT d.funcionario FROM Dependente d")
	Optional<List<Funcionario>> findFuncionarios();

	Optional<List<Dependente>> findByFuncionarioId(Long funcionarioId);

	void deleteFindByFuncionarioId(Long funcionarioId);

}

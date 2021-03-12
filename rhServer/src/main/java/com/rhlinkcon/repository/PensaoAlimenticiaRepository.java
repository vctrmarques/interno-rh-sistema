package com.rhlinkcon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.PensaoAlimenticia;
import com.rhlinkcon.model.Verba;

@Repository
public interface PensaoAlimenticiaRepository extends JpaRepository<PensaoAlimenticia, Long> {

	Page<PensaoAlimenticia> findAllByFuncionarioNomeIgnoreCaseContaining(String nomeFuncionario, Pageable pageable);

	Optional<List<PensaoAlimenticia>> findByFuncionario(Funcionario funcionario);
	
	Boolean existsByVerba(Verba verba);
	
	Boolean existsByFuncionarioAndVerba(Funcionario funcionario, Verba verba);

}

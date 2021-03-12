package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.DeclaracaoExServidorDadosFuncionais;

@Repository
public interface DeclaracaoExServidorDadosFuncionaisRepository extends JpaRepository<DeclaracaoExServidorDadosFuncionais, Long>{

	List<DeclaracaoExServidorDadosFuncionais> findByDeclaracaoExServidorId(Long id);

}

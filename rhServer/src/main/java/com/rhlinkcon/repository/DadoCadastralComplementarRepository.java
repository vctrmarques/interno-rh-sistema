package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.DadoCadastralComplementar;

@Repository
public interface DadoCadastralComplementarRepository extends JpaRepository<DadoCadastralComplementar, Long> {
	
	DadoCadastralComplementar findByFuncionarioId(Long funcionarioId);

}

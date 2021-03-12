package com.rhlinkcon.repository.declaracaoExServidor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.DeclaracaoExServidor;

@Repository
public interface DeclaracaoExServidorRepository extends JpaRepository<DeclaracaoExServidor, Long>, DeclaracaoExServidorRepositoryCustom {



}

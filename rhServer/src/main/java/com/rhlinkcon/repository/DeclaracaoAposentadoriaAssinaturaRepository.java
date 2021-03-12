package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.DeclaracaoAposentadoriaAssinatura;

@Repository
public interface DeclaracaoAposentadoriaAssinaturaRepository extends JpaRepository<DeclaracaoAposentadoriaAssinatura, Long>{

}

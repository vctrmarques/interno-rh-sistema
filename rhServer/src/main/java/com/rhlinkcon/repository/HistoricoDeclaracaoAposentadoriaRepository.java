package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.HistoricoDeclaracaoAposentadoria;

@Repository
public interface HistoricoDeclaracaoAposentadoriaRepository
		extends JpaRepository<HistoricoDeclaracaoAposentadoria, Long> {

}

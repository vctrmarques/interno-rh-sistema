package com.rhlinkcon.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.RelatorioFinanceiroFolhaPagamentoHistorico;

@Repository
public interface RelatorioFinanceiroFolhaPagamentoHistoricoRepository extends JpaRepository<RelatorioFinanceiroFolhaPagamentoHistorico, Long> {
	
	@Transactional
	Long deleteByRelatorioFinanceiroFolhaPagamentoId(Long relatorioFinanceiroId);
	

}

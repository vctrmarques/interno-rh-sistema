package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.RelatorioFinanceiroFolhaPagamento;
import com.rhlinkcon.model.StatusRelatorioFinanceiroEnum;

@Repository
public interface RelatorioFinanceiroFolhaPagamentoRepository extends JpaRepository<RelatorioFinanceiroFolhaPagamento, Long> {
	
	List<RelatorioFinanceiroFolhaPagamento> findByHistoricoAtualStatusIn(List<StatusRelatorioFinanceiroEnum> listStatus);
	
	List<RelatorioFinanceiroFolhaPagamento> findByHistoricoAtualStatusInAndFolhaCompetenciaId(List<StatusRelatorioFinanceiroEnum> listStatus, Long folhaCompetenciaId);
	
//	Boolean existsByFolhaCompetenciaIdAndFiliaisIdInOrSituacoesFuncionaisIdInOrLotacoesIdIn(Long folhaCompetenciaId, List<Long> listEmpresaFilial, List<Long> listSituacaoFuncional, List<Long> listLotacoes);

}

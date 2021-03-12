package com.rhlinkcon.repository.adiantamentoPagamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.AdiantamentoPagamento;

@Repository
public interface AdiantamentoPagamentoRepository extends JpaRepository<AdiantamentoPagamento, Long>, AdiantamentoPagamentoRepositoryCustom {
	List<AdiantamentoPagamento> findByEmpresaFilialIdAndLotacaoId(Long filialId, Long lotacaoId);
	List<AdiantamentoPagamento> findByFuncionarioId(Long funcionarioId);
}
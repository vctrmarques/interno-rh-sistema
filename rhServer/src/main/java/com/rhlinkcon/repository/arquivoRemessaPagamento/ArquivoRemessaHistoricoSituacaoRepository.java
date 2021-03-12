package com.rhlinkcon.repository.arquivoRemessaPagamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ArquivoRemessaHistoricoSituacao;

@Repository
public interface ArquivoRemessaHistoricoSituacaoRepository extends JpaRepository<ArquivoRemessaHistoricoSituacao, Long> {

	ArquivoRemessaHistoricoSituacao findTopByOrderByIdDesc();

	List<ArquivoRemessaHistoricoSituacao> findAllByArquivoRemessaIdOrderByIdDesc(Long arquivoRemessaId);

}

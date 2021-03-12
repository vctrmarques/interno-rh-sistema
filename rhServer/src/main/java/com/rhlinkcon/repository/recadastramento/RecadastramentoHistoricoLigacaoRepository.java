package com.rhlinkcon.repository.recadastramento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rhlinkcon.model.RecadastramentoHistoricoLigacao;

public interface RecadastramentoHistoricoLigacaoRepository extends JpaRepository<RecadastramentoHistoricoLigacao, Long>{

	Page<RecadastramentoHistoricoLigacao> findAllByFuncionarioId(Long id, Pageable pageable);

}

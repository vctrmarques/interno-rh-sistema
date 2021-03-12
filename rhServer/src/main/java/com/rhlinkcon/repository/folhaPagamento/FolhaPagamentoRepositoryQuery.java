package com.rhlinkcon.repository.folhaPagamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.FolhaPagamento;

public interface FolhaPagamentoRepositoryQuery {
	List<FolhaPagamento> filterFolhaPagamento(Long filialId, Long lotacaoId, Long competenciaId);
}

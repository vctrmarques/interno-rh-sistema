package com.rhlinkcon.repository.arquivoRemessaPagamento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ArquivoRemessaPagamento;

@Repository
public interface ArquivoRemessaPagamentoRepository extends JpaRepository<ArquivoRemessaPagamento, Long>, ArquivoRemessaPagamentoRepositoryCustom {

	@Query("select max(ar.numeroRemessa) "
			+ " from ArquivoRemessaPagamento ar"
			+ " inner join FolhaPagamento f on (f.id = ar.folhaPagamento.id) "
			+ " inner join FolhaCompetencia fc on (fc.id = f.folhaCompetencia.id) "
			+ " where fc.anoCompetencia = :ano ")
	Integer maxNumeroByAno(@Param("ano") Integer ano);

	List<ArquivoRemessaPagamento> findAllByFolhaPagamentoId(Long folhaPagamentoId);


}

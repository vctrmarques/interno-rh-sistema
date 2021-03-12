package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rhlinkcon.model.TipoProcessamento;
import com.rhlinkcon.model.Verba;

public interface TipoProcessamentoRepository extends JpaRepository<TipoProcessamento, Long> {

	Page<TipoProcessamento> findByDescricaoIgnoreCaseContaining(String nome, Pageable pageable);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

	@Query("SELECT tp.verbas FROM TipoProcessamento tp WHERE tp.id = :id")
	List<Verba> findVerbasById(@Param("id") Long id);

	List<TipoProcessamento> findByDescricaoIgnoreCaseContaining(String search);

	@Query("SELECT DISTINCT(fp.tipoProcessamento) FROM FolhaPagamento fp WHERE fp.folhaCompetencia.id = :competenciaId AND fp.status = 'BLOQUEADO' AND fp.situacao = 'CONCLUIDO' ")
	List<TipoProcessamento> getTipoProcessListFolhaPagByCompetenciaId(@Param("competenciaId") Long competenciaId);
	
	Boolean existsByVerbasContaining(Verba verba);

}

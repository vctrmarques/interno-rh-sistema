package com.rhlinkcon.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.FolhaCompetencia;

@Repository
public interface FolhaCompetenciaRepository extends JpaRepository<FolhaCompetencia, Long> {

	Optional<FolhaCompetencia> findFirstByFimCompetenciaIsNullOrderByAnoCompetenciaDescMesCompetenciaDesc();

	Optional<FolhaCompetencia> findFirstByFimCompetenciaIsNotNullOrderByAnoCompetenciaDescMesCompetenciaDesc();

	Optional<FolhaCompetencia> findFolhaCompetenciaByMesCompetenciaAndAnoCompetencia(Integer mes, Integer ano);

	List<FolhaCompetencia> findByFimCompetenciaIsNullAndProgramacaoFechamentoNotNull();

	List<FolhaCompetencia> findIdByFimCompetenciaIsNotNull();

	Boolean existsFolhaCompetenciaByMesCompetenciaAndAnoCompetencia(Integer mes, Integer ano);

	Boolean existsFolhaCompetenciaByMesCompetenciaAndAnoCompetenciaAndFimCompetenciaIsNotNull(Integer mes, Integer ano);

	List<FolhaCompetencia> findAllByAnoCompetencia(Integer ano);

	@Query("select distinct(c.anoCompetencia) from FolhaCompetencia c where c.fimCompetencia is not null")
	List<Long> findAnosCompetenciasFechadas();
	
	@Query("select distinct(c.anoCompetencia) from FolhaCompetencia c")
	List<Long> findAnosCompetencias();

	List<FolhaCompetencia> findAllByAnoCompetenciaAndFimCompetenciaIsNotNull(Integer ano);

	@Query("SELECT DISTINCT(fc) FROM FolhaCompetencia fc, FolhaPagamento fp WHERE fc = fp.folhaCompetencia AND fp.status = 'BLOQUEADO' AND fp.situacao = 'CONCLUIDO' "
			+ "AND fc.anoCompetencia = :ano AND fc.fimCompetencia IS NOT NULL")
	List<FolhaCompetencia> competenciasPorAnoFolhaBloqueadaConcluida(@Param("ano") Integer ano);
	
	@Query("SELECT DISTINCT(fc) FROM FolhaCompetencia fc, FolhaPagamento fp WHERE fc = fp.folhaCompetencia AND fp.status = 'BLOQUEADO' AND fp.situacao = 'CONCLUIDO' "
			+ "AND fc.anoCompetencia = :ano ")
	List<FolhaCompetencia> competenciasPorAnoFolhaBloqueadaConcluidaAndCompetenciaNaoFechada(@Param("ano") Integer ano);

	@Modifying
	@Transactional
	@Query("update FolhaPagamento fp set fp.status = 'BLOQUEADO' where fp.folhaCompetencia.id = :folhaCompetenciaId")
	void updateAllStatusFolhaPagamento(@Param("folhaCompetenciaId") Long folhaCompetenciaId);

	@Query("select fc from  FolhaCompetencia fc WHERE CONCAT(CAST(fc.mesCompetencia as text) ,'/', CAST(fc.anoCompetencia as text)) IN (:listaPeriodos)")
	List<FolhaCompetencia> findByPeriodoString(@Param("listaPeriodos") List<String> listaPeriodos);

	Long findFolhaCompetenciaIdByMesCompetenciaAndAnoCompetencia(Integer mes, Integer ano);
}

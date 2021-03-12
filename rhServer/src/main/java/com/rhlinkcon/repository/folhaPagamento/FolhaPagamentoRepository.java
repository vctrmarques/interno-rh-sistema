package com.rhlinkcon.repository.folhaPagamento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.FolhaPagamento;
import com.rhlinkcon.model.SituacaoProcessamentoEnum;
import com.rhlinkcon.model.StatusFolhaEnum;

@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Long>, FolhaPagamentoRepositoryQuery {
	List<FolhaPagamento> findAllByFolhaCompetenciaId(Long id);

	@Query("SELECT f.situacao FROM FolhaPagamento f WHERE f.id = :id")
	String findSituacaoById(@Param("id") Long id);

	boolean existsByFolhaCompetenciaIdAndTipoProcessamentoIdAndFilialId(Long folhaCompetenciaId,
			Long tipoProcessamentoId, Long filialId);

	@Query("select fp.id from FolhaPagamento fp where fp.folhaCompetencia.id = :folhaCompetenciaId "
			+ "and fp.tipoProcessamento.id = :tipoProcessamentoId and fp.filial.id = :filialId")
	Long getIdByFolhaCompetenciaIdAndTipoProcessamentoIdAndFilialId(
			@Param("folhaCompetenciaId") Long folhaCompetenciaId,
			@Param("tipoProcessamentoId") Long tipoProcessamentoId, @Param("filialId") Long filialId);

	Page<FolhaPagamento> findAllByStatusAndTipoProcessamentoIdAndFilialId(StatusFolhaEnum status, Long processamentoId,
			Long filialId, Pageable pageable);

	List<FolhaPagamento> findAllByFolhaCompetenciaIdAndFilialIdIn(Long competenciaId, List<Long> filiaisId);

	boolean existsByFolhaCompetenciaId(Long folhaCompetenciaId);

	List<FolhaPagamento> findByFolhaCompetenciaId(Long folhaCompetenciaId);

	boolean existsByFolhaCompetenciaIdAndSituacaoNot(Long folhaCompetenciaId,
			SituacaoProcessamentoEnum situacaoProcessamento);

}

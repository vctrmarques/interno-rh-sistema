package com.rhlinkcon.repository.pensionista;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoSummaryDto;

@Repository
public interface PensionistaRepository extends JpaRepository<Pensionista, Long>, PensionistaRepositoryCustom {

	Page<Pensionista> findByFuncionarioNomeIgnoreCaseContaining(String descricao, Pageable pageable);

	Optional<List<Pensionista>> findByFuncionarioId(Long funcionarioId);

	Boolean existsByNome(String nome);

	Optional<Pensionista> findByMatricula(Integer matricula);

	Boolean existsByNomeAndIdNot(String nome, Long id);

	Boolean existsByMatricula(Integer matricula);

	Boolean existsByMatriculaAndIdNot(Integer matricula, Long id);

	Page<Pensionista> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	List<Pensionista> findByNomeIgnoreCaseContaining(String search);

	List<Pensionista> findByNomeIgnoreCaseContainingOrMatricula(String search, Integer matricula);

	Page<Pensionista> findByNomeIgnoreCaseContainingOrMatricula(String search, Integer matricula, Pageable pageable);

	Pensionista findTopByFuncionarioId(Long id);

	@Query("select p from Pensionista p WHERE p.status = true and p.funcionario.filial.id = :filialId "
			+ " and (p.pensaoPagamento.dataPrimeiroPagamento is null or p.pensaoPagamento.dataPrimeiroPagamento <= :dataCompetenciaFinal)"
			+ " and (p.pensaoPagamento.dataFimBeneficio is null or p.pensaoPagamento.dataFimBeneficio >= :dataCompetenciaFinal)")
	List<Pensionista> findAptosByFilialIdAndStatus(@Param("filialId") Long filialId,
			@Param("dataCompetenciaFinal") LocalDate dataCompetenciaFinal);

	@Query("SELECT fpc.pensionista.id AS id, MAX(fpc.id) AS idContracheque FROM Contracheque fpc "
			+ " WHERE fpc.pensionista is not null "
			+ " AND (:todasSitFunc IS TRUE OR fpc.pensionista.funcionario.tipoSituacaoFuncional.id in (:sitFuncIds)) "
			+ " AND (:pensSelecionadoId IS NULL OR fpc.pensionista.id = :pensSelecionadoId)"
			+ " AND (:dataNascimento IS NULL OR fpc.pensionista.dataNascimento = :dataNascimento)"
			+ " AND fpc.pensionista.funcionario.filial.id in (:filialIds) AND fpc.folhaPagamento.status = 'BLOQUEADO'"
			+ "	AND fpc.folhaPagamento.situacao = 'CONCLUIDO' AND fpc.folhaPagamento.folhaCompetencia.fimCompetencia IS NOT NULL GROUP BY fpc.pensionista.id ")
	List<ServidorPagBloqueadoSummaryDto> findInaptosByFilialIdAndStatus(@Param("filialIds") List<Long> filialIds,
			@Param("sitFuncIds") List<Long> sitFuncIds, @Param("todasSitFunc") boolean todasSitFunc,
			@Param("pensSelecionadoId") Long pensSelecionadoId, @Param("dataNascimento") LocalDate dataNascimento);

	@Query("SELECT p FROM Pensionista p WHERE p.funcionario.filial.id = :filialId "
			+ " AND (:isSemLotacao = true OR p.funcionario.lotacao.id in (:lotacoesId)) "
			+ " AND (:status is null OR p.status = :status)" + " ORDER BY p.nome")
	List<Pensionista> findAllByFuncionarioFilialIdAndFuncionarioLotacaoId(@Param("filialId") Long filialId,
			@Param("isSemLotacao") boolean isSemLotacao, @Param("lotacoesId") List<Long> lotacoesId,
			@Param("status") Boolean status);

}
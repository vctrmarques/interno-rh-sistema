package com.rhlinkcon.repository.funcionario;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.GrupoVinculoEnum;
import com.rhlinkcon.model.TipoHistoricoSituacaoFuncionalEnum;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoSummaryDto;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, FuncionarioRepositoryCustom {

	Boolean existsByMatricula(Integer matricula);

	Boolean existsByMatriculaAndIdNot(Integer matricula, Long id);

	Page<Funcionario> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	List<Funcionario> findByNomeIgnoreCaseContaining(String search);

	List<Funcionario> findByNomeIgnoreCaseContainingOrMatricula(String search, Integer matricula);

	Page<Funcionario> findByNomeIgnoreCaseContainingOrMatricula(String search, Integer matricula, Pageable pageable);

	@Query("select f from Funcionario f where (select count(p) from Processo p where p.funcionario.id = f.id)>0")
	Page<Funcionario> findByFuncionarioCountProcesso(Pageable pageable);

	@Query("select f from Funcionario f, AcidenteTrabalho at where f.id = at.funcionario.id and f.nome LIKE %:nomeOrMatricula% ")
	Page<Funcionario> findComAcidenteDeTrabalho(@Param("nomeOrMatricula") String nomeOrMatricula, Pageable pageable);

	@Query("select distinct f from Funcionario f, AcidenteTrabalho at where f.id = at.funcionario.id ")
	Page<Funcionario> findComAcidenteDeTrabalho(Pageable pageable);

	@Query("select f from Funcionario f where" + " ((select count(p) from Processo p where p.funcionario.id = f.id)>0)"
			+ " and f.nome like %:nome% ")
	Page<Funcionario> findByFuncionarioCountProcessoLikeFuncionarioNome(Pageable pageable, @Param("nome") String nome);

	@Query("SELECT f FROM Funcionario f WHERE f.id IN(SELECT ts.funcionario.id FROM TempoServico ts)")
	Page<Funcionario> getAllFuncionariosWithTempoServico(Pageable pageable);

	@Query("SELECT f FROM Funcionario f WHERE f.id IN(SELECT ts.funcionario.id FROM TempoServico ts) AND f.nome LIKE %:nome%")
	Page<Funcionario> getAllFuncionariosWithTempoServicoByName(Pageable pageable, @Param("nome") String nome);

	@Query("select f from Funcionario f where (select count(p) from PensaoAlimenticia p where p.funcionario.id = f.id)>0")
	Page<Funcionario> findByFuncionarioCountPensoes(Pageable pageable);

	@Query("select f from Funcionario f where"
			+ " ((select count(p) from PensaoAlimenticia p where p.funcionario.id = f.id)>0)"
			+ " and f.nome like %:nome% ")
	Page<Funcionario> findByFuncionarioCountPensaoLikeFuncionarioNome(Pageable pageable, @Param("nome") String nome);

	@Query("select f from Funcionario f where f.nome like %:nome% and ("
			+ " (f in (select rc.funcionario from RecisaoContrato rc where rc.status <> 'EFETIVADA' and rc.funcionario.id = f.id)) "
			+ " or " + " (f not in (select rc.funcionario from RecisaoContrato rc where rc.funcionario.id = f.id)) "
			+ " ) ")
	List<Funcionario> findSemRecisaoEfetivadas(@Param("nome") String nome);

	@Query("select f.id from Funcionario f where f.filial.id = :filialId")
	List<Long> findAllFuncionarioByFilialId(@Param("filialId") Long filialId);

	@Query("select f from Funcionario f where f.filial.id = :filialId")
	List<Funcionario> findAllFuncionarioByFilial(@Param("filialId") Long filialId);

	@Query("select f from Funcionario f where f.filial.id = :filialId and f.tipoSituacaoFuncional.entraFolha is true"
			+ " and (f.dataInicioSituacaoFuncional is null or f.dataInicioSituacaoFuncional <= :dataCompetenciaFinal)"
			+ " and (f.dataFimSituacaoFuncional is null or f.dataFimSituacaoFuncional <= :dataCompetenciaFinal)")
	List<Funcionario> findAptosByFilialIdAndTipoSituacaoFuncionalEntraFolha(@Param("filialId") Long filialId,
			@Param("dataCompetenciaFinal") Instant dataCompetenciaFinal);

	@Query("SELECT fpc.funcionario.id AS id, MAX(fpc.id) AS idContracheque FROM Contracheque fpc "
			+ " WHERE fpc.funcionario.filial.id in (:filialIds) AND (:todasSitFunc IS TRUE OR fpc.funcionario.tipoSituacaoFuncional.id in (:sitFuncIds)) AND fpc.folhaPagamento.status = 'BLOQUEADO'"
			+ " AND (:funcSelecionadoId IS NULL OR fpc.funcionario.id = :funcSelecionadoId)"
			+ " AND (:dataNascimento IS NULL OR fpc.funcionario.dataNascimento = :dataNascimento)"
			+ "	AND fpc.folhaPagamento.situacao = 'CONCLUIDO' AND fpc.folhaPagamento.folhaCompetencia.fimCompetencia IS NOT NULL GROUP BY fpc.funcionario.id ")
	List<ServidorPagBloqueadoSummaryDto> findInaptosByFilialIdInAndSituacaoFuncIdIn(
			@Param("filialIds") List<Long> filialIds, @Param("sitFuncIds") List<Long> sitFuncIds,
			@Param("todasSitFunc") boolean todasSitFunc, @Param("funcSelecionadoId") Long funcSelecionadoId,
			@Param("dataNascimento") Instant dataNascimento);

	@Query("select f from Funcionario f where f.matricula = :matricula")

	public Funcionario findByMatricula(@Param("matricula") String matricula);

	Long countByFilialId(Long filialId);

	Long countByFilialIdAndLotacaoId(Long filialId, Long lotacaoId);

	@Query("select f.emailCorporativo from Funcionario f where f.id = :id")
	String findEmailById(@Param("id") Long id);

	Optional<Funcionario> findByMatricula(Integer matricula);

	List<Funcionario> findByNomeIgnoreCaseContainingAndTipoSituacaoFuncionalDescricaoIn(String search,
			List<String> nomes);
	
	Optional<List<Funcionario>> findByTipoSituacaoFuncionalId(Long tipoSituacaoFuncionalId);

	@Query("select f from Funcionario f where (:nome is null or f.nome like %:nome%) "
			+ "and (:matricula is null or (cast(f.matricula as string) like cast(:matricula as string))) and (:cpf is null or f.cpf like %:cpf%) "
			+ " and (:pis is null or f.pisPasep like %:pis%)")
	List<Funcionario> findByDadosPessoais(@Param("nome") String searchFuncionario,
			@Param("matricula") Integer searchMatricula, @Param("cpf") String searchCPF,
			@Param("pis") String searchPis);

	@Query("select f from Funcionario f where "
			+ " f in (select func from Funcionario func where (func.vinculo.grupo = 'COMISSIONADO' and func.dataAdmissao between (:dataInicio) and (:dataFim)) or func.vinculo.grupo in (:values)) "
			+ " and (:nome is null or f.nome like %:nome%) "
			+ " and (:matricula is null or (cast(f.matricula as string) like cast(:matricula as string))) and (:cpf is null or f.cpf like %:cpf%) "
			+ " and (:pis is null or f.pisPasep like %:pis%)")
	List<Funcionario> findAdmitidosNoPeriodo(@Param("nome") String searchFuncionario,
			@Param("matricula") Integer searchMatricula, @Param("cpf") String searchCPF, @Param("pis") String searchPis,
			@Param("dataInicio") Instant dataInicio, @Param("dataFim") Instant dataFim,
			@Param("values") List<GrupoVinculoEnum> values);

	@Query("select f from Funcionario f where f.tipoSituacaoFuncional.descricao in (:values) "
			+ "and (:nome is null or f.nome like %:nome%) "
			+ "and (:matricula is null or (cast(f.matricula as string) like cast(:matricula as string))) "
			+ "and (:cpf is null or f.cpf like %:cpf%) " + "and (:pis is null or f.pisPasep like %:pis%)")
	List<Funcionario> findAposentados(@Param("nome") String searchFuncionario,
			@Param("matricula") Integer searchMatricula, @Param("cpf") String searchCPF, @Param("pis") String searchPis,
			@Param("values") List<String> values);

	@Query("select f from Funcionario f where f in (select h.funcionario from HistoricoSituacaoFuncional h where h.tipoHistoricoSituacaoFuncional = :enum) "
			+ "and (:nome is null or f.nome like %:nome%) "
			+ "and (:matricula is null or (cast(f.matricula as string) like cast(:matricula as string))) "
			+ "and (:cpf is null or f.cpf like %:cpf%) " + "and (:pis is null or f.pisPasep like %:pis%)")
	List<Funcionario> findExonerados(@Param("nome") String searchFuncionario,
			@Param("matricula") Integer searchMatricula, @Param("cpf") String searchCPF, @Param("pis") String searchPis,
			@Param("enum") TipoHistoricoSituacaoFuncionalEnum valor);

	@Query("SELECT f FROM Funcionario f WHERE f.tipoSituacaoFuncional.descricao = 'APOSENTADO' "
			+ " AND f.filial.id = :filialId " + " AND (:isSemLotacao = true OR f.lotacao.id in (:lotacoesId)) "
			+ " ORDER BY f.nome ASC")
	List<Funcionario> findAllAposentadosByFilialIdAndLotacaoId(@Param("filialId") Long filialId,
			@Param("isSemLotacao") boolean isSemLotacao, @Param("lotacoesId") List<Long> lotacoesId);
}

package com.rhlinkcon.repository.contracheque;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.FolhaCompetencia;
import com.rhlinkcon.model.FolhaPagamento;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.SituacaoProcessamentoEnum;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoFuncionarioCountDto;
import com.rhlinkcon.payload.batimentoFolhaPagamento.ProjecaoSomatorioValores;
import com.rhlinkcon.payload.batimentoFolhaPagamento.ProjecaoSomatorioValoresFiliais;
import com.rhlinkcon.payload.dirf.ProjecaoDirfBeneficiario;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoFilialDto;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto;

@Repository
public interface ContrachequeRepository extends JpaRepository<Contracheque, Long>, ContrachequeRepositoryQuery {

	@Query("select distinct(c.tipoSituacaoFuncional) from Contracheque c where c.folhaPagamento.id = :folhaPagamentoId")
	List<String> findDistinctTipoSituacaoFuncionalByFolhaPagamentoId(@Param("folhaPagamentoId") Long folhaPagamentoId);

	@Modifying
	@Transactional
	@Query("update Contracheque set situacao = 'PENDENTE' where folhaPagamento = :folhaPagamento")
	void updateSituacaoParaPendenteByFolhaPag(@Param("folhaPagamento") FolhaPagamento folhaPagamento);

	@Query("select c.valorLiquido from Contracheque c where c.id = :id")
	Double findValorLiquidoByContrachequeId(@Param("id") Long id);

	List<Contracheque> findByFolhaPagamentoAndFuncionarioIsNotNull(FolhaPagamento folhaPagamento);

	List<Contracheque> findByFolhaPagamentoAndPensionistaIsNotNull(FolhaPagamento folhaPagamento);

	List<Contracheque> findByFolhaPagamentoAndFuncionarioIdNotIn(FolhaPagamento folhaPagamento,
			List<Long> funcionariosId);

	List<Contracheque> findByFolhaPagamentoAndPensionistaIdNotIn(FolhaPagamento folhaPagamento,
			List<Long> pensionistasId);

	Optional<List<Contracheque>> findByFolhaPagamentoAndFuncionarioNotNull(FolhaPagamento folhaPagamento);

	Optional<List<Contracheque>> findByFolhaPagamentoAndPensionistaNotNull(FolhaPagamento folhaPagamento);

	Optional<Contracheque> findByFolhaPagamentoIdAndPensionistaId(Long folhaPagamentoId, Long pensionistaId);

	boolean existsByFolhaPagamentoIdAndPensionistaId(Long folhaPagamentoId, Long pensionistaId);

	Optional<List<Contracheque>> findByFolhaPagamento(FolhaPagamento folhaPagamento);

	Optional<Contracheque> findByFolhaPagamentoIdAndFuncionarioId(Long folhaPagamentoId, Long funcionarioId);

	boolean existsByFolhaPagamentoIdAndFuncionarioId(Long folhaPagamentoId, Long funcionarioId);

	List<Contracheque> findByFolhaPagamentoId(Long folhaPagamentoId);

	List<Contracheque> findByFolhaPagamentoIdAndFuncionarioIsNotNullAndTipoSituacaoFuncionalAndLotacaoIn(
			Long folhaPagamentoId, String tipoSituacaoFuncional, List<String> lotacoesString);

	List<Contracheque> findByFolhaPagamentoIdAndPensionistaIsNotNullAndLotacaoIn(Long folhaPagamentoId,
			List<String> lotacoesString);

	Long countByFolhaPagamentoId(Long folhaPagamentoId);

	List<Contracheque> findByFuncionarioIdAndFolhaPagamentoTipoProcessamentoId(Long funcionarioId,
			Long tipoProcessamentoId);

	Long countByFolhaPagamentoIdAndSituacao(Long folhaPagamentoId, SituacaoProcessamentoEnum situacao);

	boolean existsByFolhaPagamentoIdAndSituacao(Long folhaPagamentoId, SituacaoProcessamentoEnum situacao);

	boolean existsByFolhaPagamentoIdAndSituacaoNot(Long folhaPagamentoId, SituacaoProcessamentoEnum situacao);

	Optional<Contracheque> findByFuncionarioIdAndFolhaPagamentoFolhaCompetenciaMesCompetenciaBetweenAndFolhaPagamentoFolhaCompetenciaAnoCompetenciaBetween(
			Long funcionarioId, Integer mesInicio, Integer mesFim, Integer anoInicio, Integer anoFim);

	@Query("select fc from Contracheque fc WHERE fc.funcionario.id =:funcionarioId and fc.folhaPagamento.folhaCompetencia IN (:listaFolhaCompetencia)")
	List<Contracheque> listByFuncionarioIdAndFolhaCompetencias(@Param("funcionarioId") Long funcionarioId,
			@Param("listaFolhaCompetencia") List<FolhaCompetencia> listaFolhaCompetencia);

	@Query("select f from Funcionario f where f.id not in(select c.funcionario.id from Contracheque c where c.folhaPagamento.id = :folhaPagamentoId)"
			+ "and f.filial.id = :filialId and f.nome like :nome% and f.lotacao.id like :lotacao")
	Page<Funcionario> findByIdNotIn(@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("filialId") Long filialId,
			@Param("nome") String nome, @Param("lotacao") Long lotacaoId, Pageable pageable);

	@Query("select f from Funcionario f where f.id not in(select c.funcionario.id from Contracheque c where c.folhaPagamento.id = :folhaPagamentoId)"
			+ "and f.filial.id = :filialId and f.nome like :nome% ")
	Page<Funcionario> findByIdNotIn(@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("filialId") Long filialId,
			@Param("nome") String nome, Pageable pageable);

	@Query("select f from Funcionario f where f.id not in(select c.funcionario.id from Contracheque c where c.folhaPagamento.id = :folhaPagamentoId)"
			+ "and f.filial.id = :filialId and f.nome like :nome% and f.dataAdmissao = :dataAdmissao ")
	Page<Funcionario> findByIdNotIn(@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("filialId") Long filialId,
			@Param("nome") String nome, @Param("dataAdmissao") Instant dataInicio, Pageable pageable);

	@Query("select f from Funcionario f where f.id not in(select c.funcionario.id from Contracheque c where c.folhaPagamento.id = :folhaPagamentoId)"
			+ "and f.filial.id = :filialId and f.nome like :nome% and f.dataAdmissao = :dataAdmissao and f.lotacao.id like :lotacao")
	Page<Funcionario> findByIdNotIn(@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("filialId") Long filialId,
			@Param("nome") String nome, @Param("lotacao") Long lotacaoId, @Param("dataAdmissao") Instant dataInicio,
			Pageable pageable);

	List<Contracheque> findByFuncionarioIdAndFolhaPagamentoFolhaCompetenciaAnoCompetencia(Long funcionarioId,
			Integer ano);

	List<Contracheque> findByFuncionarioIdAndFolhaPagamentoFolhaCompetenciaId(Long funcionarioId,
			Long folhaCompetenciaId);

	@Query("select count(fcc.id) as quantidade, sum(fcc.valorBruto) as vantagens, sum(fcc.valorDesconto) as descontos, sum(fcc.valorLiquido) as liquidos "
			+ " from Contracheque fcc " + " inner join FolhaPagamento f on (f.id = fcc.folhaPagamento.id) "
			+ " inner join FolhaCompetencia fc on (fc.id = f.folhaCompetencia.id) "
			+ " where fc.mesCompetencia = :mes and fc.anoCompetencia = :ano and f.tipoProcessamento.id = :tipoProcessamentoId ")
	ProjecaoSomatorioValores getSomatorioValoresOrgao(@Param("mes") Integer mes, @Param("ano") Integer ano,
			@Param("tipoProcessamentoId") Long tipoProcessamentoId);

	@Query("select distinct f.filial.id as empresa, count(fcc.id) as quantidade, sum(fcc.valorBruto) as vantagens, sum(fcc.valorDesconto) as descontos, sum(fcc.valorLiquido) as liquidos "
			+ " from Contracheque fcc " + " inner join FolhaPagamento f on (f.id = fcc.folhaPagamento.id) "
			+ " inner join FolhaCompetencia fc on (fc.id = f.folhaCompetencia.id) "
			+ " where fc.mesCompetencia = :mes and fc.anoCompetencia = :ano and f.tipoProcessamento.id = :tipoProcessamentoId "
			+ " group by f.filial.id ")
	List<ProjecaoSomatorioValoresFiliais> getSomatorioValoresFiliais(@Param("mes") Integer mes,
			@Param("ano") Integer ano, @Param("tipoProcessamentoId") Long tipoProcessamentoId);

	List<Contracheque> findAllByFolhaPagamentoFolhaCompetenciaIdAndFolhaPagamentoTipoProcessamentoIdAndFolhaPagamentoFilialId(
			Long folhaCompetenciaId, Long tipoProcessamentoId, Long filialId);

	// Relatório Gerencial Por Filial
	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c), c.folhaPagamento.filial) "
			+ "FROM Contracheque c " + "WHERE c.folhaPagamento.tipoProcessamento.id = :tipoProcessamentoId "
			+ "AND c.folhaPagamento.folhaCompetencia.id = :folhaCompetenciaId GROUP BY c.folhaPagamento, c.folhaPagamento.filial")
	Page<RelatorioGerencialDto> getRelGerencialFilialByTipoProcessAndFolhaCompId(
			@Param("tipoProcessamentoId") Long tipoProcessamentoId,
			@Param("folhaCompetenciaId") Long folhaCompetenciaId, Pageable pageable);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c), c.folhaPagamento.filial) "
			+ "FROM Contracheque c " + "WHERE c.folhaPagamento.tipoProcessamento.id = :tipoProcessamentoId "
			+ "AND c.folhaPagamento.folhaCompetencia.id = :folhaCompetenciaId GROUP BY c.folhaPagamento, c.folhaPagamento.filial")
	List<RelatorioGerencialDto> getRelGerencialFilialByTipoProcessAndFolhaCompId(
			@Param("tipoProcessamentoId") Long tipoProcessamentoId,
			@Param("folhaCompetenciaId") Long folhaCompetenciaId);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c)) "
			+ "FROM Contracheque c " + "WHERE c.folhaPagamento.tipoProcessamento.id = :tipoProcessamentoId "
			+ "AND c.folhaPagamento.folhaCompetencia.id = :folhaCompetenciaId AND c.folhaPagamento.filial.id = :filialId")
	RelatorioGerencialDto getRelGerencialFilialByTipoProcessAndFolhaCompIdAndFilialId(
			@Param("tipoProcessamentoId") Long tipoProcessamentoId,
			@Param("folhaCompetenciaId") Long folhaCompetenciaId, @Param("filialId") Long filialId);

	// Relatório Gerencial Por Cargo
	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c), c.cargoEfetivo) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) GROUP BY c.cargoEfetivo ")
	Page<RelatorioGerencialDto> getRelGerencialCargoByFolhaPagamentoId(@Param("folhaPagamentoId") Long folhaPagamentoId,
			@Param("lotacao") String lotacao, Pageable pageable);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c), c.cargoEfetivo) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) GROUP BY c.cargoEfetivo")
	List<RelatorioGerencialDto> getRelGerencialCargoByFolhaPagamentoId(@Param("folhaPagamentoId") Long folhaPagamentoId,
			@Param("lotacao") String lotacao);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c)) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId AND c.cargoEfetivo = :cargoEfetivo "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) ")
	RelatorioGerencialDto getRelGerencialCargoByFolhaPagamentoIdAndCargo(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("lotacao") String lotacao,
			@Param("cargoEfetivo") String cargoEfetivo);

	// Relatório Gerencial Por Lotação
	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c), c.lotacao) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId GROUP BY c.lotacao")
	Page<RelatorioGerencialDto> getRelGerencialLotacaoByFolhaPagamentoId(
			@Param("folhaPagamentoId") Long folhaPagamentoId, Pageable pageable);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c), c.lotacao) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId GROUP BY c.lotacao")
	List<RelatorioGerencialDto> getRelGerencialLotacaoByFolhaPagamentoId(
			@Param("folhaPagamentoId") Long folhaPagamentoId);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c)) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId AND c.lotacao = :lotacao")
	RelatorioGerencialDto getRelGerencialLotacaoByFolhaPagamentoIdAndLotacao(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("lotacao") String lotacao);

	// Relatório Gerencial Por Função
	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c), c.cargoFuncao) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) GROUP BY c.cargoFuncao")
	Page<RelatorioGerencialDto> getRelGerencialFuncaoByFolhaPagamentoId(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("lotacao") String lotacao, Pageable pageable);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c), c.cargoFuncao) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) GROUP BY c.cargoFuncao")
	List<RelatorioGerencialDto> getRelGerencialFuncaoByFolhaPagamentoId(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("lotacao") String lotacao);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c)) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId AND c.cargoEfetivo = :cargoFuncao "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) ")
	RelatorioGerencialDto getRelGerencialFuncaoByFolhaPagamentoIdAndFuncao(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("lotacao") String lotacao,
			@Param("cargoFuncao") String cargoFuncao);

	// Relatório Gerencial Por Vinculo
	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c), c.vinculo) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) GROUP BY c.vinculo")
	Page<RelatorioGerencialDto> getRelGerencialVinculoByFolhaPagamentoId(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("lotacao") String lotacao, Pageable pageable);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c), c.vinculo) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) GROUP BY c.vinculo")
	List<RelatorioGerencialDto> getRelGerencialVinculoByFolhaPagamentoId(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("lotacao") String lotacao);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c)) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId AND c.vinculo = :vinculo "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) ")
	RelatorioGerencialDto getRelGerencialVinculoByFolhaPagamentoIdAndVinculo(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("lotacao") String lotacao,
			@Param("vinculo") String vinculo);

	// Relatório Gerencial Por Funcionário
	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(c) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId "
			+ "AND (:cargoEfetivo IS NULL OR c.cargoEfetivo = :cargoEfetivo) "
			+ "AND (:cargoFuncao IS NULL OR c.cargoFuncao = :cargoFuncao) "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) ")
	Page<RelatorioGerencialDto> getRelGerencialFuncionarioByFolhaPagamentoId(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("cargoEfetivo") String cargoEfetivo,
			@Param("cargoFuncao") String cargoFuncao, @Param("lotacao") String lotacao, Pageable pageable);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(c) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId "
			+ "AND (:cargoEfetivo IS NULL OR c.cargoEfetivo = :cargoEfetivo) "
			+ "AND (:cargoFuncao IS NULL OR c.cargoFuncao = :cargoFuncao) "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) ")
	List<RelatorioGerencialDto> getRelGerencialFuncionarioByFolhaPagamentoId(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("cargoEfetivo") String cargoEfetivo,
			@Param("cargoFuncao") String cargoFuncao, @Param("lotacao") String lotacao);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(c) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId AND c.funcionario.id = :funcionarioId "
			+ "AND (:cargoEfetivo IS NULL OR c.cargoEfetivo = :cargoEfetivo) "
			+ "AND (:cargoFuncao IS NULL OR c.cargoFuncao = :cargoFuncao) "
			+ "AND (:lotacao IS NULL OR c.lotacao = :lotacao) ")
	RelatorioGerencialDto getRelGerencialFuncionarioByFolhaPagamentoIdAndFuncionarioId(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("cargoEfetivo") String cargoEfetivo,
			@Param("cargoFuncao") String cargoFuncao, @Param("lotacao") String lotacao,
			@Param("funcionarioId") Long funcionarioId);

	// Relatório Gerencial - Somatório
	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c)) "
			+ "FROM Contracheque c " + "WHERE c.folhaPagamento.tipoProcessamento.id = :tipoProcessamentoId "
			+ "AND c.folhaPagamento.folhaCompetencia.id = :folhaCompetenciaId")
	RelatorioGerencialDto getRelGerencialFilialByTipoProcessAndFolhaCompIdSomatorio(
			@Param("tipoProcessamentoId") Long tipoProcessamentoId,
			@Param("folhaCompetenciaId") Long folhaCompetenciaId);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(c.valorBruto), SUM(c.valorDesconto), AVG(c.valorBruto), COUNT(c)) "
			+ "FROM Contracheque c WHERE c.folhaPagamento.id = :folhaPagamentoId "
			+ "AND (:cargoEfetivo IS NULL OR c.cargoEfetivo = :cargoEfetivo) "
			+ "AND (:cargoFuncao IS NULL OR c.cargoFuncao = :cargoFuncao) "
			+ "AND (:vinculo IS NULL OR c.vinculo = :vinculo) " + "AND (:lotacao IS NULL OR c.lotacao = :lotacao) ")
	RelatorioGerencialDto getRelGerencialByFolhaPagamentoIdSomatorio(@Param("folhaPagamentoId") Long folhaPagamentoId,
			@Param("cargoEfetivo") String cargoEfetivo, @Param("cargoFuncao") String cargoFuncao,
			@Param("lotacao") String lotacao, @Param("vinculo") String vinculo);

	@Query("SELECT fu.id as id, fu.nome as nome, fu.cpf as cpf, "
			+ " p.id as peId, p.nome as peNome, p.cpf as peCpf, p.funcionario.id as fpId, "
			+ " sum(fcc.valorBruto) as vantagens, sum(fcc.valorDesconto) as descontos, sum(fcc.valorLiquido) as liquidos, "
			+ " s.descricao as situacao " + " FROM Contracheque fcc "
			+ " LEFT JOIN Funcionario fu on (fcc.funcionario.id = fu.id) "
			+ " LEFT JOIN Pensionista p on (fcc.pensionista.id = p.id) "
			+ " LEFT JOIN SituacaoFuncional s on (fu.tipoSituacaoFuncional.id = s.id) "
			+ " INNER JOIN FolhaPagamento f on (fcc.folhaPagamento.id = f.id) "
			+ " INNER JOIN FolhaCompetencia fc on (f.folhaCompetencia.id = fc.id) "
			+ " WHERE fc.anoCompetencia = :anoBase and f.filial.id = :filialId and (:funcionarioId is null or fu.id = :funcionarioId) "
			+ " GROUP BY fu.id, fu.nome, fu.cpf, p.id, p.nome, p.cpf, p.funcionario.id, s.descricao "
			+ " ORDER BY fu.nome, p.nome")
	List<ProjecaoDirfBeneficiario> getProjecaoDirfFuncionarioContracheque(@Param("anoBase") Integer anoBase,
			@Param("filialId") Long filialId, @Param("funcionarioId") Long funcionarioId);

	@Query("SELECT fu.id as id, fu.nome as nome, fu.cpf as cpf, "
			+ " p.id as peId, p.nome as peNome, p.cpf as peCpf, p.funcionario.id as fpId, "
			+ " sum(fcc.valorBruto) as vantagens, sum(fcc.valorDesconto) as descontos, sum(fcc.valorLiquido) as liquidos, "
			+ " s.descricao as situacao " + " FROM Contracheque fcc "
			+ " LEFT JOIN Funcionario fu on (fcc.funcionario.id = fu.id) "
			+ " LEFT JOIN Pensionista p on (fcc.pensionista.id = p.id) "
			+ " LEFT JOIN SituacaoFuncional s on (fu.tipoSituacaoFuncional.id = s.id) "
			+ " INNER JOIN FolhaPagamento f on (fcc.folhaPagamento.id = f.id) "
			+ " INNER JOIN FolhaCompetencia fc on (f.folhaCompetencia.id = fc.id) "
			+ " WHERE fc.anoCompetencia = :anoBase and f.filial.id = :filialId and "
			+ " (:query is null or fu.nome like %:query% or fu.cpf like %:query% or p.nome like %:query% or p.cpf like %:query%) "
			+ " GROUP BY fu.id, fu.nome, fu.cpf, p.id, p.nome, p.cpf, p.funcionario.id, s.descricao "
			+ " ORDER BY fu.nome, p.nome")
	List<ProjecaoDirfBeneficiario> getProjecaoDirfFuncionarioPensionista(@Param("anoBase") Integer anoBase,
			@Param("filialId") Long filialId, @Param("query") String query);

	@Query("SELECT new com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoFuncionarioCountDto(fcc.funcionario.tipoSituacaoFuncional.id, fcc.funcionario.tipoSituacaoFuncional.descricao, COUNT(fcc)) "
			+ " FROM Contracheque fcc "
			+ " WHERE fcc.folhaPagamento.folhaCompetencia.id = :competenciaId and fcc.folhaPagamento.tipoProcessamento.id = :tipoProcessamentoId "
			+ " AND (:filialId is null or fcc.folhaPagamento.filial.id = :filialId) "
			+ " GROUP BY fcc.funcionario.tipoSituacaoFuncional.id, fcc.funcionario.tipoSituacaoFuncional.descricao ")
	List<BatimentoFuncionarioCountDto> getCountTotalFuncionariosPorSituacao(@Param("competenciaId") Long competenciaId,
			@Param("tipoProcessamentoId") Long tipoProcessamentoId, @Param("filialId") Long filialId);

	@Query("SELECT new com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoFuncionarioCountDto(fcc.funcionario.lotacao.id, fcc.funcionario.lotacao.descricao, COUNT(fcc)) "
			+ " FROM Contracheque fcc "
			+ " WHERE fcc.folhaPagamento.folhaCompetencia.id = :competenciaId and fcc.folhaPagamento.tipoProcessamento.id = :tipoProcessamentoId AND fcc.folhaPagamento.filial.id = :filialId "
			+ " GROUP BY fcc.funcionario.lotacao.id, fcc.funcionario.lotacao.descricao ")
	List<BatimentoFuncionarioCountDto> getCountTotalFuncionariosPorLotacao(@Param("competenciaId") Long competenciaId,
			@Param("tipoProcessamentoId") Long tipoProcessamentoId, @Param("filialId") Long filialId);

	@Query("SELECT new com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoFilialDto(c.folhaPagamento.filial.nomeFilial, COUNT(c.funcionario), "
			+ " SUM(c.valorLiquido), SUM(c.valorBruto)) " + " FROM Contracheque c "
			+ " WHERE c.folhaPagamento.folhaCompetencia.anoCompetencia = :ano "
			+ " AND c.folhaPagamento.folhaCompetencia.mesCompetencia = :mes "
			+ " AND c.folhaPagamento.filial.id IN (:filialList) "
			+ " AND c.tipoSituacaoFuncional IN (:situacaoFuncionalList) "
			+ " AND c.folhaPagamento.tipoProcessamento.id = :tipoProcessamentoId "
			+ " GROUP BY c.folhaPagamento.filial.nomeFilial ")
	List<RelatorioFolhaPagamentoResumoFilialDto> getDadosContrachequeByFiltros(
			@Param("filialList") ArrayList<Long> filialList,
			@Param("situacaoFuncionalList") ArrayList<String> situacaoFuncionalList, @Param("ano") Integer ano,
			@Param("mes") Integer mes, @Param("tipoProcessamentoId") Long tipoProcessamentoId);

	@Query("SELECT new com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoFilialDto(c.folhaPagamento.filial.nomeFilial, COUNT(c.funcionario), "
			+ " SUM(c.valorLiquido), SUM(c.valorBruto)) " + " FROM Contracheque c "
			+ " WHERE c.folhaPagamento.folhaCompetencia.anoCompetencia = :ano "
			+ " AND c.folhaPagamento.folhaCompetencia.mesCompetencia = :mes "
			+ " AND c.folhaPagamento.filial.id IN (:filialList) "
			+ " AND c.folhaPagamento.tipoProcessamento.id = :tipoProcessamentoId "
			+ " GROUP BY c.folhaPagamento.filial.nomeFilial ")
	List<RelatorioFolhaPagamentoResumoFilialDto> getDadosContrachequeByFiltros(
			@Param("filialList") ArrayList<Long> filialList, @Param("ano") Integer ano, @Param("mes") Integer mes,
			@Param("tipoProcessamentoId") Long tipoProcessamentoId);

}

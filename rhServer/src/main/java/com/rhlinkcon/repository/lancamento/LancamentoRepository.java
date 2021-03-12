package com.rhlinkcon.repository.lancamento;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.FuncionarioVerba;
import com.rhlinkcon.model.IdentificacaoVerbaEnum;
import com.rhlinkcon.model.Lancamento;
import com.rhlinkcon.model.PensionistaVerba;
import com.rhlinkcon.model.TipoVerba;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoVerbaDto;
import com.rhlinkcon.payload.dirf.DirfValoresDto;
import com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoProventosDto;
import com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	@Modifying
	@Transactional
	@Query("delete from Lancamento f where f.contracheque = :contracheque")
	void deleteByContracheque(@Param("contracheque") Contracheque contracheque);

	@Query("select sum(fv.valor) " + " from Lancamento fv " + " inner join Verba v on (v.id = fv.verba.id) "
			+ " where v.codigo in (:codigos) and fv.contracheque.id in "
			+ " (select fcc.id from Contracheque fcc where fcc.funcionario.id = :funcionarioId and fcc.folhaPagamento.id in "
			+ " (select f.id from FolhaPagamento f where f.folhaCompetencia.id in "
			+ " (select fc.id from FolhaCompetencia fc where fc.anoCompetencia = :anoBase) and f.filial.id = :filialId)) ")
	Double somaValorVerbasFuncionarioByFiltros(@Param("anoBase") Integer anoBase, @Param("filialId") Long filialId,
			@Param("funcionarioId") Long funcionarioId, @Param("codigos") List<String> codigos);

	@Query("SELECT new com.rhlinkcon.payload.relatorioGerencial.RelatorioGerencialDto(SUM(fpcv.valor), COUNT(fpcv), fpcv.verba) "
			+ "FROM Lancamento fpcv WHERE fpcv.contracheque.folhaPagamento.id = :folhaPagamentoId "
			+ "AND (:cargoEfetivo IS NULL OR fpcv.contracheque.cargoEfetivo = :cargoEfetivo) "
			+ "AND (:cargoFuncao IS NULL OR fpcv.contracheque.cargoFuncao = :cargoFuncao) "
			+ "AND (:funcionarioId IS NULL OR fpcv.contracheque.funcionario.id = :funcionarioId) "
			+ "AND (:lotacao IS NULL OR fpcv.contracheque.lotacao = :lotacao) "
			+ "AND fpcv.verba.tipoVerba != 'OUTROS' GROUP BY fpcv.verba")
	List<RelatorioGerencialDto> getRelGerencialResumoByFolhaPagamentoId(Long folhaPagamentoId, String cargoEfetivo,
			String cargoFuncao, Long funcionarioId, String lotacao);

	@Query("select new com.rhlinkcon.payload.dirf.DirfValoresDto(fc.mesCompetencia, sum(fv.valor)) "
			+ " from Lancamento fv " + " left join Verba v on (v.id = fv.verba.id) "
			+ " left join Contracheque fcc on (fv.contracheque.id = fcc.id) "
			+ " left join Funcionario fu on (fcc.funcionario.id = fu.id) "
			+ " left join FolhaPagamento f on (fcc.folhaPagamento.id = f.id) "
			+ " left join FolhaCompetencia fc on (f.folhaCompetencia.id = fc.id) "
			+ " where v.codigo in (:codigos) and fc.anoCompetencia = :anoBase and f.filial.id = :filialId and fu.id = :funcionarioId "
			+ " group by fc.mesCompetencia ")
	List<DirfValoresDto> somaValorVerbasMesFuncionarioByFiltros(@Param("anoBase") Integer anoBase,
			@Param("filialId") Long filialId, @Param("funcionarioId") Long funcionarioId,
			@Param("codigos") List<String> codigos);

	Boolean existsByVerba(Verba verba);

	boolean existsByFuncionarioVerba(FuncionarioVerba funcionarioVerba);

	boolean existsByPensionistaVerba(PensionistaVerba pensionistaVerba);

	@Query("select l.funcionarioVerba from Lancamento l where l.contracheque.folhaPagamento.id = :folhaPagamentoId "
			+ " and l.contracheque.folhaPagamento.status = 'BLOQUEADO' and l.contracheque.folhaPagamento.situacao = 'CONCLUIDO' "
			+ " and l.contracheque.folhaPagamento.folhaCompetencia.fimCompetencia is not null "
			+ " and l.funcionarioVerba is not null and l.funcionarioVerba.ativo is true "
			+ " and l.funcionarioVerba.recorrencia = 'EM_PARCELA' and l.funcionarioVerba.parcelas > l.funcionarioVerba.parcelasPagas ")
	List<FuncionarioVerba> getFuncionarioVerbasPagasByFolhaPagamentoId(
			@Param("folhaPagamentoId") Long folhaPagamentoId);

	@Query("select l.pensionistaVerba from Lancamento l where l.contracheque.folhaPagamento.id = :folhaPagamentoId "
			+ " and l.contracheque.folhaPagamento.status = 'BLOQUEADO' and l.contracheque.folhaPagamento.situacao = 'CONCLUIDO' "
			+ " and l.contracheque.folhaPagamento.folhaCompetencia.fimCompetencia is not null "
			+ " and l.pensionistaVerba is not null and l.pensionistaVerba.ativo is true "
			+ " and l.pensionistaVerba.recorrencia = 'EM_PARCELA' and l.pensionistaVerba.parcelas > l.pensionistaVerba.parcelasPagas ")
	List<PensionistaVerba> getPensionistaVerbasPagasByFolhaPagamentoId(
			@Param("folhaPagamentoId") Long folhaPagamentoId);

	@Query("select new com.rhlinkcon.payload.batimentoFolhaPagamento.BatimentoVerbaDto(sum(fv.valor), fv.verba) "
			+ " from Lancamento fv " + " left join Verba v on (v.id = fv.verba.id) "
			+ " left join Contracheque fcc on (fv.contracheque.id = fcc.id) "
			+ " left join Funcionario fu on (fcc.funcionario.id = fu.id) "
			+ " left join FolhaPagamento f on (fcc.folhaPagamento.id = f.id) "
			+ " left join FolhaCompetencia fc on (f.folhaCompetencia.id = fc.id) "
			+ " where fc.id = :competenciaId and f.tipoProcessamento.id = :tipoProcessamentoId and v.tipoVerba = :tipo "
			+ " and (:filialId is null or f.filial.id = :filialId) "
			+ " and (:situacaoId is null or fu.tipoSituacaoFuncional.id = :situacaoId) "
			+ " and (:lotacaoId is null or fu.lotacao.id = :lotacaoId) " + " group by fv.verba ")
	List<BatimentoVerbaDto> getBatimentoVerbaByFiltros(@Param("competenciaId") Long competenciaId,
			@Param("tipoProcessamentoId") Long tipoProcessamentoId, @Param("filialId") Long filialId,
			@Param("tipo") TipoVerba tipo, @Param("situacaoId") Long situacaoId, @Param("lotacaoId") Long lotacaoId);

	@Query("SELECT new com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento.RelatorioFolhaPagamentoResumoProventosDto("
			+ "COUNT(fv), " + "SUM(fv.valor), " + "fv.verba, " + "fv.contracheque.folhaPagamento.filial.id) "
			+ "FROM Lancamento fv " + "WHERE fv.contracheque.folhaPagamento.id = :folhaPagamentoId "
			+ "AND fv.verba.tipoVerba != 'OUTROS'"
			+ "AND (:identificacaoVerba IS NULL AND fv.verba.identificadorVerba = NULL) OR (fv.verba.identificadorVerba = :identificacaoVerba)"
			+ "AND (:situacaoFuncional IS NULL OR fv.contracheque.tipoSituacaoFuncional = :situacaoFuncional) "
			+ "GROUP BY fv.verba, fv.contracheque.folhaPagamento.filial.id ")
	List<RelatorioFolhaPagamentoResumoProventosDto> listContrachequesByVerba(
			@Param("folhaPagamentoId") Long folhaPagamentoId, @Param("situacaoFuncional") String situacaoFuncional,
			@Param("identificacaoVerba") IdentificacaoVerbaEnum identificacaoVerba);

}
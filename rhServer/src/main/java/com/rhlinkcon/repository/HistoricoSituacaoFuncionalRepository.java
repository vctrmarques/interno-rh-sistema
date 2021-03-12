package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.HistoricoSituacaoFuncional;
import com.rhlinkcon.model.TipoHistoricoSituacaoFuncionalEnum;
import com.rhlinkcon.payload.historicoSituacaoFuncional.ProjectionSituacaoFuncional;

@Repository
public interface HistoricoSituacaoFuncionalRepository extends JpaRepository<HistoricoSituacaoFuncional, Long> {

	@Query("select f.id as id, " + "f.nome as nome, " + "f.matricula as matricula, " + "fu.nome as funcao, "
			+ "s.tipoHistoricoSituacaoFuncional as situacao " + "from Funcionario f "
			+ "left join  HistoricoSituacaoFuncional s  on (f.id=s.funcionario.id) "
			+ "left join Funcao fu on fu.id = s.funcao.id  "
			+ "where s.id=(select max(sf.id) from HistoricoSituacaoFuncional sf " + " where sf.funcionario.id=f.id) "
			+ " or s.id is null")
	Page<ProjectionSituacaoFuncional> findFuncionariosSituracao(Pageable pageable);

	@Query("select f.id as id, " + "f.nome as nome, " + "f.matricula as matricula, " + "fu.nome as funcao, "
			+ "s.tipoHistoricoSituacaoFuncional as situacao " + "from Funcionario f "
			+ "left join  HistoricoSituacaoFuncional s  on (f.id=s.funcionario.id) "
			+ "left join Funcao fu on fu.id = s.funcao.id  "
			+ "where s.id=(select max(sf.id) from HistoricoSituacaoFuncional sf " + " where sf.funcionario.id=f.id)  "
			+ " or s.id is null and f.nome like %:nome% ")
	Page<ProjectionSituacaoFuncional> findFuncionariosSituracaoAndFuncionarioNome(Pageable pageable,
			@Param("nome") String nome);

	List<HistoricoSituacaoFuncional> findByFuncionarioId(Long funcionarioId);

	@Query("select min(h.id) from HistoricoSituacaoFuncional h where h.funcionario.id = :funcionarioId")
	Long getPrimeiroHistoricoSituacaoFuncionalIdByFuncionario(@Param("funcionarioId") Long funcionarioId);

	void deleteFindByFuncionarioId(Long funcionarioId);

	Page<HistoricoSituacaoFuncional> getAllSituacoesByFuncionarioIdAndTipoHistoricoSituacaoFuncional(Long funcionarioId,
			TipoHistoricoSituacaoFuncionalEnum tipoSituacaoFuncional, Pageable pageable);

	List<HistoricoSituacaoFuncional> getAllSituacoesByFuncionarioIdAndTipoHistoricoSituacaoFuncional(Long funcionarioId,
			TipoHistoricoSituacaoFuncionalEnum tipoSituacaoFuncional);

	HistoricoSituacaoFuncional findTopByFuncionarioIdAndTipoHistoricoSituacaoFuncionalOrderByIdDesc(Long funcionarioId,
			TipoHistoricoSituacaoFuncionalEnum exoneracao);
}

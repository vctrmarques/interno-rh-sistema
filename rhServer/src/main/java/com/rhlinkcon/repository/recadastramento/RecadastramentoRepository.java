package com.rhlinkcon.repository.recadastramento;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.Recadastramento;

@Repository
public interface RecadastramentoRepository
		extends JpaRepository<Recadastramento, Long>, RecadastramentoRepositoryCustom {

	Recadastramento findAllByAnexosId(Long id);

	@Transactional
	@Query("update Recadastramento r SET r.status = false where r.funcionario.id = :funcionarioId or r.pensao.id = :pensaoId")
	void inativarAnteriores(@Param("funcionarioId") Long funcionarioId, @Param("pensaoId") Long pensaoId);

//	@Query("select r as recadastramento, f as funcionario, p as pensao "
//			+ " from Recadastramento r "
//			+ " full join Funcionario f on (f.id = r.funcionario.id) "
//			+ " full join Pensionista p on (p.id = r.pensao.id) "
//			+ " where "
//			+ " (((:#{#filtro.descricao} is null or (f.nome like %:#{#filtro.descricao}%) or (cast(f.matricula as string) like %:#{#filtro.descricao}%)) and "
//			+ " (f.tipoSituacaoFuncional.id in (select s.id from SituacaoFuncional s where s.descricao like 'APOSENTADO' or s.descricao like 'APOSENTADA') and "
//			+ " f.id not in (select pp.funcionario.id from Pensionista pp where pp.status = true)) and "
//			+ " (:#{#filtro.funfin} is true or (f.id not in (select ff.id from Funcionario ff where ff.dataNascimento > :#{#filtro.dataNascido} and ff.dataAdmissao <= :#{#filtro.dataAdmitido}))) and "
//			+ " (:#{#filtro.funprev} is true or (f.id in (select ffp.id from Funcionario ffp where ffp.dataNascimento > :#{#filtro.dataNascido} and ffp.dataAdmissao <= :#{#filtro.dataAdmitido}))) and "
//			+ " (:#{#filtro.aposentado} is true or f.id = 0)) "
//			+ " or "
//			+ " ((p.status = true) and (:#{#filtro.descricao} is null or (p.nome like %:#{#filtro.descricao}%) or (cast(p.matricula as string) like %:#{#filtro.descricao}%)) and "
//			+ " (:#{#filtro.funfin} is true or (p.funcionario.id not in (select pff.id from Funcionario pff where pff.dataNascimento > :#{#filtro.dataNascido} and pff.dataAdmissao <= :#{#filtro.dataAdmitido}))) and "
//			+ " (:#{#filtro.funprev} is true or (p.funcionario.id in (select ppff.id from Funcionario ppff where ppff.dataNascimento > :#{#filtro.dataNascido} and ppff.dataAdmissao <= :#{#filtro.dataAdmitido}))) and "
//			+ " (:#{#filtro.pensionista} is true or p.id = 0))) "
//			+ " and "
//			+ " (r.id is null or r.status = true)")
//	Page<ProjectionRecadastramento> findByFiltros(@Param("filtro") RecadastramentoFiltro filtro, Pageable pageable);

	@Query("select f from Funcionario f where :descricao is null or f.nome like %:descricao% order by f.nome ")
	Page<Funcionario> findByFiltrosFuncionario(@Param("descricao") String descricao, Pageable pageable);

	@Query("select p from Pensionista p where :descricao is null or p.nome like %:descricao% order by p.nome ")
	Page<Pensionista> findByFiltrosPensionista(@Param("descricao") String descricao, Pageable pageable);

	Page<Recadastramento> findAllByFuncionarioIdAndData(Long id, LocalDate dt, Pageable pageable);

	Page<Recadastramento> findAllByFuncionarioId(Long id, Pageable pageable);

	Recadastramento findTopByFuncionarioIdAndStatusOrderByDataDesc(Long id, boolean b);

	Recadastramento findTopByPensaoIdAndStatusOrderByDataDesc(Long id, boolean b);

}

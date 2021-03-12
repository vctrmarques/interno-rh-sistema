package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Frequencia;
import com.rhlinkcon.payload.frequencia.ProjectionFrequencia;

@Repository
public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {
	@Query("select f from Frequencia f  " + "where month(f.data) = month(GETDATE()) "
			+ "and year(f.data) = year(GETDATE()) " + "and f.funcionario.id = :funcionarioId ")
	List<Frequencia> findAllByEntradaUmTodayAndFuncionarioId(@Param("funcionarioId") Long funcionarioId);

	@Query("select case when count(f.data) > 0 then true else false end from Frequencia f "
			+ " where month(f.data) = month(GETDATE()) " + "and year(f.data) = year(GETDATE()) "
			+ "and day(f.data) = day(GETDATE()) " + "and f.funcionario.id = :funcionarioId")
	Boolean existsFrequenciaHoje(@Param("funcionarioId") Long funcionarioId);

	List<Frequencia> findAllByFuncionarioId(Long funcionarioId);

	@Query(value = "select f.funcionario_id as funcionarioId, " + "fu.nome as nome, " + "month(f.data) as mes, "
			+ "year(f.data) as ano, "
			+ "sum(DATEDIFF(ss,'00:00:00',convert(varchar(10), f.horas_trabalhadas, 108))) as cargaHoraria, "
			+ "sum(DATEDIFF(ss,'00:00:00',convert(varchar(10), f.hora_extra, 108))) as horaExtra   "
			+ "from Frequencia f join Funcionario fu " + "on fu.id = f.funcionario_id " + "where year(f.data) = :ano "
			+ "and f.funcionario_id = :funcionarioId "
			+ "group by f.funcionario_id,fu.nome, month(f.data), year(f.data)", nativeQuery = true)
	List<ProjectionFrequencia> findFrequenciaByAnoAndFuncionarioId(@Param("ano") Integer ano,
			@Param("funcionarioId") Long funcionarioId);

	@Query("select f from Frequencia f where f.funcionario.id = :funcionarioId and year(f.data)=:ano")
	List<Frequencia> findAllByFuncionarioIdAndAno(@Param("funcionarioId") Long funcionarioId,
			@Param("ano") Integer ano);

	@Query("select f from Frequencia f where f.funcionario.id = :funcionarioId and year(f.data)=:ano and month(f.data)=:mes ")
	List<Frequencia> findAllByFuncionarioIdAndAnoAndMes(@Param("funcionarioId") Long funcionarioId,
			@Param("ano") Integer ano, @Param("mes") Integer mes);
}

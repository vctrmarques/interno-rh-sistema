package com.rhlinkcon.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.FeriasProgramacao;

@Repository
public interface FeriasProgramacaoRepository extends JpaRepository<FeriasProgramacao, Long> {
	
	List<FeriasProgramacao> findTop10ByFuncionarioIdOrderByIdDesc(Long funcionarioId);
	
	List<FeriasProgramacao> findByFuncionarioId(Long funcionarioId);
	
	@Query(value="SELECT * FROM ferias_programacao fp WHERE fp.exercicio_inicio = :dataExercicioInicio AND fp.situacao = 'EM_ABERTO'", nativeQuery = true)
	List<FeriasProgramacao> findByExercicioInicio(@Param("dataExercicioInicio") Date dataExercicioInicio);
	
	@Query(value="SELECT MAX(exercicio_inicio) FROM ferias_programacao fp WHERE fp.funcionario_id = :funcionarioId", nativeQuery = true)
	Date findByMaxExercicioInicialByFuncionarioId(@Param("funcionarioId") Long funcionarioId);	
	
	@Query("SELECT fp FROM FeriasProgramacao fp WHERE fp.funcionario.id = :funcionarioId AND fp.exercicioInicio = :dataExercicioInicio AND fp.situacao = 'EM_ABERTO'")
	FeriasProgramacao findByExercicioInicioByFuncionario(@Param("funcionarioId") Long funcionarioId, @Param("dataExercicioInicio") Date dataExercicioInicio);	

	@Query("SELECT fp FROM FeriasProgramacao fp WHERE fp.funcionario.id = :funcionarioId AND fp.situacao = 'EM_ABERTO'")
	FeriasProgramacao findByFuncionarioIdAndSituacao(@Param("funcionarioId") Long funcionarioId);
	
}

package com.rhlinkcon.repository.tranferenciaFuncionario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.TransferenciaFuncionario;

@Repository
public interface TransferenciaFuncionarioRepository extends JpaRepository<TransferenciaFuncionario, Long> {

	@Query("select tf from TransferenciaFuncionario tf where tf.funcionario.id = :funcionarioId order by tf.id desc ")
	List<TransferenciaFuncionario> getAllTransferenciaByFuncionario(@Param("funcionarioId") Long funcionarioId);
}

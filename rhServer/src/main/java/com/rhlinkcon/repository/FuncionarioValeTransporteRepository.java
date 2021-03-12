package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.FuncionarioValeTransporte;

@Repository
public interface FuncionarioValeTransporteRepository extends JpaRepository<FuncionarioValeTransporte, Long> {

	List<FuncionarioValeTransporte> findByFuncionarioId(Long funcionarioId);
	
	@Transactional
	void deleteByFuncionarioId(Long funcionarioId);
	
	@Query("SELECT vt.id FROM FuncionarioValeTransporte vt WHERE vt.funcionario.id =:funcionarioId")
	List<Long> findIdsByFuncionarioId(@Param("funcionarioId")Long funcionarioId);

}

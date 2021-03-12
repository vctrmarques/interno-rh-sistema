package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.TempoServico;

@Repository
public interface TempoServicoRepository extends JpaRepository<TempoServico, Long> {

	List<TempoServico> findByFuncionarioId(Long funcionarioId);
}

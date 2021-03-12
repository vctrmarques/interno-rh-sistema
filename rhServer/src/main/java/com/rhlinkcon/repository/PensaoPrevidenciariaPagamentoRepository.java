package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.PensaoPrevidenciariaPagamento;

@Repository
public interface PensaoPrevidenciariaPagamentoRepository extends JpaRepository<PensaoPrevidenciariaPagamento, Long>{


}

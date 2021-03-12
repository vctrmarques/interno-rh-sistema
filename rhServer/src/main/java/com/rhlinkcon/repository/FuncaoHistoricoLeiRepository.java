package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.FuncaoHistoricoLei;

@Repository
public interface FuncaoHistoricoLeiRepository extends JpaRepository<FuncaoHistoricoLei, Long> {

}

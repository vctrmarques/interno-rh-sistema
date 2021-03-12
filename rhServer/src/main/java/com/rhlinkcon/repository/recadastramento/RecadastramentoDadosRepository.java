package com.rhlinkcon.repository.recadastramento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.RecadastramentoDados;

@Repository
public interface RecadastramentoDadosRepository extends JpaRepository<RecadastramentoDados, Long>{

}

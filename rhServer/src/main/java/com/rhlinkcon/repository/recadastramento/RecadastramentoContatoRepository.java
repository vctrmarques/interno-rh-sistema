package com.rhlinkcon.repository.recadastramento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.RecadastramentoContato;

@Repository
public interface RecadastramentoContatoRepository extends JpaRepository<RecadastramentoContato, Long>{

}

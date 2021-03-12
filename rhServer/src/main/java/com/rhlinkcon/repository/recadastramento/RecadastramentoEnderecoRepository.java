package com.rhlinkcon.repository.recadastramento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.RecadastramentoEndereco;

@Repository
public interface RecadastramentoEnderecoRepository extends JpaRepository<RecadastramentoEndereco, Long>{

}

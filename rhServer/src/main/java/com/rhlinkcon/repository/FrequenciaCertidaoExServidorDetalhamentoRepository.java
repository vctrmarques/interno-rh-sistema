package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.FrequenciaCertidaoExServidorDetalhamento;

@Repository
public interface FrequenciaCertidaoExServidorDetalhamentoRepository extends JpaRepository<FrequenciaCertidaoExServidorDetalhamento,Long>{

	void deleteByCertidaoExSeguradoId(Long id);

}

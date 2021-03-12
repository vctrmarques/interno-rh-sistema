package com.rhlinkcon.repository.legislacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.legislacao.EnteFederado;

@Repository
public interface EnteFederadoRepository extends JpaRepository<EnteFederado, Long> {

	List<EnteFederado> findByDescricaoIgnoreCaseContaining(String descricao);
}

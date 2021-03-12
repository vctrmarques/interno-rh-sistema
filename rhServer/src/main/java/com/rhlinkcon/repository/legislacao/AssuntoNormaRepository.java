package com.rhlinkcon.repository.legislacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.legislacao.AssuntoNorma;

@Repository
public interface AssuntoNormaRepository extends JpaRepository<AssuntoNorma, Long> {

	List<AssuntoNorma> findByDescricaoIgnoreCaseContaining(String descricao);

}

package com.rhlinkcon.repository.legislacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.legislacao.UnidadeGestora;

@Repository
public interface UnidadeGestoraRepository extends JpaRepository<UnidadeGestora, Long> {

	List<UnidadeGestora> findByDescricaoIgnoreCaseContaining(String descricao);

}

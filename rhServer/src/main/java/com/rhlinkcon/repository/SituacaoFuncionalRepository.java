package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.SituacaoFuncional;
import com.rhlinkcon.model.TipoAfastamentoEnum;

@Repository
public interface SituacaoFuncionalRepository extends JpaRepository<SituacaoFuncional, Long> {

	Page<SituacaoFuncional> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	List<SituacaoFuncional> findByDescricaoIgnoreCaseContaining(String descricao);

	List<SituacaoFuncional> findByDescricaoIgnoreCaseContainingAndEntraFolha(String descricao, boolean entraFolha);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

	List<SituacaoFuncional> findByTipo(TipoAfastamentoEnum tipo);

	List<SituacaoFuncional> findByIdIn(List<Long> id);

	List<SituacaoFuncional> findByEntraFolha(boolean entraFolha);

}

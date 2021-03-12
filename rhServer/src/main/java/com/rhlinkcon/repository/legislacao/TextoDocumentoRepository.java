package com.rhlinkcon.repository.legislacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.legislacao.TextoDocumento;

@Repository
public interface TextoDocumentoRepository extends JpaRepository<TextoDocumento, Long> {

	List<TextoDocumento> findByDescricaoIgnoreCaseContaining(String descricao);

	Boolean existsByCodigo(Integer codigo);

	Boolean existsByCodigoAndIdNot(Integer codigo, Long id);
}
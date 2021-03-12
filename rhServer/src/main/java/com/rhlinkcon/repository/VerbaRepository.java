package com.rhlinkcon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.DestinacaoExterna;
import com.rhlinkcon.model.FaixaEnum;
import com.rhlinkcon.model.Verba;

@Repository
public interface VerbaRepository extends JpaRepository<Verba, Long> {

	Page<Verba> findByDescricaoVerbaIgnoreCaseContaining(String descricao, Pageable pageable);

	Page<Verba> findByDescricaoVerbaIgnoreCaseContainingOrCodigoIgnoreCaseContaining(String descricao, String codigo,
			Pageable pageable);

	Boolean existsByCodigo(String codigo);

	Boolean existsByCodigoAndIdNot(String codigo, Long id);

	List<Verba> findAllByCategoriaProfissionaisId(Long id);

	Optional<List<Verba>> findAllByDestinacaoExterna(DestinacaoExterna destinacaoExterna);

	List<Verba> findByDescricaoVerbaIgnoreCaseContainingOrCodigoIgnoreCaseContaining(String descricaoVerba,
			String codigo);

	List<Verba> findByDescricaoVerbaIgnoreCaseContaining(String search);

	Optional<Verba> findByCodigo(String codigo);

	Optional<Verba> findByFaixaAliquota(FaixaEnum faixaEnum);

	Boolean existsByIncidenciasContaining(Verba verba);

	Boolean existsByVerbaAssociada(Verba verba);

}

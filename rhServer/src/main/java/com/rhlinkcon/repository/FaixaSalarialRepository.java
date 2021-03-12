package com.rhlinkcon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ClasseSalarial;
import com.rhlinkcon.model.FaixaSalarial;
import com.rhlinkcon.model.GrupoSalarial;

@Repository
public interface FaixaSalarialRepository extends JpaRepository<FaixaSalarial, Long> {

	Boolean existsByGrupoSalarialId(Long id);

	Optional<FaixaSalarial> findByGrupoSalarialAndClasseSalarial(GrupoSalarial grupoSalarial,
			ClasseSalarial classeSalarial);
	
	List<FaixaSalarial> findAllByGrupoSalarialId(Long grupoSalarialId);
}

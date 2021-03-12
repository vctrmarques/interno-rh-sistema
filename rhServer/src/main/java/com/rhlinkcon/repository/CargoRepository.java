package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.Cargo;
import com.rhlinkcon.model.Verba;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

	Boolean existsByNome(String nome);

	Boolean existsByNomeAndIdNot(String nome, Long id);

	Page<Cargo> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	List<Cargo> findByNomeIgnoreCaseContaining(String search);

	Boolean existsByVerbasContaining(Verba verba);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM cargo_atividade WHERE cargo_id = :idCargo", nativeQuery = true)
	void deleteCargoAtividade(@Param("idCargo") Long idCargo);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM cargo_habilidade WHERE cargo_id = :idCargo", nativeQuery = true)
	void deleteCargoHabilidade(@Param("idCargo") Long idCargo);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM cargo_curso WHERE cargo_id = :idCargo", nativeQuery = true)
	void deleteCargoCurso(@Param("idCargo") Long idCargo);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM cargo_vinculo WHERE cargo_id = :idCargo", nativeQuery = true)
	void deleteCargoVinculo(@Param("idCargo") Long idCargo);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM lotacao_cargo WHERE cargo_id = :idCargo", nativeQuery = true)
	void deleteLotacaoCargo(@Param("idCargo") Long idCargo);

}

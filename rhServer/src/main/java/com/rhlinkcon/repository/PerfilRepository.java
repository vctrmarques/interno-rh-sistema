package com.rhlinkcon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

	Page<Perfil> findByAtivoAndNomeIgnoreCaseContaining(boolean ativo, String nome, Pageable pageable);

	Optional<List<Perfil>> findByAtivoAndNomeIgnoreCaseContaining(boolean ativo, String nome);

	Optional<List<Perfil>> findByAtivo(boolean ativo);

	Page<Perfil> findByAtivo(boolean ativo, Pageable pageable);

	Boolean existsByNomeAndAtivo(String nome, boolean ativo);

	Boolean existsByNomeAndAtivoAndIdNot(String nome, boolean ativo, Long id);

	Optional<List<Perfil>> findByUsuariosIdAndAtivo(Long usuarioId, boolean ativo);

}

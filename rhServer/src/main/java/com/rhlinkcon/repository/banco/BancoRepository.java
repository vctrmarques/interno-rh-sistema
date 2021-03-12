package com.rhlinkcon.repository.banco;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Banco;

@Repository
public interface BancoRepository extends JpaRepository<Banco, Long>, BancoRepositoryCustom {

	Boolean existsByCodigo(String codigo);

	Boolean existsByNome(String nome);

	Boolean existsByCodigoAndIdNot(String codigo, Long id);

	Boolean existsByNomeAndIdNot(String nome, Long id);

	List<Banco> findByNomeIgnoreCaseContaining(String search);

	Banco findByCodigo(String search);

	List<Banco> findByNomeIgnoreCaseContainingOrCodigoIgnoreCaseContaining(String nome, String codigo);

}

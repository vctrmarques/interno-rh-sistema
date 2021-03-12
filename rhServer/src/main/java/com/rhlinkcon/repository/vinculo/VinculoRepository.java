package com.rhlinkcon.repository.vinculo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Verba;
import com.rhlinkcon.model.Vinculo;

@Repository
public interface VinculoRepository extends JpaRepository<Vinculo, Long> {

	List<Vinculo> findByDescricaoIgnoreCaseContaining(String descricao);

	Vinculo findByDescricao(String descricao);

//	Boolean existsByCodigo(Integer codigo);
//
//	Boolean existsByNome(String nome);
//
//	Boolean existsByCodigoAndIdNot(Integer codigo, Long id);
//
//	Boolean existsByNomeAndIdNot(String nome, Long id);

	Boolean existsByVerbasContaining(Verba verba);

}

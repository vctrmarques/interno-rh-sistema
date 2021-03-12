package com.rhlinkcon.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Menu;
import com.rhlinkcon.model.Papel;
import com.rhlinkcon.model.PapelNomeEnum;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {
	Optional<Papel> findByNome(PapelNomeEnum papelNome);

	Optional<List<Papel>> findByMenu(Menu menu);

	@Query(value = " SELECT p.id FROM menu m, usuario_papel up, papel p "
			+ " where up.usuario_id = :usuarioLogadoId and up.papel_id = p.id and p.id_menu = m.id "
			+ " and m.ativo = 1 ", nativeQuery = true)

	List<BigInteger> listPapelIdsUsuarioLogado(@Param("usuarioLogadoId") Long usuarioLogadoId);

	@Query(value = " SELECT p.id FROM menu m, usuario_papel up, papel p "
			+ " where up.usuario_id = :usuarioLogadoId and up.papel_id = p.id and p.id_menu = m.id "
			+ " and m.ativo = 1 and m.id = :menuID", nativeQuery = true)

	List<BigInteger> listPapelIdsUsuarioLogadoMenu(@Param("usuarioLogadoId") Long usuarioLogadoId,
			@Param("menuID") Long menuID);

	List<Papel> findByIdIn(List<Long> papelIds);
}

package com.rhlinkcon.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Menu;
import com.rhlinkcon.model.MenuCategoriaEnum;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	Optional<List<Menu>> findByAtivoAndCategoriaOrderByNomeAsc(boolean ativo, MenuCategoriaEnum categoria);

	List<Menu> findByAtivoOrderByNomeAsc(boolean ativo);

	List<Menu> findByAtivo(boolean ativo);

	@Query(value = "SELECT m FROM Menu m where m.ativo = 1 and m.nome LIKE %:nomeSearch%")
	List<Menu> findByNomeIgnoreCaseContainingAndAtivo(@Param("nomeSearch") String nomeSearch);

	Optional<Menu> findByNome(String nome);

	@Query(value = " SELECT m.id FROM menu m, usuario_papel up, papel p "
			+ " where up.usuario_id = :usuarioLogadoId and up.papel_id = p.id and p.id_menu = m.id "
			+ " and m.ativo = 1 and m.categoria = :categoria UNION "
			+ "	SELECT m.id FROM menu m WHERE m.ativo = 1 AND m.id NOT IN ( SELECT id_menu FROM papel WHERE id_menu IS NOT NULL ) and m.categoria = :categoria", nativeQuery = true)

	List<BigInteger> listMenuIdsUsuarioLogadoCategoria(@Param("usuarioLogadoId") Long usuarioLogadoId,
			@Param("categoria") String categoria);

	@Query(value = " SELECT m.id FROM menu m, usuario_papel up, papel p "
			+ " where up.usuario_id = :usuarioLogadoId and up.papel_id = p.id and p.id_menu = m.id "
			+ " and m.ativo = 1 and m.nome LIKE %:nomeSearch% UNION "
			+ "	SELECT m.id FROM menu m WHERE m.ativo = 1 AND m.id NOT IN ( SELECT id_menu FROM papel WHERE id_menu IS NOT NULL ) and m.nome LIKE %:nomeSearch%", nativeQuery = true)

	List<BigInteger> listMenuIdsUsuarioLogadoAndNomeSearch(@Param("usuarioLogadoId") Long usuarioLogadoId,
			@Param("nomeSearch") String nomeSearch);

	List<Menu> findByIdIn(List<Long> menuIds);

}

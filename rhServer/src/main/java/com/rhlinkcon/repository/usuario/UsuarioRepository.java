package com.rhlinkcon.repository.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Papel;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryCustom {

	@Query("SELECT u.anexo.fileDownloadUri FROM Usuario u WHERE u.id = :id and u.anexo IS NOT NULL")
	String getFileDownloadUriByLoggedUser(@Param("id") Long id);

	List<Usuario> findByIdIn(List<Long> userIds);

	@Query("SELECT u.papeis FROM Usuario u WHERE u.id = :id")
	List<Papel> findPapeisById(@Param("id") Long id);

	Optional<Usuario> findByLogin(String login);

	Boolean existsByLogin(String login);

	Boolean existsByEmail(String email);

	Boolean existsByLoginAndIdNot(String login, Long id);

	Boolean existsByEmailAndIdNot(String email, Long id);

	@Query("SELECT u FROM Usuario u WHERE u.id NOT IN :userIds ") // AND u.importadoLdap = true
	List<Usuario> findByIdInNotAndImportadoLdap(@Param("userIds") List<Long> userIds);

	@Query("SELECT nome FROM Usuario WHERE id = :id ")
	String findNomeById(@Param("id") Long id);

	@Query("select id from Usuario where nome like %:nome%")
	Optional<List<Long>> findUsuarioIdsByNome(@Param("nome") String nome);
	
	Usuario findByEmail(String email);

	@Query("SELECT new com.rhlinkcon.payload.DadoBasicoDto(u.id, u.nome) "
		 + "FROM Usuario u WHERE u.nome LIKE %:nome%")
	List<DadoBasicoDto> getDadoBasicoList(@Param("nome") String nome);

}

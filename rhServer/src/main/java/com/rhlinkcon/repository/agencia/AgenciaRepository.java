package com.rhlinkcon.repository.agencia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Agencia;
import com.rhlinkcon.model.Banco;
import com.rhlinkcon.payload.DadoBasicoDto;

@Repository
public interface AgenciaRepository extends JpaRepository<Agencia, Long>, AgenciaRepositoryCustom {

	Boolean existsByNome(String nome);

	Boolean existsByNomeAndIdNot(String nome, Long id);

	Boolean existsByNumeroAndBanco(Integer numero, Banco banco);

	List<Agencia> findByNumeroAndBancoId(Integer search, Long bancoId);

	Agencia findFirstByNumeroAndBancoId(Integer valor, Long bancoId);

	@Query("SELECT new com.rhlinkcon.payload.DadoBasicoDto(m.id, m.nome) "
			+ "FROM Agencia m WHERE m.numero = :numero AND m.banco.id = :ufId")
	// + "FROM Agencia m WHERE m.nome LIKE %:nome% AND m.uf.id = :ufId")
	List<DadoBasicoDto> getDadoBasicoListAndUfId(@Param("numero") Integer numero, @Param("ufId") Long ufId);

	@Query("SELECT new com.rhlinkcon.payload.DadoBasicoDto(m.id, m.nome) " + "FROM Agencia m WHERE m.nome LIKE %:nome%")
	List<DadoBasicoDto> getDadoBasicoList(@Param("nome") String nome);

}

package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.payload.DadoBasicoDto;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {

	Page<Municipio> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);

	Municipio findByDescricaoAndUfId(String descricao, Long ufId);

	Boolean existsByDescricao(String descricao);

	Boolean existsByDescricaoAndIdNot(String descricao, Long id);

	List<Municipio> findByUfId(Long ufId);

	List<Municipio> findByNaturalidade(String naturalidade);

	@Query("SELECT new com.rhlinkcon.payload.DadoBasicoDto(m.id, m.descricao) "
			+ "FROM Municipio m WHERE m.descricao LIKE %:descricao% AND m.uf.id = :ufId")
	List<DadoBasicoDto> getDadoBasicoListAndUfId(@Param("descricao") String descricao, @Param("ufId") Long ufId);

	@Query("SELECT new com.rhlinkcon.payload.DadoBasicoDto(m.id, m.descricao) "
			+ "FROM Municipio m WHERE m.descricao LIKE %:descricao%")
	List<DadoBasicoDto> getDadoBasicoList(@Param("descricao") String descricao);

}

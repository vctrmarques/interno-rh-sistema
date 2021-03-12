package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Nacionalidade;
import com.rhlinkcon.payload.DadoBasicoDto;

@Repository
public interface NacionalidadeRepository extends JpaRepository<Nacionalidade, Long> {

	Page<Nacionalidade> findBySiglaIgnoreCaseContaining(String descricao, Pageable pageable);

	Nacionalidade findBySiglaIgnoreCaseContaining(String descricao);

	Boolean existsBySigla(String sigla);

	Boolean existsBySiglaAndIdNot(String sigla, Long id);

	@Query("SELECT new com.rhlinkcon.payload.DadoBasicoDto(n.id, n.nacionalidadeFeminina) FROM Nacionalidade n WHERE n.nacionalidadeFeminina LIKE %:nacionalidadeFeminina%")
	List<DadoBasicoDto> getDadoBasicoListByNacionalidadeFeminina(
			@Param("nacionalidadeFeminina") String nacionalidadeFeminina);

	@Query("SELECT new com.rhlinkcon.payload.DadoBasicoDto(n.id, n.nacionalidadeFeminina) FROM Nacionalidade n")
	List<DadoBasicoDto> getDadoBasicoList();

}

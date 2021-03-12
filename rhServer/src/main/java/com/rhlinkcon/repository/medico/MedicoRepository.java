package com.rhlinkcon.repository.medico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Medico;
import com.rhlinkcon.model.ProntuarioPericiaMedica;
import com.rhlinkcon.payload.DadoBasicoDto;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long>, MedicoRepositoryCustom {

	
	Boolean existsByCpf(String cpf);
	
	Boolean existsByMatricula(Integer integer);
	
	Boolean existsByCrm(Integer integer);
	
	@Query("SELECT new com.rhlinkcon.payload.DadoBasicoDto(m.id, m.nome) " + "FROM Medico m WHERE m.nome LIKE %:nome% AND m.status = 'true'")
	List<DadoBasicoDto> getDadoBasicoList(@Param("nome") String nome);
	
	Medico findByNome(@Param("nome") String nome);
	
	@Query("SELECT m FROM Medico m WHERE m.usuario.id = :id ")
	Medico getMedicoByUsarioId(@Param("id") Long id);
}

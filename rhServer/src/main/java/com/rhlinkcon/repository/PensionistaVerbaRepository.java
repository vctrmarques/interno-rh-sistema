package com.rhlinkcon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.PensionistaVerba;
import com.rhlinkcon.model.Verba;

@Repository
public interface PensionistaVerbaRepository extends JpaRepository<PensionistaVerba, Long> {

	List<PensionistaVerba> findAllByVerbaIdIn(List<Long> ids);

	@Query("select f from PensionistaVerba f where f.pensionista.id = :pensionistaId and f.ativo = true "
			+ "and (f.recorrencia = 'FIXA' or f.parcelasPagas < f.parcelas ) ")
	List<PensionistaVerba> findByPensionistaId(@Param("pensionistaId") Long pensionistaId);

	PensionistaVerba findByPensionistaAndVerba(Pensionista pensionista, Verba verba);

	Boolean existsByPensionistaAndVerbaAndAtivo(Pensionista pensionista, Verba verba, Boolean ativo);

	Boolean existsByVerba(Verba verba);

	Optional<List<PensionistaVerba>> findByPensionistaAndIdNotIn(Pensionista pensionista, List<Long> ids);

}

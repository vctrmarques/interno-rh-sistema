package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Turno;
import com.rhlinkcon.model.TurnoFolga;

@Repository
public interface TurnoFolgaRepository extends JpaRepository<TurnoFolga, Long> {

	List<TurnoFolga> findByTurno(Turno turno);
}
package com.rhlinkcon.repository.agendaMedica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.AgendaMedica;

@Repository
public interface AgendaMedicaRepository extends JpaRepository<AgendaMedica, Long>, AgendaMedicaRepositoryCustom {

}

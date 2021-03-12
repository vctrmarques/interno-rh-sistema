package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Anexo;

@Repository
public interface AnexoRepository extends JpaRepository<Anexo, Long> {
	List<Anexo> findAllByIdIn(List<Long> ids);
}

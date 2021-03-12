package com.rhlinkcon.repository.auditoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Auditoria;

@Repository
public interface AuditoriaRepository extends JpaRepository<Auditoria, Long>, AuditoriaRepositoryCustom {

}

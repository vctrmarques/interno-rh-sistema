package com.rhlinkcon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Telefone;

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

}

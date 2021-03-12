package com.rhlinkcon.repository.dirf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Dirf;

@Repository
public interface DirfRepository extends JpaRepository<Dirf, Long>, DirfRepositoryCustom {

}

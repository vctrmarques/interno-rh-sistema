package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.DependenteBeneficio;

@Repository
public interface DependenteBeneficioRepository extends JpaRepository<DependenteBeneficio, Long> {
	
	@Autowired
	List<DependenteBeneficio> findByDependenteId(Long dependenteId);
	
	@Autowired
	void deleteFindByDependenteId(Long dependenteId);

}

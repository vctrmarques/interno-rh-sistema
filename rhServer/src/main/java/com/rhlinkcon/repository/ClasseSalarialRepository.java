package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ClasseSalarial;

@Repository
public interface ClasseSalarialRepository extends JpaRepository<ClasseSalarial, Long>{
	
	@Query("SELECT nft FROM ClasseSalarial nft WHERE nft.grupoSalarial.id = :id")
	List<ClasseSalarial> getListClassesSalariaisIdByGrupoSalarialId(@Param("id") Long id);

}

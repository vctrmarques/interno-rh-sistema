package com.rhlinkcon.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rhlinkcon.model.DefinicaoOrganico;

@Repository
public interface DefinicaoOrganicoRepository extends JpaRepository<DefinicaoOrganico, Long> {

	@Autowired
	DefinicaoOrganico findByEmpresaFilialId(Long empresaFilialId);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM empresa_filial_lotacao WHERE empresa_filial_id = :empresa_filial_id", nativeQuery = true)
	void deleteEmpresaFilialLotacaoByEmpresaFilialId(@Param("empresa_filial_id")Long empresaFilialId);	

}

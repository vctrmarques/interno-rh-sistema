package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.PrestadorServico;
import com.rhlinkcon.model.Verba;

@Repository
public interface PrestadorServicoRepository extends JpaRepository<PrestadorServico, Long> {

	Page<PrestadorServico> findByNomeCivilIgnoreCaseContaining(String nomeCivil, Pageable pageable);
	
	Boolean existsByNomeCivil(String nomeCivil);
	
	Boolean existsByNomeCivilAndIdNot(String nomeCivil, Long id);
	
	Boolean existsByVerba(Verba verba);

}

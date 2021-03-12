package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.TomadorServico;

@Repository
public interface TomadorServicoRepository extends JpaRepository<TomadorServico, Long> {

	List<TomadorServico> findByRazaoSocialIgnoreCaseContaining(String razaoSocial);

	Page<TomadorServico> findByCnpjIgnoreCaseContaining(String cnpj, Pageable pageable);

	Boolean existsByCnpj(String cnpj);
	
	Boolean existsByCnpjAndIdNot(String cnpj, Long id);

}

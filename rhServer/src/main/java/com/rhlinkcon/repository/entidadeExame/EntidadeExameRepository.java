package com.rhlinkcon.repository.entidadeExame;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.EntidadeExame;

@Repository
public interface EntidadeExameRepository extends JpaRepository<EntidadeExame, Long> {

	Page<EntidadeExame> findByTomadorServicoRazaoSocialIgnoreCaseContaining(String razaoSocial, Pageable pageable);

	Boolean existsByTomadorServicoRazaoSocial(String razaoSocial);

	Boolean existsByTomadorServicoRazaoSocialAndIdNot(String razaoSocial, Long id);

}

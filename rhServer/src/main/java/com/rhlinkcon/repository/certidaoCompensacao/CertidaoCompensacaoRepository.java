package com.rhlinkcon.repository.certidaoCompensacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CertidaoCompensacao;

@Repository
public interface CertidaoCompensacaoRepository
		extends JpaRepository<CertidaoCompensacao, Long>, CertidaoCompensacaoRepositoryCustom {

	Page<CertidaoCompensacao> findByFuncionarioNomeIgnoreCaseContaining(String descricao, Pageable pageable);

	CertidaoCompensacao findAllByAnexosId(Long id);

	@Query("select max(numero) from CertidaoCompensacao d where d.ano = :ano ")
	Long maxNumeroByAno(@Param("ano") Integer ano);

	@Query("select max(numeroRetificacao) from CertidaoCompensacao d where d.ano = :ano and d.numero = :numero ")
	Long maxNumeroRetificacaoByAnoAndNumero(@Param("ano") Integer ano, @Param("numero") Long numero);

}

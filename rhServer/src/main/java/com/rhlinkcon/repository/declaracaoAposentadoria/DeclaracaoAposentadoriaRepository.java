package com.rhlinkcon.repository.declaracaoAposentadoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.DeclaracaoAposentadoria;

@Repository
public interface DeclaracaoAposentadoriaRepository
		extends JpaRepository<DeclaracaoAposentadoria, Long>, DeclaracaoAposentadoriaRepositoryCustom {

	Page<DeclaracaoAposentadoria> findByFuncionarioNomeIgnoreCaseContaining(String descricao, Pageable pageable);

	DeclaracaoAposentadoria findAllByAnexosId(Long id);

	@Query("select max(numero) from DeclaracaoAposentadoria d where d.ano = :ano ")
	Long maxNumeroByAno(@Param("ano") Integer ano);

	@Query("select max(numeroRetificacao) from DeclaracaoAposentadoria d where d.ano = :ano and d.numero = :numero ")
	Long maxNumeroRetificacaoByAnoAndNumero(@Param("ano") Integer ano, @Param("numero") Long numero);

}

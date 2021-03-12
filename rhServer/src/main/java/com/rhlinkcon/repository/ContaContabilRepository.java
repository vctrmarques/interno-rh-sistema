package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ContaContabil;
import com.rhlinkcon.model.TipoContaLotacaoEnum;
import com.rhlinkcon.model.Verba;

@Repository
public interface ContaContabilRepository extends JpaRepository<ContaContabil, Long> {

	Page<ContaContabil> findByCentroCustoDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);
	
	@Query("select c from ContaContabil c where CAST(c.conta as string) like %:conta% and c.tipoConta=:tipo")
	List<ContaContabil> findAllByTipoContaAndContaContaining(@Param("tipo") TipoContaLotacaoEnum tipo, @Param("conta") String conta);

	Boolean existsByVerba(Verba verba);
	
}

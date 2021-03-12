package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.CertidaoExSegurado;
import com.rhlinkcon.model.StatusSituacaoCertidaoExSeguradoEnum;

@Repository
public interface CertidaoExSeguradoRepository extends JpaRepository<CertidaoExSegurado, Long> {
	
	CertidaoExSegurado findAllByAnexosId(Long id);

	@Query("SELECT ces FROM CertidaoExSegurado ces where YEAR(ces.createdAt) = :ano order by ces.createdAt desc")
	List<CertidaoExSegurado> findByLastByAnoCertidaoOrderByCreatedAtDesc(@Param("ano") Integer ano);

	CertidaoExSegurado findByNumeroCertidao(Integer numero);

	@Query("select d from CertidaoExSegurado d where "
			+ " (:status is null or d.statusSituacaoCertidao = :status) and (:descricao is null or (d.funcionario.nome like %:descricao%) "
			+ " or (cast(d.funcionario.matricula as string) like %:descricao%) "
			+ " or (cast(d.funcionario.pisPasep as string) like %:descricao%)"
			+ " or (d.funcionario.cpf like %:descricao%))")
	Page<CertidaoExSegurado> getByFiltro(@Param("descricao") String descricao, @Param("status") StatusSituacaoCertidaoExSeguradoEnum status, Pageable pageable);

	@Query("select max(numeroCertidao) from CertidaoExSegurado c where c.anoCertidao = :ano ")
	Integer maxNumeroByAno(@Param("ano") Integer ano);
}
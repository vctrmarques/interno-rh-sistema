package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.NivelSalarialHistorico;
import com.rhlinkcon.model.NivelSalarialHistoricoOrigemAjusteEnum;
import com.rhlinkcon.model.ReferenciaSalarial;
import com.rhlinkcon.payload.referenciaSalarial.NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse;

@Repository
public interface NivelSalarialHistoricoRepository extends JpaRepository<NivelSalarialHistorico, Long> {

	List<NivelSalarialHistorico> findByNivelSalarial(ReferenciaSalarial nivelSalarial);

	Page<NivelSalarialHistorico> findByNivelSalarial(ReferenciaSalarial nivelSalarial, Pageable pageable);

	Page<NivelSalarialHistorico> findByNivelSalarialAndOrigemAjuste(ReferenciaSalarial nivelSalarial,
			NivelSalarialHistoricoOrigemAjusteEnum origemAjuste, Pageable pageable);

	Page<NivelSalarialHistorico> findByOrigemAjuste(NivelSalarialHistoricoOrigemAjusteEnum origemAjuste,
			Pageable pageable);

	@Query("SELECT NEW com.rhlinkcon.payload.referenciaSalarial.NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse("
			+ "nsh.nivelSalarial.id, nsh.nivelSalarial.descricao, nsh.nivelSalarial.codigo, count(nsh.nivelSalarial.id)"
			+ ") FROM NivelSalarialHistorico nsh WHERE nsh.nivelSalarial.descricao LIKE %:nivelSalarialDescricao% GROUP BY "
			+ "nsh.nivelSalarial.id, nsh.nivelSalarial.descricao, nsh.nivelSalarial.codigo ")
	Page<NivelSalarialHistoricoAgrupadosPorNivelSalarialResponse> getAllNivelSalarialHistoricosAgrupadosPorNivelSalarial(
			@Param("nivelSalarialDescricao") String nivelSalarialDescricao, Pageable pageable);

}
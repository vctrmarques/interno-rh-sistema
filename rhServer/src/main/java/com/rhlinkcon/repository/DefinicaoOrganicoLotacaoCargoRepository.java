package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.DefinicaoOrganicoLotacaoCargo;

@Repository
public interface DefinicaoOrganicoLotacaoCargoRepository extends JpaRepository<DefinicaoOrganicoLotacaoCargo, Long> {

	List<DefinicaoOrganicoLotacaoCargo>	findByEmpresaFilialIdAndLotacaoId(Long empresaFilialId, Long lotacaoId);
	
	void deleteByEmpresaFilialIdAndLotacaoIdAndCargoId(Long empresaFilialId, Long lotacaoId, Long cargoId);

}

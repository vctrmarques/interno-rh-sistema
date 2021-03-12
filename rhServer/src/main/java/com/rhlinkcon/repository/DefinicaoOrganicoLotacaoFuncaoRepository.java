package com.rhlinkcon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.DefinicaoOrganicoLotacaoFuncao;

@Repository
public interface DefinicaoOrganicoLotacaoFuncaoRepository extends JpaRepository<DefinicaoOrganicoLotacaoFuncao, Long> {

	List<DefinicaoOrganicoLotacaoFuncao> findByEmpresaFilialIdAndLotacaoId(Long empresaFilialId, Long lotacaoId);
	
	void deleteByEmpresaFilialIdAndLotacaoIdAndFuncaoId(Long empresaFilialId, Long lotacaoId, Long funcaoId);

}

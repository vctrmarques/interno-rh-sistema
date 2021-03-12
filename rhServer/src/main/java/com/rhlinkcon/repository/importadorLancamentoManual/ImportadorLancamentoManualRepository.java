
package com.rhlinkcon.repository.importadorLancamentoManual;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ImportadorLancamentoManual;

@Repository
public interface ImportadorLancamentoManualRepository extends JpaRepository<ImportadorLancamentoManual, Long> {

	Page<ImportadorLancamentoManual> findByFolhaPagamentoIdAndExcluido(Long folhaPagamentoId, boolean excluido,
			Pageable pageable);

}

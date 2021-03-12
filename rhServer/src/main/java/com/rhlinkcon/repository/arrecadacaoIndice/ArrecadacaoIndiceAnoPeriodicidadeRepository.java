package com.rhlinkcon.repository.arrecadacaoIndice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ArrecadacaoIndiceAnoPeriodicidade;
import com.rhlinkcon.model.PeriodicidadeMesEnum;
import com.rhlinkcon.service.ArrecadacaoIndiceAnoPeriodicidadeDto;

@Repository
public interface ArrecadacaoIndiceAnoPeriodicidadeRepository extends JpaRepository<ArrecadacaoIndiceAnoPeriodicidade, Long>{

	boolean existsByArrecadacaoIndiceId (Long id);
	
	List<ArrecadacaoIndiceAnoPeriodicidade> findAllByArrecadacaoIndiceId(Long id);
	
	ArrecadacaoIndiceAnoPeriodicidade findFirstByArrecadacaoIndiceIdAndPeriodicidadeMes(Long id, PeriodicidadeMesEnum valueEnumPeriodicidade);
	
	@Query("SELECT new com.rhlinkcon.service.ArrecadacaoIndiceAnoPeriodicidadeDto(m.ano) "
			+ " FROM ArrecadacaoIndiceAnoPeriodicidade m "
			+ " WHERE str(m.ano) LIKE %:ano% group by m.ano")
	List<ArrecadacaoIndiceAnoPeriodicidadeDto> getAnoList(@Param("ano") Long ano);

	ArrecadacaoIndiceAnoPeriodicidade findByAnoAndId(Long ano, Long id);
}

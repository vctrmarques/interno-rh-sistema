package com.rhlinkcon.repository.arrecadacaoIndice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ArrecadacaoIndiceAnoPeriodicidade;
import com.rhlinkcon.model.ArrecadacaoIndiceMes;
import com.rhlinkcon.model.MesEnum;

@Repository
public interface ArrecadacaoIndiceMesRepository extends JpaRepository<ArrecadacaoIndiceMes, Long>{

	boolean existsByArrecadacaoIndiceAnoPeriodicidadeIdAndMes (Long id, MesEnum valueEnumMes);

	ArrecadacaoIndiceMes findFirstByArrecadacaoIndiceAnoPeriodicidadeIdAndMes(Long id, MesEnum valueEnumMes);
	
	List<ArrecadacaoIndiceMes> findFirstByArrecadacaoIndiceAnoPeriodicidadeId (Long id);
	
}

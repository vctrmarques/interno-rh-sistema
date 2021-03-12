package com.rhlinkcon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ItemFormula;
import com.rhlinkcon.model.Verba;

@Repository
public interface ItemFormulaRepository extends JpaRepository<ItemFormula, Long> {

	Optional<List<ItemFormula>> findByVerbaAndIdNotIn(Verba verba, List<Long> ids);

	Boolean existsByRotina(Verba verba);
}

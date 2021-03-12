package com.rhlinkcon.repository.recisaoContrato;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.RecisaoContrato;

@Repository
public interface RecisaoContratoRepository extends JpaRepository<RecisaoContrato, Long>, RecisaoContratoRepositoryCustom {
	List<RecisaoContrato> findByFuncionario(Funcionario funcionario);
}

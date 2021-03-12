package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ExperienciaProfissional;

@Repository
public interface ExperienciaProfissionalRepository  extends JpaRepository<ExperienciaProfissional, Long> {
    Page<ExperienciaProfissional> findByFuncionario_NomeIgnoreCaseContaining(String nome, Pageable pageable);

    Boolean existsExperienciaProfissionalByFuncaoIdAndFuncionarioId(Long funcao, Long funcionario);

    Boolean existsExperienciaProfissionalByIdNotAndFuncaoIdAndFuncionarioId(Long id, Long funcao, Long funcionario);
}

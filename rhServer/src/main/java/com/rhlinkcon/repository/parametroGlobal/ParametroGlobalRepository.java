package com.rhlinkcon.repository.parametroGlobal;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ParametroGlobal;
import com.rhlinkcon.model.ParametroGlobalChaveEnum;

@Repository
public interface ParametroGlobalRepository extends JpaRepository<ParametroGlobal, Long> {

	List<ParametroGlobal> findByAtivo(boolean ativo);

	Optional<ParametroGlobal> findByChave(ParametroGlobalChaveEnum chave);

	Optional<ParametroGlobal> findByChaveAndAtivo(ParametroGlobalChaveEnum chave, boolean ativo);

}

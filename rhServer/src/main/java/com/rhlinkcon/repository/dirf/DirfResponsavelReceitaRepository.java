package com.rhlinkcon.repository.dirf;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rhlinkcon.model.DirfResponsavelReceita;

public interface DirfResponsavelReceitaRepository extends JpaRepository<DirfResponsavelReceita, Long>{

	List<DirfResponsavelReceita> findByCpf(String search);

}

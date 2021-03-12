package com.rhlinkcon.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rhlinkcon.model.Notificacao;
import com.rhlinkcon.model.Usuario;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long>{
	
	Page<Notificacao> findByDestinatario(Usuario idEntidade, Pageable pageable);
	
	Long countByDestinatarioAndVisualizada(Usuario idEntidade, Boolean visualizada);

}

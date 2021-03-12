package com.rhlinkcon.repository.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rhlinkcon.model.Usuario;

public interface UsuarioRepositoryCustom {
	Page<Usuario> filtro(String nome, String cpf, String login, String idFilialFiltro, Pageable pageable);
}

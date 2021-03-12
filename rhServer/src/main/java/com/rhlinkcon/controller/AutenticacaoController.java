package com.rhlinkcon.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.generico.JwtAuthenticationResponse;
import com.rhlinkcon.payload.generico.LoginRequest;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.security.JwtTokenProvider;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	JwtTokenProvider tokenProvider;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		ResponseEntity<?> retorno = null;
		Usuario user  = null;


		user = usuarioRepository.findByLogin(loginRequest.getUsernameOrEmail())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "login", loginRequest.getUsernameOrEmail()));

		if(user.getAtivo()) {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsernameOrEmail(),
							loginRequest.getPassword()
							)
					);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = tokenProvider.generateToken(authentication);
			retorno = ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
		} else {
			retorno = Utils.badRequest(false, "Usuário inativo ou sem permissão de acesso.");
		}
		return retorno;

	}

}
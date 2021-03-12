package com.rhlinkcon.security;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.repository.usuario.UsuarioRepository;

import java.util.Date;

@Component
public class JwtTokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${app.jwtSecret}")
	private String jwtSecret;

	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	@Autowired
	UsuarioRepository userRepository;

	public String generateToken(Authentication authentication) {

		String idUser = null;
		try {
			UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
			idUser = Long.toString(userPrincipal.getId());
		} catch (Exception e) {
			UserPrincipalLdap userPrincipalLdap = (UserPrincipalLdap) authentication.getPrincipal();

			Usuario user = userRepository.findByLogin(userPrincipalLdap.getUsername())
					.orElseThrow(() -> 
					new UsernameNotFoundException("User not found with username or email : " + userPrincipalLdap.getUsername())
							);
			idUser = Long.toString(user.getId());
			// TODO: handle exception
		}

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

		return Jwts.builder()
				.setSubject(idUser)
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody();

		return Long.parseLong(claims.getSubject());
	}

	public boolean validateToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}
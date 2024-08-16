package io.waterkite94.hd.hotdeal.common.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.waterkite94.hd.hotdeal.common.security.dto.LoginRequest;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final UserDetailsService userDetailsService;
	private final AuthenticationManager authenticationManager;
	private final Environment env;

	public AuthenticationFilter(UserDetailsService userDetailsService, AuthenticationManager authenticationManager,
		Environment env) {
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
		this.env = env;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			LoginRequest creds = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

			return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException exception) {
			throw new UsernameNotFoundException(exception.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
		String username = ((User)authResult.getPrincipal()).getUsername();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		byte[] secretKeyBytes = Base64.getEncoder()
			.encode(Objects.requireNonNull(env.getProperty("jwt.secret")).getBytes(StandardCharsets.UTF_8));
		SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
		Instant now = Instant.now();

		String token = Jwts
			.builder()
			.subject(userDetails.getUsername())
			.expiration(Date.from(now.plusMillis(8640000)))
			.issuedAt(Date.from(now))
			.signWith(secretKey)
			.compact();

		response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
	}
}

package com.example.security.jwt;

import com.example.security.service.UserDetailsServiceImpl;
import com.example.security.utils.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenManager jwtTokenManager;
	private final UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final String header = request.getHeader(SecurityConstants.HEADER_STRING);

		String email = null;
		String authToken = null;

		if (Objects.nonNull(header) && header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			authToken = header.replace(SecurityConstants.TOKEN_PREFIX, Strings.EMPTY);

			try {
				email = jwtTokenManager.getUsernameFromToken(authToken);
			} catch (Exception e) {
				log.error("Authentication Exception: {}", e.getMessage());
				chain.doFilter(request, response);
				return;
			}
		}

		if (Objects.isNull(email) || Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
			chain.doFilter(request, response);
			return;
		}

		UserDetails user = userDetailsService.loadUserByUsername(email);
		boolean validToken = jwtTokenManager.validateToken(authToken, user.getUsername());

		if (!validToken) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		log.info("Authentication successful. Logged in user: {}", email);

		chain.doFilter(request, response);
	}
}

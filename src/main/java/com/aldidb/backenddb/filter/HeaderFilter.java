package com.aldidb.backenddb.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aldidb.backenddb.kernel.JwtTokenProvider;

public class HeaderFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;

	public HeaderFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = jwtTokenProvider.resolveToken(request);
		try {
			if (token != null && jwtTokenProvider.validateToken(token)) {
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception ex) {
			// this is very important, since it guarantees the user is not authenticated at all
			SecurityContextHolder.clearContext();
			response.sendError(HttpStatus.FORBIDDEN.value(), ex.getMessage());
			System.out.println(ex.getMessage());
			return;
		}
//		String Authorization = request.getHeader("Authorization");
//		System.out.println("===== ini level security =====");
//		System.out.println(Authorization);

		filterChain.doFilter(request, response);

	}

}

package com.aldidb.backenddb.kernel;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.aldidb.backenddb.message.ResponseCreateToken;
import com.aldidb.backenddb.repository.ModelUserAndRoles;
import com.aldidb.backenddb.repository.RoleRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	@Value("${security.jwt.token.expire-length:3600000}")
	private long validityInMilliseconds = 3600000; // 1h

	@Autowired
	private MyUserDetails myUserDetails;

	@Autowired
	private RoleRepository roleRepository;

	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	public ResponseCreateToken createToken(String username) {

		Claims claims = Jwts.claims().setSubject(username);
		ModelUserAndRoles data = roleRepository.getUserAndRoles(username);
		Set<String> items = new HashSet<String>(Arrays.asList(data.getRoles().split(";")));
		
		claims.put("auth", items.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" +role)).collect(Collectors.toList()));

		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMilliseconds);

		String jwt = Jwts.builder()//
				.setClaims(claims)//
				.setIssuedAt(now)//
				.setExpiration(validity)//
				.signWith(SignatureAlgorithm.HS256, secretKey)//
				.compact();
		
		ResponseCreateToken response = new ResponseCreateToken();
		response.setJwtToken(jwt);
		response.setRoles(Arrays.asList(data.getRoles().split(";")));
		response.setUsername(username);
		response.setUuid(data.getUuid());
		return response;
	}
	
	  public Authentication getAuthentication(String token) {
		    UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token));
		    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		  }

		  public String getUsername(String token) {
		    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		  }

		  public String resolveToken(HttpServletRequest req) {
		    String bearerToken = req.getHeader("Authorization");
		    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
		      return bearerToken.substring(7);
		    }
		    return null;
		  }

		  public boolean validateToken(String token) {
		    try {
		      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
		      return true;
		    } catch (JwtException | IllegalArgumentException e) {
//		      throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
		    	throw new IllegalArgumentException("invalid token");
		    }
		  }

}

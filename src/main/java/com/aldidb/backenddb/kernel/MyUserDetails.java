package com.aldidb.backenddb.kernel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aldidb.backenddb.model.User;
import com.aldidb.backenddb.repository.ModelUserAndRoles;
import com.aldidb.backenddb.repository.RoleRepository;
import com.aldidb.backenddb.repository.UserRepository;

@Service
public class MyUserDetails implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ModelUserAndRoles data = roleRepository.getUserAndRoles(username);
		User user = userRepository.findByName(username);
		if(user == null) {
			throw new UsernameNotFoundException("User '" + username + "' not found");
		}

		List<String> roles = Arrays.asList(data.getRoles().split(";"));
		Set<String> items = new HashSet<String>(roles);
		List<GrantedAuthority> authorities = items.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" +role)).collect(Collectors.toList());
		
	    return org.springframework.security.core.userdetails.User//
	            .withUsername(username)//
	            .password(user.getPassword())//
	            .authorities(authorities)//
	            .accountExpired(false)//
	            .accountLocked(false)//
	            .credentialsExpired(false)//
	            .disabled(false)//
	            .build();
	}

}

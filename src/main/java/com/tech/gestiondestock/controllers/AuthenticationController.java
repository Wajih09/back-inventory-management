package com.tech.gestiondestock.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tech.gestiondestock.dto.auth.AuthenticationRequest;
import com.tech.gestiondestock.dto.auth.AuthenticationResponse;
import com.tech.gestiondestock.models.auth.ExtendedUser;
import com.tech.gestiondestock.services.auth.ApplicationUserDetailsService;
import com.tech.gestiondestock.utils.JwtUtil;

import static com.tech.gestiondestock.utils.Constants.AUTHENTICATION_ENDPOINT;

@RestController
@RequestMapping(AUTHENTICATION_ENDPOINT)
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ApplicationUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
		String password = "123456789";
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(password);
		System.out.println(encodedPassword);
		System.out.println(String.format("request.getLogin() = %s", request.getLogin()));
		System.out.println(String.format("request.getPassword() = %s", request.getPassword()));
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getLogin(),
						request.getPassword()
			)
		);
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());
		final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);
		return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
	}

}

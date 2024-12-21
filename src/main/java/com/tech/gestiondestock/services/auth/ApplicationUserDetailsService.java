package com.tech.gestiondestock.services.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tech.gestiondestock.dto.UtilisateurDto;
import com.tech.gestiondestock.exception.EntityNotFoundException;
import com.tech.gestiondestock.exception.ErrorCodes;
import com.tech.gestiondestock.models.Utilisateur;
import com.tech.gestiondestock.models.auth.ExtendedUser;
import com.tech.gestiondestock.repository.UtilisateurDao;
import com.tech.gestiondestock.services.UtilisateurService;

@Service
public class ApplicationUserDetailsService implements UserDetailsService{
	
	private UtilisateurService service;
	
	public ApplicationUserDetailsService(@Lazy UtilisateurService service) {
		this.service = service;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UtilisateurDto utilisateur = service.findByEmail(email);
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	    utilisateur.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleNom())));
		return new ExtendedUser(utilisateur.getEmail(), utilisateur.getMotDePasse(), /*Collections.emptyList()*/ utilisateur.getEntreprise().getId(), authorities); 
	}
}

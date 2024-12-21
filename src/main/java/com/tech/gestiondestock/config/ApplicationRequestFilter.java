package com.tech.gestiondestock.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tech.gestiondestock.services.auth.ApplicationUserDetailsService;
import com.tech.gestiondestock.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Component //@Service or @Repositery both extends @Component https://www.youtube.com/watch?v=BVdQ3iuovg0 min40
//@RequiredArgsConstructor //create constructor when any final field is introduced in the class
public class ApplicationRequestFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;

  private final ApplicationUserDetailsService userDetailsService;
  
  public ApplicationRequestFilter(@Lazy ApplicationUserDetailsService userDetailsService) {
      this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    String userEmail = null;
    String jwt = null;
    String idEntreprise = null;

    if(authHeader != null && authHeader.startsWith("Bearer ")) {
      jwt = authHeader.substring(7);
      userEmail = jwtUtil.extractUsername(jwt);
      idEntreprise = jwtUtil.extractIdEntreprise(jwt);
    }

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      if (jwtUtil.validateToken(jwt, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities()
        );
        usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    MDC.put("idEntreprise", idEntreprise);
    chain.doFilter(request, response);
  }
}


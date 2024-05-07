package com.dip.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dip.util.JwtHelper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtHelper jwtHelper;
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestHeader = request.getHeader("Authorization");
		String username = null;
		String token = null;
		
		if(requestHeader != null && requestHeader.startsWith("Bearer")) {
			token = requestHeader.substring(7);
			
			try {
				username = this.jwtHelper.getUsernameFromToken(token);
				log.info("Username successfully extracted from the token");
				
			}catch(IllegalArgumentException e) {
				log.error("Token not in correct format");
				e.printStackTrace();
			}catch(ExpiredJwtException e) {
				log.error("Jwt token is expired");
				e.printStackTrace();
			}catch(MalformedJwtException e) {
				log.error("Wrong Token");
				e.printStackTrace();
			}catch(Exception e) {
				log.error("Error while extracting username");
				e.printStackTrace();
			}
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			log.trace("No authentication found in Security Context");
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			log.trace("Got user details fromt the userDetailsService");
			Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
			log.trace("Validated the token");
			
			if(validateToken) {
				log.trace("Valid token found");
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				log.trace("Instance of UsernamePasswordAuthenticationToken is created");
				log.trace("Setting the detaisl in the authentication object");
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				log.trace("Successful in setting the details");
				log.trace("Setting the authentication context in Security Context Holder");
				SecurityContextHolder.getContext().setAuthentication(authentication);
				log.trace("Successful in setting the context");
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}

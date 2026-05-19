package com.hms.HospitalManagementSystem.SecurityConfig;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response, 
                                        Authentication authentication) throws IOException, ServletException {
        
    	
    	// Extract roles directly from the authorities collection using GrantedAuthority streams
    	boolean isAdmin = authentication.getAuthorities().stream()
    	        .map(GrantedAuthority::getAuthority)
    	        .anyMatch(role -> role.equals("ROLE_ADMIN"));

    	boolean isDoctor = authentication.getAuthorities().stream()
    	        .map(GrantedAuthority::getAuthority)
    	        .anyMatch(role -> role.equals("ROLE_DOCTOR"));

    	boolean isPatient = authentication.getAuthorities().stream()
    	        .map(GrantedAuthority::getAuthority)
    	        .anyMatch(role -> role.equals("ROLE_PATIENT"));

    	// Route to the corresponding dashboard path
    	if (isAdmin) {
    	    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    	}
    	if (isDoctor) {
    	    response.sendRedirect(request.getContextPath() + "/doctor/dashboard");
    	}
    	if (isPatient) {
    	    response.sendRedirect(request.getContextPath() + "/patient/dashboard");
    	} 

    }
}

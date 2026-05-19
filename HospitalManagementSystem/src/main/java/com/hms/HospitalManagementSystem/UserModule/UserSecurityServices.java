package com.hms.HospitalManagementSystem.UserModule;

import java.util.Collections;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserSecurityServices implements UserDetailsService {
	
	private final UserRepository userRepository;

    public UserSecurityServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 2. Fetch the 'role' parameter selected in the HTML dropdown form
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String selectedRole = request.getParameter("role"); // Matches name="role" in HTML

            // 3. Strict Check: If the dropdown choice doesn't match the database role, fail immediately
            if (selectedRole == null || !user.getRole().name().equalsIgnoreCase(selectedRole)) {
                throw new BadCredentialsException("The selected role does not match your account type.");
            }
        }
        
        // Note: Spring Security expects roles to have a "ROLE_" prefix by default
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

}

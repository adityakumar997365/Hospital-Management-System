package com.hms.HospitalManagementSystem.SecurityConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	private final CustomSuccessHandler customSuccessHandler;
	
	

    public SecurityConfig(CustomSuccessHandler customSuccessHandler) {
		this.customSuccessHandler = customSuccessHandler;
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        // Essential: Use BCrypt to safely hash passwords in your database
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Allow everyone to access the login page, registration, and static assets
                .requestMatchers("/login", "/register", "/", "/register/***","/forgot-password","/forgot-password/verify","/forgot-password/update","/forgot-password/change-password", "/forgot-password/change-success").permitAll()
                
                // Restrict specific URLs to specific user roles
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/patient/**").hasRole("PATIENT")
                .requestMatchers("/doctor/**").hasRole("DOCTOR")
                
                // All other dashboard pages require a successful login
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")             // Your custom login endpoint or HTML controller
                .successHandler(customSuccessHandler)
                .failureUrl("/login?error=true") // Target page on invalid credentials
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            );

        return http.build();
    }
}


package com.example.demo.security.config;

import com.example.demo.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor // Lombok annotations to generate constructor with all fields as parameters.
@EnableWebSecurity // Enable Spring Security features for web applications.
public class WebSecurityConfig {

    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // Configures security filter chain for HTTP requests.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection for simplicity.
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v*/registration/**")// Allow registration API endpoints.
                        .permitAll() // Allow access without authentication.
                        .anyRequest().authenticated() // Require authentication for all other requests.
                )
                .formLogin(Customizer.withDefaults()); // Use default from login configuration.
        return http.build(); // Build and return the configured security filter chain.
    }


    // Configures DaoAuthenticationProvider with password encoder and user details service.
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder); // set password encoder
        provider.setUserDetailsService(appUserService); // set user details service.
        return provider; // Return configured  DaoAuthenticationProvider.
    }


    // Retrieves the authentication manager from Spring's authentication configuration.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    // Configures AuthenticationManagerBuilder to use custom authentication provider.
    @Bean
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }


}

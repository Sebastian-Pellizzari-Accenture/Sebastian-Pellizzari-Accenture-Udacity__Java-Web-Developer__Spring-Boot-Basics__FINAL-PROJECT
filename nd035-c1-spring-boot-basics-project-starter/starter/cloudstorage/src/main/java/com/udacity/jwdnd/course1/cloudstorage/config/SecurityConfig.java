package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(AbstractHttpConfigurer::disable)
            
            // fixes that weird re-direction error in udacity workspace
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(
                    (request, response, authException) ->
                        response.sendRedirect("/proxy/8080/login")
                )
            )
            
            .formLogin(form -> {
                form.loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/proxy/8080/home", true)
                .failureUrl("/proxy/8080/login?error")
                .permitAll();
            })
            
            
            /* 
            .formLogin(httpForm ->{
                httpForm.loginPage("/login").permitAll();
                // httpForm.defaultSuccessUrl("/home");
            })  
            */
            .logout(logout -> {
                logout.logoutSuccessUrl("/proxy/8080/login");
                logout.permitAll();
            })          
            .authorizeHttpRequests(registry ->{
                registry.requestMatchers("/login", "/signup", "/css/**","/js/**").permitAll();
                registry.anyRequest().authenticated();
            })
            .authenticationProvider(authenticationService)
            .build();
    }
}

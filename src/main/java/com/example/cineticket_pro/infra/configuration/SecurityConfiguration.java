package com.example.cineticket_pro.infra.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
        return  http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        /* publicas */
                .requestMatchers("/auth/login","/swagger-ui/**","/swagger-ui.html","/webjars/**","/swagger-resources/**","/v3/api-docs/**").permitAll()
                .requestMatchers("/senha/**").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/gerentes/admin").permitAll()

                        /* só gerente*/
                        .requestMatchers("/gerentes/**").hasRole("GERENTE")


                        /* só funcionário */
                        .requestMatchers(HttpMethod.POST, "/usuarios").hasAnyRole("GERENTE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/**").hasAnyRole("GERENTE", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/*/status").hasAnyRole("GERENTE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/filmes/**", "/sessoes/**").hasAnyRole("GERENTE", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/filmes/**", "/sessoes/**").hasAnyRole("GERENTE", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/filmes/**", "/sessoes/**").hasAnyRole("GERENTE", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/filmes/**", "/sessoes/**").hasAnyRole("GERENTE", "ADMIN")
                         /* cliente e funcinário*/
                        .requestMatchers(HttpMethod.GET, "/filmes/**", "/sessoes/**").hasAnyRole("GERENTE","ADMIN","USER")
                        .requestMatchers("/ingressos/**").hasAnyRole("GERENTE","USER","ADMIN")

                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistration(JwtFilter filter) {
        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }
}

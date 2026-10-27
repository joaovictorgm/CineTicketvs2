package com.example.cineticket_pro.infra.configuration.external;


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
                .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                        /* só funcionário */
                        .requestMatchers("/usuarios/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/gerentes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,   "/filmes/**", "/sessoes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,    "/filmes/**", "/sessoes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,  "/filmes/**", "/sessoes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/filmes/**", "/sessoes/**").hasRole("ADMIN")
                         /* cliente e funcinário*/
                        .requestMatchers(HttpMethod.GET, "/filmes/**", "/sessoes/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/ingressos/**").hasAnyRole("USER","ADMIN")

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

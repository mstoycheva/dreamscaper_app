package com.example.dreamscaper.config;

import com.example.dreamscaper.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
                //.userDetailsService(userDetailsService)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/starting", "/sign-in", "/sign-up").permitAll()
                        .requestMatchers("/css/*.css", "/js/*.js", "/images/*.png", "/images/*.jpg").permitAll()
                        .requestMatchers("/users/*").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .headers(AbstractHttpConfigurer::disable)
                .formLogin(form -> form
                        .loginPage("/sign-in")
                        .failureUrl("/sign-in?error")
                        .defaultSuccessUrl("/user-own-page", true)
                        .permitAll()
                )
                .httpBasic(withDefaults());

        return http.build();
    }
}

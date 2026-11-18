package it.monitoraggioinsetti.monitoraggio_insetti.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(HttpMethod.GET,
                                                                "/api/province",
                                                                "/api/comuni",
                                                                "/api/insetti")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.POST,
                                                                "/api/segnalazioni",
                                                                "/api/segnalazioni/stato")
                                                .permitAll()
                                                .anyRequest().hasRole("OPERATORE"))
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/api/segnalazioni", true)
                                                .permitAll())
                                .httpBasic(Customizer.withDefaults());

                return http.build();
        }
}

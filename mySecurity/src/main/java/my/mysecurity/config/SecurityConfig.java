package my.mysecurity.config;

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
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/security/admin").hasRole("ADMIN")
                        .requestMatchers("/security/user").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/security/join", "/security/login").permitAll()
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/security/login")
                        .loginProcessingUrl("/security/login")
                        .defaultSuccessUrl("/security/", true)
                        .failureUrl("/security/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/security/logout")
                        .logoutSuccessUrl("/security/")
                        .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

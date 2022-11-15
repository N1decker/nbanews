package ru.nidecker.nbanews.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.nidecker.nbanews.handler.AuthenticationFailureHandler;
import ru.nidecker.nbanews.service.UserService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/api/v1/registration/**", "/login/**", "/images/**", "/css/**", "/fonts/**", "/js/**").permitAll()
                .antMatchers("/users/**", "/api/admin/users/**").hasRole("ADMIN")
                .antMatchers("/api/news/**").hasRole("EDITOR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login").loginPage("/").failureHandler(authenticationFailureHandler).permitAll()
                .and()
                .logout().clearAuthentication(true).invalidateHttpSession(true).logoutUrl("/logout").logoutSuccessUrl("/").permitAll(false);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }
}
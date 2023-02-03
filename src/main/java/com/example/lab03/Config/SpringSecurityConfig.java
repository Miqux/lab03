package com.example.lab03.Config;

import com.example.lab03.Models.ProfileNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Profile(ProfileNames.USERS_IN_MEMORY)
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        User.UserBuilder userBuilder = User.builder();

        UserDetails user = userBuilder
                .username("username")
                .password(passwordEncoder.encode("username"))
                .roles("USER")
                .build();

        manager.createUser(user);

        return manager;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //tutaj wpinamy się z zezwoleniami do URL poszczególnych userów.
                .mvcMatchers("/post/**","/postList", "/form")
                .authenticated()
                .mvcMatchers("/resources/**", "/signup", "/about","/h2-console/**", "/registery").permitAll()
                .anyRequest().permitAll();//każde żądanie ma być uwierzytelnione
        http
                .formLogin()//uwierzytelnienie poprzez formularz logowania
                .loginPage("/login")//formularz dostępny jest pod URL
                .permitAll();//przyznaj dostęp wszystkim użytkownikom

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().accessDeniedPage("/403");
        http.logout().logoutSuccessUrl("/").permitAll();
        return http.build();

    }

}

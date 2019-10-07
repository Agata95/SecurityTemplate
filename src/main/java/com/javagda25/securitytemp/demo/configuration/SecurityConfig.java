package com.javagda25.securitytemp.demo.configuration;

import com.javagda25.securitytemp.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        zmodyfikowana reguła bezpieczeństwa
//        do tej pory wszystko było dla klienta
//        teraz tylko po podanych url wszyscy mają dostęp
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/",
                        "/css/**",
                        "/js/**",
                        "/webjars/**",
                        "/login").permitAll()
                .anyRequest().authenticated()
//        .anyRequest().authenticated() oznacza, że dla każdej innej strony ma pytać o login i hasło
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(authenticationService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        auth.authenticationProvider(daoAuthenticationProvider);
    }
}

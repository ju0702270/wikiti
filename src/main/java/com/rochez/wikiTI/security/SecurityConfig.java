package com.rochez.wikiTI.security;

import groovyjarjarantlr.Lookahead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

/**
 * @Author : Rochez Justin
 * @Creation : 10/06/2021
 * @Modification : 19/06/2021
 * @Revision : 1.0
 * @Description :Class pour définir les regles de sécurité
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = passwordEncoder ();
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    /**
     * Définition des regles de sécurité
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/auth/upload_ckeditor"); // Autorisation csrf disable pour /auth/upload_ckeditor
        http.headers().xssProtection();
        http.authorizeRequests().antMatchers("/","/index","/login","/show/**").permitAll();
        http.authorizeRequests().antMatchers( "/auth/**","/changePass","/effectiveChangePass","/utilisateur",
                "/utilisateur/delete","/updateFonction","/updateGrade").authenticated();

        http.authorizeRequests().antMatchers("/webjars/**").permitAll();//accès au webJars
        http.authorizeRequests().antMatchers("/admin/**").hasAnyRole("ADMIN");// Attention à bien ajouter les token Csrf dans les formulaires voulu coté client

        http.formLogin().loginPage("/login").failureForwardUrl("/login");
        http.logout().logoutSuccessUrl("/index"); // redirection vers index après logout

        http.exceptionHandling().accessDeniedPage("/accessDenied");
    }

    /**
     * Définition de la méthode d'encryption
     * cette encryption doit aussi être utilisée dans UtilisateurUtil
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }
}

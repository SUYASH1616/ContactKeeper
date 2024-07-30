package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.Services.Impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
    
    @Autowired
    private SecurityCustomUserDetailService userDetailsService;

    @Autowired
    private OAuthenticationSuccessHandler oAuthenticationSuccessHandler;

    @Autowired
    private AuthFailtureHandler authFailtureHandler;

    // public SecurityConfig(SecurityCustomUserDetailService userDetailsService) {
    //     this.userDetailsService = userDetailsService;
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/home", "/register", "/login").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
        
        // we can give default login page with some other syntax
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            // post
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.defaultSuccessUrl("/user/profile", true);
            formLogin.failureUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");

            formLogin.failureHandler(authFailtureHandler);
        });

        // THESE ARE GET REQUEST.   
        // by default csrf make post
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm->{
            // post
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true ");
        });

        // this default OAuth2 page
        // httpSecurity.oauth2Login(Customizer.withDefaults());
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            // to provide path from default page link
            oauth.successHandler(oAuthenticationSuccessHandler);
        });

        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

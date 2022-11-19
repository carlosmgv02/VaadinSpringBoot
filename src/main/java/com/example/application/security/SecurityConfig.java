package com.example.application.security;

import com.example.application.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private static class CrmMemoryUserDetailsManager extends InMemoryUserDetailsManager{
        public CrmMemoryUserDetailsManager(){
            createUser(new User("user","{noop}userpass", Collections.singleton(new SimpleGrantedAuthority("USER"))));
            //createUser(new User("admin","{noop}adminpass",Collections.singleton(new SimpleGrantedAuthority("ADMIN"))));
        }
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").permitAll();
        super.configure(http);
        setLoginView(http, LoginView.class,"/logout");
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        return new CrmMemoryUserDetailsManager();
    }
}

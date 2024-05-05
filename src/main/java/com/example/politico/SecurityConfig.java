package com.example.politico;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher("127.0.0.1");
        List<RequestMatcher> ipMatchers = new ArrayList<>();
        ipMatchers.add(new IpAddressMatcher("127.0.0.1"));
        ipMatchers.add(new IpAddressMatcher("::1")); // IPv6 loopback address

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(ipAddressMatcher).permitAll()
                                .requestMatchers(ipMatchers.toArray(new RequestMatcher[0])).permitAll()
                        .anyRequest().denyAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable) // Disable HTTP Basic Auth
                .formLogin(AbstractHttpConfigurer::disable); // Disable form login

        return http.build();
    }
}

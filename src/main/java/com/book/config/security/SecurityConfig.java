package com.book.config.security;

import com.book.config.oauth.PrincipalOauth2UserService;
import com.book.config.security.jwt.JwtAuthenticationFilter;
import com.book.config.security.jwt.JwtTokenProvider;
import com.book.exception.user.AuthFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final PrincipalOauth2UserService principalOauth2UserService;
    private final AuthFailureHandler customFailureHandler;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .failureHandler(customFailureHandler)
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/auth/login/oauth")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
        http.logout().logoutUrl("/auth/logout").logoutSuccessUrl("/");

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers("/user/**").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .oauth2Login()
//                .loginPage("/auth/login/oauth")
//                .userInfoEndpoint()
//                .userService(principalOauth2UserService);
//
//        return http.build();
//    }
}

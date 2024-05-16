package com.example.chatserver.configuration.security;

import com.myproject.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
   private final UserDetailsService userDetailsService;

   private final AuthEntryPointJwt unauthorizedHandler;

   private final AuthTokenFilter authenticationJwtTokenFilter;

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.cors().and().csrf().disable()
               .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
               .authorizeRequests()
               .antMatchers("/api/test/**").permitAll()
               .antMatchers(UrlMapping.AUTH + UrlMapping.LOGIN).permitAll()
               .antMatchers("/api/test/**").permitAll()
               .anyRequest().authenticated();

       http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

       return http.build();
   }

   @Bean
   public WebMvcConfigurer corsConfigurer() {
       return new WebMvcConfigurer() {
           @Override
           public void addCorsMappings(CorsRegistry registry) {
               registry.addMapping("/**")
                       .allowedMethods("*");
           }
       };
   }
}
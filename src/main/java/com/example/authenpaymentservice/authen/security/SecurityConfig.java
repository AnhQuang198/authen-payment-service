package com.example.authenpaymentservice.authen.security;

import com.example.authenpaymentservice.authen.security.jwt.JwtAuthFilter;
import com.example.authenpaymentservice.authen.security.oauth2.CustomOAuth2UserService;
import com.example.authenpaymentservice.authen.security.oauth2.handler.OAuth2AuthenticationFailureHandler;
import com.example.authenpaymentservice.authen.security.oauth2.handler.OAuth2AuthenticationSuccessHandler;
import com.example.authenpaymentservice.authen.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired private AuthService authService;
    @Autowired private CustomOAuth2UserService oAuth2UserService;
    @Autowired private OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    @Autowired private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    //Inject in AuthService to verify user login
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager beans
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                    .csrf().disable()
                    .formLogin().disable()
                    .httpBasic().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                        .antMatchers("/v1/auth/register", "/v1/auth/login", "/v1/auth/forgot-password", "/v1/auth/verify-otp",
                                "/v1/auth/send-otp", "/v1/auth/generate-token","/v1/public/**")
                    .permitAll()
                .antMatchers("/v1/**")
                .authenticated()
                .and()
                    .oauth2Login()
                    .authorizationEndpoint()
                        .baseUri("/oauth2/authorize")
                .and()
                    .redirectionEndpoint()
                        .baseUri("/oauth2/callback/*")
                .and()
                    .userInfoEndpoint()
                    .userService(oAuth2UserService)
                .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler);
        //request di vao phai qua lop jwtAuthFilter truoc
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}

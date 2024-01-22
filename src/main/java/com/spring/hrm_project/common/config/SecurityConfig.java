package com.spring.hrm_project.common.config;

import com.spring.hrm_project.jwt.JwtAccessDeniedHandler;
import com.spring.hrm_project.jwt.JwtAuthenticationEntryPoint;
import com.spring.hrm_project.jwt.JwtTokenFilter;
import com.spring.hrm_project.repository.RoleCustomRepository;
import com.spring.hrm_project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final UserService userService;
    private static String secretKey = "my-secret-key-123123";

    private final RoleCustomRepository roleCustomRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 허용되어야 할 경로들, 특히 정적파일들(필요한경우만 설정)
        return web -> web.ignoring().requestMatchers("/sw", "/swagger-ui/**", "v3/**");
    }

    //JWT 토큰을 사용하여 인증하는 기능을 구현, 특정 경로에 대한 접근 제어를 설정하는 역할
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity
            , JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint
            , JwtAccessDeniedHandler jwtAccessDeniedHandler) throws Exception{
        httpSecurity
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable) //HTTP 기본 인증을 비활성화
                .csrf(CsrfConfigurer::disable) //CSRF(Cross-Site Request Forgery) 보호를 비활성화
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/main").permitAll()
                                .requestMatchers("/jwt-login/login").permitAll())
                .authorizeHttpRequests(authorize ->
                                roleCustomRepository.getSecurityRoleApi().forEach(role -> authorize.requestMatchers(role.getApiUrl()).hasAuthority(role.getRoleId())))
                .addFilterBefore(new JwtTokenFilter(userService, secretKey, roleCustomRepository), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(authenticationManager -> authenticationManager
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler));

                return httpSecurity.build();
    }
}
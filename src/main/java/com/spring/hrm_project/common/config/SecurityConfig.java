package com.spring.hrm_project.common.config;

import com.spring.hrm_project.jwt.JwtAuthenticationEntryPoint;
import com.spring.hrm_project.jwt.JwtTokenFilter;
import com.spring.hrm_project.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
//Spring Security는 Spring 기반의 애플리케이션에서 보안 관련 기능을 구현할 수 있게 해주는 프레임워크
public class SecurityConfig {

    private final UserService userService;
    private static String secretKey = "my-secret-key-123123";

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 허용되어야 할 경로들, 특히 정적파일들(필요한경우만 설정)
        return web -> web.ignoring().requestMatchers("/sw", "/swagger-ui/**","v3/**");
    }

    //JWT 토큰을 사용하여 인증하는 기능을 구현, 특정 경로에 대한 접근 제어를 설정하는 역할
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .formLogin(FormLoginConfigurer::disable)//HttpSecurity 객체는 스프링 시큐리티 설정을 구성하는 데 사용
                .httpBasic(HttpBasicConfigurer::disable) //HTTP 기본 인증을 비활성화
                .csrf(CsrfConfigurer::disable) //CSRF(Cross-Site Request Forgery) 보호를 비활성화
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 관리를 설정하여 상태가 없는 세션을 사용. 즉, 각 요청마다 세션을 생성 X
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/jwt-login/info").authenticated())
//                .exceptionHandling(authenticationManager -> authenticationManager
//                        .authenticationEntryPoint(new AuthenticationEntryPoint() {
//                            @Override
//                            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//                                log.info("인증실패");
//                            }
//                        })
//                        .accessDeniedHandler(new AccessDeniedHandler() {
//                            @Override
//                            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                                log.info("인증실패");
//                            }
//                        }))
                .addFilterBefore(new JwtTokenFilter(userService, secretKey), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(authenticationManager -> authenticationManager
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
                .authorizeRequests() //요청에 대한 권한을 설정하기 위한 메서드
                .anyRequest().permitAll(); //그 외의 모든 요청은 모든 사용자에게 허용

                return httpSecurity.build();
    }
}

package com.spring.hrm_project.jwt;

import com.spring.hrm_project.common.config.domain.entity.User;
import com.spring.hrm_project.service.UserService;
import com.spring.hrm_project.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION); //헤더에서 JWT 토큰 추출
        log.info("필터작동");
        // Header의 Authorization 값이 비어있으면 => JWT 토큰을 전송 X
        if(authorizationHeader == null) {
            log.info("널임");
            // 화면 로그인 시 쿠키의 "jwtToken"으로 JWT 토큰을 전송
            // 쿠키에도 JWT Token이 없다면 로그인 하지 않은 것으로 간주
            if(httpServletRequest.getCookies() == null) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            // 쿠키에서 "jwtToken"을 Key로 가진 쿠키를 찾아서 가져오고 없으면 null return
            Cookie jwtTokenCookie = Arrays.stream(httpServletRequest.getCookies())
                    .filter(cookie -> cookie.getName().equals("jwtToken"))
                    .findFirst()
                    .orElse(null);

            if(jwtTokenCookie == null) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            // 쿠키 JWT Token이 있다면 이 토큰으로 인증, 인가 진행
            String jwtToken = jwtTokenCookie.getValue();
            authorizationHeader = "Bearer " + jwtToken;
        }

        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 형식의 토큰
        if(!authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
        String token = authorizationHeader.split(" ")[1];

        // 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
        if(JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // Jwt Token에서 loginId 추출
        String loginId = JwtTokenUtil.getLoginId(token, secretKey);

        //추출한 loginId로 User 찾아오기
        User loginUser = userService.getLoginUserByLoginId(loginId);

        // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getLoginId(), null, List.of(new SimpleGrantedAuthority(loginUser.getUserRole().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        // 권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

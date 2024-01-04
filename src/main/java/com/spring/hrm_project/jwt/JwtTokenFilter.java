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
            log.info("첫번째");
            // 화면 로그인 시 쿠키의 "jwtToken"으로 JWT 토큰을 전송
            // 쿠키에도 JWT Token이 없다면 로그인 하지 않은 것으로 간주
            if(httpServletRequest.getCookies() == null) {
                log.info("두번째");
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            // 쿠키에서 "jwtToken"을 Key로 가진 쿠키를 찾아서 가져오고 없으면 null return
            Cookie jwtTokenCookie = Arrays.stream(httpServletRequest.getCookies())
                    .filter(cookie -> cookie.getName().equals("jwtToken"))
                    .findFirst()
                    .orElse(null);

            if(jwtTokenCookie == null) {
                log.info("세번째");
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            // 이 자리에 쿠키에서 가지온 값과 엑세스토큰을 비교하는 로직이 있어야 함

            // 쿠키 JWT Token이 있다면 이 토큰으로 인증, 인가 진행
            String jwtToken = jwtTokenCookie.getValue();
            authorizationHeader = "Bearer " + jwtToken;
            log.info("11111");
        }

        // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 형식의 토큰
        if(!authorizationHeader.startsWith("Bearer ")) {
            log.error("잘못된 형식의 토큰입니다.");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        log.info("여기탈까?");

        // 전송받은 값에서 'Bearer ' 뒷부분(JWT 토큰) 추출
        String token = authorizationHeader.split(" ")[1];
        log.info("토큰값 : " + token);

        // 전송받은 토큰이 만료되었으면 => 다음 필터 진행(인증 X)
        if(JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // Jwt Token에서 loginId 추출
        String loginId = JwtTokenUtil.getLoginId(token, secretKey);
        log.info("로그인아이디 : " + loginId);

        //추출한 loginId로 User 찾아오기
        User loginUser = userService.getLoginUserByLoginId(loginId);
        log.info("유저정보 : " + loginUser);

        // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginUser.getLoginId(), null, List.of(new SimpleGrantedAuthority(loginUser.getUserRole())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        // 권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

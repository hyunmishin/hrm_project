package com.spring.hrm_project.jwt;

import com.spring.hrm_project.entity.User;
import com.spring.hrm_project.entity.UserToken;
import com.spring.hrm_project.service.UserService;
import com.spring.hrm_project.utils.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
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
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
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
        String userId = JwtTokenUtil.getUserId(token, secretKey);
        log.info("유저아이디 : " + userId);

        //추출한 loginId로 User 찾아오기
        User userInfo = userService.getUserInfoByUserId(userId);

        UserToken userToken = userService.getUserTokenInfo(userId);

        // DB에 저장되어 있는 해당 사용자 토큰값과 현재 토큰값이 다르면 다음 필터 진행
        if(!userToken.getUserAccessToken().equals(token)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        log.info("유저정보 : " + userInfo);

        // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userInfo.getUserId(), null, List.of(new SimpleGrantedAuthority("ADMIN")));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        // 권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

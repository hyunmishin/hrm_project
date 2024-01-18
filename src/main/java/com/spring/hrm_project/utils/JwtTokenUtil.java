package com.spring.hrm_project.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {

    // JWT 토큰 발급
    public static String createToken(String userId, String key, long expireTimeMs) {
        // Claim = Jwt 토큰에 들어갈 정보
        // Claim에 loginId를 넣어줌으로써 나중에 loginId를 꺼내서 사용 가능
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder() //토큰을 생성하기 위한 빌더 객체
                .setClaims(claims) //앞서 생성한 클레임 객체를 토큰에 설정
                .setIssuedAt(new Date(System.currentTimeMillis())) //현재 시간을 사용하여 토큰이 발급된 시간을 설정
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs)) //토큰의 만료시간을 설정
                .signWith(SignatureAlgorithm.HS256, key) //토큰에 서명을 추가
                .compact(); //설정된 정보를 바탕으로 최종적인 JWT 토큰을 생성하고 반환
    }

    // Claims에서 loginId 추출
    public static String getUserId(String token, String secretKey) {
        return extractClaims(token, secretKey).get("userId").toString();
    }

    // 발급된 JWT 토큰의 만료 시간이 지났는지 체크
    public static boolean isExpired(String token, String secretKey) {
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 체크
        return expiredDate.before(new Date()); //expiredDate(토큰의 만료 시간)가 현재 시간보다 이전인지 체크
    }

    // SecretKey를 사용해 토큰 파싱
    // 클라이언트나 서버에서 JWT 토큰에 포함된 사용자나 세션 정보를 얻어 주어진 JWT 토큰에서 클레임 정보를 추출하여 반환
    private static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}

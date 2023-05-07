package com.book.config.security.jwt;

import io.jsonwebtoken.*;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;


@Component
@Slf4j
public class JwtTokenProvider {

    private final long accessTokenValidTime = 30 * 60 * 1000L;
    private final long refreshTokenValidTime = 3 * 24 * 60 * 60 * 1000L;


    private final Key secretKey;

    public JwtTokenProvider(@Value("${jwt.key}") String key) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    //JWT 토큰 생성
    public JwtResponse createToken(Long userId){
        Date now = new Date();
        String accessToken = Jwts.builder()
                .claim("userId", userId)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        return new JwtResponse(accessToken, refreshToken);
    }

    public String getJwt(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return token.substring(7);
    }

    public Claims parseJwt(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public Long getUserId(String token){
        return parseJwt(token).get("userId", Long.class);
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
//        try {
//            return !parseJwt(token).getExpiration().before(new Date());
//        } catch (Exception e) {
//            return false;
//        }
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
}
package com.book.utils.jwt;

import com.book.exception.book.UnAuthorizedAccess;
import com.book.utils.RedisService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Duration;
import java.util.Date;


@Component
@Slf4j
public class JwtTokenProvider {

    private final long accessTokenValidTime = 30 * 60 * 1000L;
    private final long refreshTokenValidTime = 3 * 24 * 60 * 60 * 1000L;


    private final Key secretKey;
    private final RedisService redisService;

    public JwtTokenProvider(@Value("${jwt.key}") String key, RedisService redisService) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.redisService = redisService;
    }

    public String createAccessToken(Long userId){
        Date now = new Date();
        String accessToken = Jwts.builder()
                .claim("userId", userId)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        return accessToken;
    }

    public String createRefreshToken(Long userId){
        Date now = new Date();
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        redisService.setValues("refresh:" + String.valueOf(userId), refreshToken, Duration.ofMillis(refreshTokenValidTime));

        return refreshToken;
    }


    public String getAccessToken(HttpServletRequest request){
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

    public JwtResponse reissueToken(String refreshToken){
        if(!validateToken(refreshToken)){
            throw new UnAuthorizedAccess("리프레시 토큰이 유효하지 않습니다.");
        }

        Long userId = getUserId(refreshToken);
        if(!redisService.getValues("refresh:" + String.valueOf(userId)).equals(refreshToken)){
            throw new UnAuthorizedAccess("리프레시 토큰이 유효하지 않습니다.");
        }
        return new JwtResponse(createAccessToken(userId), createRefreshToken(userId));
    }

    public void deleteToken(Long userId){
        redisService.deleteValues("refresh:" + String.valueOf(userId));
    }

//    public void invalidateAccessToken(String accessToken){
//        Date expiration = parseJwt(accessToken).getExpiration();
//        Long now = new Date().getTime();
//        Long remainTime = expiration.getTime() - now;
//
//        redisService.setValues("access:"+String.valueOf(getUserId(accessToken)), "logout",  Duration.ofMillis(remainTime));
//    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
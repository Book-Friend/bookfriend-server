package com.book.config.interceptor;

import com.book.utils.jwt.JwtTokenProvider;
import com.book.exception.book.UnAuthorizedAccess;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class LoginCheckHandler {

    private final JwtTokenProvider tokenProvider;

    public Long getUserId(HttpServletRequest request){
        String token = tokenProvider.getAccessToken(request);
        if (token != null && tokenProvider.validateToken(token)) {
            Long userId = tokenProvider.getUserId(token);
            if(userId != null){
                return userId;
            }
        }
        throw new UnAuthorizedAccess(String.format("JWT 토큰 (%s)이 잘못되었습니다.", token));
    }
}

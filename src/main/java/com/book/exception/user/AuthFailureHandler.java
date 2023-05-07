package com.book.exception.user;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMsg = URLEncoder.encode("아이디 또는 비밀번호를 다시 확인해주세요", "UTF-8");
        setDefaultFailureUrl("/auth/login?error=true&exception="+errorMsg);
        super.onAuthenticationFailure(request, response, exception);
    }
}

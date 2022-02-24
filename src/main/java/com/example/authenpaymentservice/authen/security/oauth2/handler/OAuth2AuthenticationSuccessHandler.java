package com.example.authenpaymentservice.authen.security.oauth2.handler;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.security.jwt.JwtTokenProvider;
import com.example.authenpaymentservice.authen.utils.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired private JwtTokenProvider tokenProvider;

    @Value("${auth.authorizedRedirectUris}")
    private String targetUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User user = UserData.getCurrentUserLogin(authentication);
        String token = tokenProvider.generateToken(user);

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

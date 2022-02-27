package com.example.authenpaymentservice.authen.security.oauth2.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
  @Value("${auth.authorizedRedirectUris}")
  private String targetUrl;

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {

    StringBuilder requestUrl = new StringBuilder(targetUrl);
    requestUrl.append("?error=").append(exception.getLocalizedMessage());

    getRedirectStrategy().sendRedirect(request, response, String.valueOf(requestUrl));
  }
}

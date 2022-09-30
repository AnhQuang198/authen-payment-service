package com.example.authenpaymentservice.authen.security.jwt;

import com.example.authenpaymentservice.authen.service.AuthService;
import com.example.authenpaymentservice.authen.utils.JsonParser;
import com.example.authenpaymentservice.exception.ErrorDetails;
import com.example.authenpaymentservice.exception.Message;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Log4j2
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && Objects.nonNull(jwtTokenProvider.validateToken(jwt))) {
                int userId = jwtTokenProvider.getUserIdFromJWT(jwt);
                UserDetails userDetails = authService.loadUserById(userId);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken
                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails();
            errorDetails.setMessage(Message.NO_ACCESS_RESOURCE);
            errorDetails.setStatusCode(HttpStatus.SC_UNAUTHORIZED);
            errorDetails.setPath(request.getRequestURI());
            errorDetails.setTimestamp(new Date());
            log.error("JWT Error {}", e);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            if (e instanceof ExpiredJwtException) {
                errorDetails.setMessage(Message.JWT_EXPIRED);
                errorDetails.setStatusCode(HttpStatus.SC_FORBIDDEN);
                response.setStatus(HttpStatus.SC_FORBIDDEN);
            }
            response.getWriter().write(JsonParser.toJson(errorDetails));
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("x-auth-token");
        if (StringUtils.hasText(bearerToken)) {
            return bearerToken;
        }
        return null;
    }
}

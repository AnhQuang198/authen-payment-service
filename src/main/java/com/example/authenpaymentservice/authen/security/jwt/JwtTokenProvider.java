package com.example.authenpaymentservice.authen.security.jwt;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.enums.UserState;
import com.example.authenpaymentservice.authen.model.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expire-time}")
    private int tokenExpireTime;

    @Value("${jwt.refresh-expire-time}")
    private int refreshExpireTime;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userRole", String.valueOf(user.getRole()));
        claims.put("userState", String.valueOf(user.getState()));
        return doGenerateToken(claims, String.valueOf(user.getId()));
    }

    public String generateRefreshToken(User user) {
        return doGenerateRefreshToken(user.getId(), user.getRole().toString());
    }

    private String doGenerateRefreshToken(int userId, String userRole) {
        return String.format("%s-%s-%s", UUID.randomUUID(), userId, userRole);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public int getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public TokenInfo validateToken(String accessToken) {
        Claims claims = getAllClaimsFromToken(accessToken);

        Date expiration = claims.getExpiration();
        if(expiration.before(new Date()))
            return null;

        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setUserId(Integer.valueOf(claims.get(Claims.SUBJECT, String.class)));
        tokenInfo.setRole(UserRole.valueOf(claims.get("userRole", String.class)));
        tokenInfo.setState(UserState.valueOf(claims.get("userState", String.class)));

        return tokenInfo;
    }
}

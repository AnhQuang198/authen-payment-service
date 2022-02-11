package com.example.authenpaymentservice.authen.security.jwt;

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

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expire-time}")
    private int tokenExpireTime;

    @Value("${jwt.refresh-expire-time}")
    private int refreshExpireTime;

    public String generateToken(TokenInfo tokenInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userRole", tokenInfo.getRole().toString());
        claims.put("userState", tokenInfo.getState().toString());
        return doGenerateToken(claims, tokenInfo.getUserId().toString());
    }

    public String generateRefreshToken(TokenInfo tokenInfo) {
        return doGenerateRefreshToken(tokenInfo.getUserId(), tokenInfo.getRole().toString());
    }

    private String doGenerateRefreshToken(int userId, String userRole) {
        return "random string " + userRole + " " + userId;
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

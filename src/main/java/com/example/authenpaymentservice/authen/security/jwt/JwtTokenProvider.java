package com.example.authenpaymentservice.authen.security.jwt;

import com.example.authenpaymentservice.authen.entity.User;
import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.enums.UserState;
import com.example.authenpaymentservice.authen.security.data.TokenInfo;
import com.example.authenpaymentservice.authen.utils.Common;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
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

    @Getter
    @Value("${jwt.token-expire-time}")
    private long tokenExpireTime;

    @Getter
    @Value("${jwt.refresh-expire-time}")
    private long refreshExpireTime;

    @Getter
    private long expAt;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", String.valueOf(user.getId()));
        claims.put("userRole", String.valueOf(user.getRole()));
        claims.put("userState", String.valueOf(user.getState()));
        return doGenerateToken(claims, String.valueOf(user.getId()));
    }

    public String generateRefreshToken(User user) {
        return doGenerateRefreshToken(user.getId(), user.getRole().toString());
    }

    private String doGenerateRefreshToken(long userId, String userRole) {
        String refreshToken = String.format("%s-%s-%s", UUID.randomUUID(), userRole, userId);
        return Common.encode(refreshToken);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        long iat = System.currentTimeMillis() + tokenExpireTime;
        expAt = iat;
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(iat)) //milliseconds
                .setExpiration(new Date(iat)) //milliseconds
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public long getUserIdFromJWT(String token) {
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

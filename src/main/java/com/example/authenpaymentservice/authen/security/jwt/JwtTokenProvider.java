package com.example.authenpaymentservice.authen.security.jwt;

import com.example.authenpaymentservice.authen.enums.UserRole;
import com.example.authenpaymentservice.authen.enums.UserState;
import com.example.authenpaymentservice.authen.model.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expire-time}")
    private int tokenExpireTime;

    @Value("${jwt.refresh-expire-time}")
    private int refreshExpireTime;

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

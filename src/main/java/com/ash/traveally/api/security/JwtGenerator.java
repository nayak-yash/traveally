package com.ash.traveally.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.ash.traveally.api.security.SecurityConstant.*;

@Component
public class JwtGenerator {

    public String generateToken(Authentication auth) {
        String email = auth.getName();
        Date currentDate  = new Date();
        Date expiryDate = new Date(currentDate.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String getEmailFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }
}

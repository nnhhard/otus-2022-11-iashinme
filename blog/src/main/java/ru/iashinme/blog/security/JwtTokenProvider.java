package ru.iashinme.blog.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import ru.iashinme.blog.model.User;

import java.security.Key;

@Component
public class JwtTokenProvider {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String getJwtToken(User user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(key)
                .compact();
    }

    public String getUsername(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

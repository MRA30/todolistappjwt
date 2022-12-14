package com.todolistapp.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.todolistapp.models.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

    private String secretKet = "todolistapp";

    private Long time = System.currentTimeMillis();

    private Long tokenValid = System.currentTimeMillis() + 1000 * 60 * 60 * 5;

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractFullName(String token) {
        String getSubjectAll = extractClaim(token, Claims::getSubject);
        String[] jwtSubject = getSubjectAll.split(",");
        return jwtSubject[1];
    }

    public Integer extractId(String token) {
        String getSubjectAll = extractClaim(token, Claims::getSubject);
        String[] jwtSubject = getSubjectAll.split(",");
        return Integer.parseInt(jwtSubject[0]); 
    }

    public String extractEmail(String token) {
        String getSubjectAll = extractClaim(token, Claims::getSubject);
        String[] jwtSubject = getSubjectAll.split(",");
        return jwtSubject[2];
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKet).parseClaimsJws(token).getBody();
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, user.getId() + "," + user.getFullName() + "," + user.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(time)).setExpiration(new Date(tokenValid))
                .signWith(SignatureAlgorithm.HS256, secretKet).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername())  && !isTokenExpired(token));
    }
    
}

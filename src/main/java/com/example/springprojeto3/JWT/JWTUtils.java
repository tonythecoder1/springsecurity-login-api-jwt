package com.example.springprojeto3.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JWTUtils {
    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "0123456789-0123456789-0123456789";
    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 0;
    public static final long EXPIRE_MINUTES = 30;

    private  JWTUtils(){}

    private static Key generateKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Date start) {
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static JWTToken createToken(String username, String role, Long id) {
        Date issuedAt = new Date();
        Date limit = toExpireDate(issuedAt);

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(limit)
                .claim("role", role)
                .claim("id", id)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();


        return new JWTToken(token);
    }


    static Claims getClaimsFromToken(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token)).getBody();
        }catch (JwtException e){
            log.error(String.format("Token Invalido %s", e.getMessage()));
        }
        return null;
    }

    public static String getUsernameFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }

    public static boolean isValidToken(String token){
        try{
        Jwts.parserBuilder()
                .setSigningKey(generateKey()).build()
                .parseClaimsJws(refactorToken(token));
        return true;
        }catch (JwtException e){
            log.error(String.format("Token invalido %s", e.getMessage()));
        }
        return false;
    }

    public static String refactorToken(String token){

        if (token.contains(JWT_BEARER)){
            return token.substring((JWT_BEARER.length()));
        }
        return token;
    }
}


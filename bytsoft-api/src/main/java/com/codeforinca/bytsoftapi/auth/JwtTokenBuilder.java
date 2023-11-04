package com.codeforinca.bytsoftapi.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtTokenBuilder
{
    private static final String SECRET_KEY = "2B4B6250655368566D597133743677397A244226452948404D635166546A576E";
    private static final long EXPIRATION_TIME = 3600000;

    private JwtTokenBuilder jwtTokenBuilder;

    private JwtTokenBuilder() {} // Singleton Pattern

    public JwtTokenBuilder getInstance(

    ){
        if(jwtTokenBuilder==null){
            jwtTokenBuilder=new JwtTokenBuilder();
        }
        return jwtTokenBuilder;
    }

    public static String generateToken(
            String username
    ) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(
            String token
    ) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static String getUsernameFromToken(
            String token
    ) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public static boolean isTokenExpired(
                    String token
            ) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

}

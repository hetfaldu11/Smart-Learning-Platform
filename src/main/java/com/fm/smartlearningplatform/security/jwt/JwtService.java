//package com.fm.smartlearningplatform.security.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//
//@Service
//public class JwtService {
//
//    private static final String SECRET =
//            "mysecretkeymysecretkeymysecretkey123456";
//
//    private Key getSignKey() {
//
//        byte[] keyBytes =
//                Decoders.BASE64.decode(
//                        Base64.getEncoder()
//                                .encodeToString(
//                                        SECRET.getBytes()
//                                )
//                );
//
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String generateToken(
//            String email
//    ) {
//
//        return Jwts.builder()
//
//                .setSubject(email)
//
//                .setIssuedAt(new Date())
//
//                .setExpiration(
//                        new Date(
//                                System.currentTimeMillis()
//                                        + 1000 * 60 * 60
//                        )
//                )
//
//                .signWith(
//                        getSignKey(),
//                        SignatureAlgorithm.HS256
//                )
//
//                .compact();
//    }
//
//    public String extractUsername(
//            String token
//    ) {
//
//        Claims claims =
//                Jwts.parserBuilder()
//                        .setSigningKey(getSignKey())
//                        .build()
//                        .parseClaimsJws(token)
//                        .getBody();
//
//        return claims.getSubject();
//    }
//
//    public boolean isTokenValid(
//            String token,
//            UserDetails userDetails
//    ) {
//
//        String username =
//                extractUsername(token);
//
//        return username.equals(
//                userDetails.getUsername()
//        );
//    }
//}
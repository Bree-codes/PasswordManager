package com.bughunters.code.passwordmanagerwebapplication.service;

import com.bughunters.code.passwordmanagerwebapplication.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${key}")
    private String key;

    public String generateToken(User user){

        Map<String, Object> claim = new HashMap<>();

        claim.put("userId", user.getId());
        claim.put("userRole", user.getRole());

        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 5))
                .signWith(generateSecretKey())
                .claims(claim)
                .compact();
    }

    private Claims extractClaims(String token){

        return Jwts
                .parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private  <T> T extractClaim(Function<Claims , T > claimResolver, String token){
        return claimResolver.apply(extractClaims(token));
    }

    public String getExtractUsername(String token){
        return extractClaim(Claims::getSubject, token);
    }

    public Date extractExpirationDate(String token){
        return extractClaim(Claims::getExpiration, token);
    }

    private SecretKey generateSecretKey(){
        byte[] bytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(bytes);
    }


    public Boolean isValid(String token, UserDetails userDetails){
        return !(extractExpirationDate(token).compareTo(new Date()) < 0)
                && getExtractUsername(token).equals(userDetails.getUsername());
    }


}

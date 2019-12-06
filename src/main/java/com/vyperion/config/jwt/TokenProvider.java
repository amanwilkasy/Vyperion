package com.vyperion.config.jwt;

import com.vyperion.dto.User;
import com.vyperion.dto.client.ClientUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;

@Slf4j
@Component
public class TokenProvider {

    private final UserDetailsServiceImpl userDetailsService;

    public TokenProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmailFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public String createToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + JwtProperties.EXPIRATION);

        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("user", new ClientUser(user));
        claims.put("roles", user.getRoles());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, JwtProperties.SECRET)
                .compact();
    }

    private String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(JwtProperties.SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    ClientUser getClientUserFromToken(String token) {
        LinkedHashMap userClaims = Jwts.parser().setSigningKey(JwtProperties.SECRET)
                .parseClaimsJws(token).getBody()
                .get("user", LinkedHashMap.class);
        return new ClientUser(userClaims.get("id").toString(),
                userClaims.get("firstName").toString(),userClaims.get("lastName").toString(),
                userClaims.get("email").toString(), new HashSet<>((ArrayList) userClaims.get("roles")));
    }

    boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JwtProperties.SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

}

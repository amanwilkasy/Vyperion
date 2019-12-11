package com.vyperion.config.jwt;

import com.vyperion.dto.client.ClientUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

//https://stackoverflow.com/questions/13152946/what-is-onceperrequestfilter
@Slf4j
@Data
@Component
@EqualsAndHashCode(callSuper = false)
public class JWTFilter extends OncePerRequestFilter{

    private TokenProvider tokenProvider;
    private String token;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        resolveToken(servletRequest).ifPresent(token -> {
            if (tokenProvider.validateToken(token)) {
                this.token = token;
                Authentication auth = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        });

//        if (resolveToken(servletRequest).isPresent() && tokenProvider.validateToken(token)) {
//            notifyObserver();
//            Authentication auth = tokenProvider.getAuthentication(token);
//            SecurityContextHolder.getContext().setAuthentication(auth);
//        }

        filterChain.doFilter(servletRequest, servletResponse);
    }


    public ClientUser getClientUser() {
        return tokenProvider.getClientUserFromToken(token);
    }


    public Optional<String> resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtProperties.AUTHORIZATION);
        Optional<String> token = Optional.empty();
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProperties.BEARER)) {
            token = Optional.of(bearerToken.substring(7));
        }
        return token;
    }

}


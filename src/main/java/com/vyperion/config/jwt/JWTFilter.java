package com.vyperion.config.jwt;

import com.vyperion.controllers.TokenListener;
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
import java.util.ArrayList;
import java.util.List;

//https://stackoverflow.com/questions/13152946/what-is-onceperrequestfilter
@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter implements TokenNotifier {

    private TokenProvider tokenProvider;
    private String token;
    private List<TokenListener> tokenListeners = new ArrayList<>();

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        token = resolveToken(servletRequest);
        if (token != null && tokenProvider.validateToken(token)) {
            notifyObserver();
            Authentication auth = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(JwtProperties.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProperties.BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }


    @Override
    public void registerObserver(TokenListener tokenListener) {
        tokenListeners.add(tokenListener);
    }

    @Override
    public void removeObserver(TokenListener tokenListener) {
        int i = tokenListeners.indexOf(tokenListener);
        if (i >= 0){
            tokenListeners.remove(i);
        }
    }

    @Override
    public void notifyObserver() {
        for (TokenListener tokenListener : tokenListeners) {
            tokenListener.update(tokenProvider.getClientUserFromToken(token));
        }
    }
}


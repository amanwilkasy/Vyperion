package com.vyperion.config.jwt;


import com.vyperion.controllers.TokenListener;

public interface TokenNotifier {
    void registerObserver(TokenListener tokenListener);
    void removeObserver(TokenListener tokenListener);
    void notifyObserver();
}

package com.vyperion.controllers;


import com.vyperion.dto.client.ClientUser;

public interface TokenListener {
    void update(ClientUser clientUser);
}

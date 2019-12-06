package com.vyperion.dto.client;


import com.vyperion.dto.BaseApiKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClientApiKey extends BaseApiKey {

    private int id;

    private String keyName;

    private String keyValue;

    private String userId;
}

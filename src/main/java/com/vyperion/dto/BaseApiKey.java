package com.vyperion.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.vyperion.dto.client.ClientApiKey;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ClientApiKey.class, name = "clientApiKey"),
        @JsonSubTypes.Type(value = ApiKey.class, name = "apiKey")}
)
public abstract class BaseApiKey {

    private int id;

    private String keyName;

    private String keyValue;

}

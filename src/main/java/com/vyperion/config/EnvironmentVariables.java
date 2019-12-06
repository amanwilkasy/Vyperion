package com.vyperion.config;

public enum EnvironmentVariables {
    PROFILE(System.getenv("PROFILE")),
    DEV_DB(System.getenv("DEV_DB")),
    PROD_DB(System.getenv("PROD_DB")),
    PRODUCTION("production"),
    DEVELOPMENT("development");
    public final String VALUE;
    EnvironmentVariables(String value) {
        this.VALUE = value;
    }
}

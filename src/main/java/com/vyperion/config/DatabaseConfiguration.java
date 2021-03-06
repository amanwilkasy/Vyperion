package com.vyperion.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DatabaseConfiguration {

    @Value("${PROFILE_VALUE}")
    private String profile;

    @Value("${PROD_DB_VALUE}")
    private String prodDB;

    @Value("${DEV_DB_VALUE}")
    private String devDB;

    public boolean isProdEnv(){
        return profile.contains(EnvironmentVariables.PRODUCTION.VALUE);
    }

    @Bean
    public DataSource getDataSource() {
        return isProdEnv() ? prodDb() : devDb();
    }

    private DataSource prodDb() {
        log.info("Using production database");
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(prodDB).build();
    }
    private DataSource devDb() {
        log.info("Using development database");
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url(devDB).build();
    }

}

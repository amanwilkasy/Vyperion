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

    @Value("${PROFILE}")
    private String profile;

    @Value("${PROD_DB}")
    private String prodDB;

    @Value("${DEV_DB}")
    private String devDB;


    @Bean
    public DataSource getDataSource() {
        return profile.contains(EnvironmentVariables.PRODUCTION.VALUE) ? prodDb() : devDb();
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

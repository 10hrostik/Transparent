package com.api.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ R2dbcProperties.class, FlywayProperties.class })
public class FlywayConfig {
  @Bean(initMethod = "migrate")
  public Flyway flyway(FlywayProperties flywayProperties, R2dbcProperties r2dbcProperties) {
    return Flyway.configure()
        .dataSource(
            flywayProperties.getUrl(),
            flywayProperties.getUser(),
            flywayProperties.getPassword()
        )
        .locations(flywayProperties.getLocations().toArray(String[]::new))
        .sqlMigrationPrefix(flywayProperties.getSqlMigrationPrefix())
        .baselineOnMigrate(true)
        .defaultSchema(flywayProperties.getDefaultSchema())
        .load();
  }
}
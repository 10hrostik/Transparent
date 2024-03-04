package com.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;

@Configuration
public class R2dbcConfiguration {

  @Bean
  public R2dbcCustomConversions r2dbcCustomConversions() {
    return R2dbcCustomConversions.of(PostgresDialect.INSTANCE);
  }
}

package com.api.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

@Slf4j
@Component
public class DatabaseConfigurer {
  @Value("${postgres.connection}")
  private String connectionString;

  private final String superUser = "postgres";

  private final String superPassword = "postgres";


  @PostConstruct
  public void init() {
    createDatabase();
    createAuthorities();
  }

  private void createDatabase() {
    try (Connection connection = DriverManager.getConnection(connectionString, superUser, superPassword)) {
      if (checkIfDatabaseNotExists(connection)) {
        String query = """
          CREATE DATABASE transparent;
          REVOKE ALL ON SCHEMA public FROM PUBLIC;
          REVOKE ALL ON DATABASE transparent FROM PUBLIC;
          """;

        Statement statement = connection.createStatement();
        statement.execute(query);
      }
    } catch (SQLException e) {
        log.error(e.toString());
    }
  }

  private void createAuthorities() {
    try (Connection connection = DriverManager.getConnection(connectionString + "transparent", superUser, superPassword)) {
      if (checkIfAuthoritiesNotExists(connection)) {
        String query = """
          CREATE ROLE transparent_super_role;
          GRANT CONNECT ON DATABASE transparent TO transparent_super_role;
          GRANT ALL ON DATABASE transparent TO transparent_super_role;
          CREATE USER flyway_user NOSUPERUSER NOCREATEDB NOCREATEROLE LOGIN ENCRYPTED PASSWORD 'VwJkf4nHIpbl';
          GRANT ALL ON DATABASE transparent TO flyway_user;
          GRANT ALL PRIVILEGES ON DATABASE transparent TO flyway_user;
          CREATE ROLE transparent_service_role;
          GRANT CONNECT ON DATABASE transparent TO transparent_service_role;
          CREATE USER transparent_service NOSUPERUSER NOCREATEDB NOCREATEROLE LOGIN ENCRYPTED PASSWORD 'XfExc8ZTbsze';
          GRANT transparent_service_role TO transparent_service;
          """;

        Statement statement = connection.createStatement();
        statement.execute(query);
      }
    } catch (SQLException e) {
      log.error(e.toString());
    }
  }

  private boolean checkIfDatabaseNotExists(Connection connection) throws SQLException {
    String query = "SELECT * FROM pg_database WHERE datname = 'transparent'";
    Statement statement = connection.createStatement();
    statement.execute(query);
    ResultSet resultSet = statement.getResultSet();

    return !resultSet.next();
  }

  private boolean checkIfAuthoritiesNotExists(Connection connection) throws SQLException {
    String query = "select * from pg_user as users where usename = 'transparent_service' or usename = 'flyway_user'";
    Statement statement = connection.createStatement();
    statement.execute(query);
    ResultSet resultSet = statement.getResultSet();

    return !resultSet.next();
  }
}

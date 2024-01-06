package com.launcher.script;

import lombok.extern.slf4j.Slf4j;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

@Slf4j
public class DatabaseConfigurer {
  private String connectionString;

  private Properties baseProperties;

  private Properties currentProperties;

  private static DatabaseConfigurer instance;

  private final String superUser = "postgres";

  private final String superPassword = "postgres";

  private DatabaseConfigurer() {}

  public static DatabaseConfigurer getInstance() {
    if (instance == null) {
      instance = new DatabaseConfigurer();
    }
    return instance;
  }

  public void init() {
    loadProperties();
    createDatabase();
    createFlywaySchema();
    createAuthorities();
  }

  private void loadProperties() {
    try {
      baseProperties = getProperties("application.properties");
      currentProperties = getProperties("application-" + baseProperties.getProperty("spring.profiles.active") + ".properties");
      connectionString = currentProperties.getProperty("postgres.connection");
    } catch (NullPointerException e) {
      connectionString = baseProperties.getProperty("postgres.connection");
    } catch (Exception e) {
      log.error("Something went wrong\n" + e);
    }
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
          GRANT ALL PRIVILEGES ON SCHEMA flyway TO flyway_user;
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

  private void createFlywaySchema() {
    try (Connection connection = DriverManager.getConnection(connectionString + "transparent", superUser, superPassword)) {
      if (checkIfFlywaySchemeNotExists(connection)) {
        String query = """
          CREATE SCHEMA flyway;
          REVOKE ALL ON SCHEMA flyway FROM PUBLIC;
          """;

        Statement statement = connection.createStatement();
        statement.execute(query);
      }
    } catch (SQLException e) {
      log.error(e.toString());
    }
  }

  private Properties getProperties(String filename) throws IOException {
    String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
    String configPath = rootPath + filename;
    Properties props = new Properties();
    props.load(new FileInputStream(configPath));

    return props;
  }

  private boolean checkIfDatabaseNotExists(Connection connection) throws SQLException {
    String query = "SELECT * FROM pg_database WHERE datname = 'transparent'";
    Statement statement = connection.createStatement();
    statement.execute(query);
    ResultSet resultSet = statement.getResultSet();

    return !resultSet.next();
  }

  private boolean checkIfFlywaySchemeNotExists(Connection connection) throws SQLException {
    String query = "select * from information_schema.schemata where schema_name = 'flyway'";
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

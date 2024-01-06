CREATE SCHEMA IF NOT EXISTS transparent_service;
GRANT USAGE ON SCHEMA transparent_service TO transparent_service_role;
GRANT ALL PRIVILEGES ON SCHEMA transparent_service TO flyway_user;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA transparent_service TO transparent_service_role;
GRANT USAGE ON ALL SEQUENCES IN SCHEMA transparent_service TO transparent_service;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA transparent_service TO transparent_service_role;
ALTER DEFAULT PRIVILEGES IN SCHEMA transparent_service GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO transparent_service_role;
ALTER DEFAULT PRIVILEGES IN SCHEMA transparent_service GRANT USAGE ON SEQUENCES TO transparent_service_role;
ALTER DEFAULT PRIVILEGES IN SCHEMA transparent_service GRANT EXECUTE ON FUNCTIONS TO transparent_service_role;



CREATE USER readonly WITH PASSWORD 'Testpass01';

GRANT CONNECT ON DATABASE osprey01 TO readonly;
GRANT USAGE ON SCHEMA public TO readonly;

GRANT SELECT ON ALL TABLES IN SCHEMA public TO readonly;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
   GRANT SELECT ON TABLES TO readonly;

   -- java user id
CREATE USER ospreyjavausr WITH PASSWORD 'F4&^mfWXqazY';

GRANT USAGE, SELECT ON SEQUENCE batch_job_seq TO ospreyjavausr;
GRANT USAGE, SELECT ON SEQUENCE batch_job_execution_seq TO ospreyjavausr;
GRANT USAGE, SELECT ON SEQUENCE batch_step_execution_seq TO ospreyjavausr;

GRANT CONNECT ON DATABASE osprey01 TO ospreyjavausr;
GRANT USAGE ON SCHEMA public TO ospreyjavausr;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO ospreyjavausr;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
   GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO ospreyjavausr;
   
   -- node user id
CREATE USER ospreynodeusr WITH PASSWORD '@7wTASPfJ&gh';

GRANT CONNECT ON DATABASE osprey01 TO ospreynodeusr;
GRANT USAGE ON SCHEMA public TO ospreynodeusr;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO ospreynodeusr;

ALTER DEFAULT PRIVILEGES IN SCHEMA public
   GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO ospreynodeusr;
   
   
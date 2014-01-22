-- Create user
CREATE ROLE cp_user LOGIN
  ENCRYPTED PASSWORD 'md5512481dca54307ac2855b3a13f751ecd'
  NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION CONNECTION LIMIT 25;

-- Create database
CREATE DATABASE cp
  WITH OWNER = cp_user
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English_United States.1252'
       LC_CTYPE = 'English_United States.1252'
       CONNECTION LIMIT = -1;
GRANT ALL ON DATABASE cp TO cp_user;
REVOKE ALL ON DATABASE cp FROM public;

-- Create sequences
CREATE SEQUENCE users_sequence
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE users_sequence
  OWNER TO postgres;
GRANT ALL ON TABLE users_sequence TO postgres;
GRANT ALL ON TABLE users_sequence TO public;
    
CREATE SEQUENCE credentials_sequence
	START WITH 1
	INCREMENT BY 1
	NO MINVALUE
	NO MAXVALUE
	CACHE 1;
ALTER TABLE credentials_sequence
  OWNER TO postgres;
GRANT ALL ON TABLE credentials_sequence TO postgres;
GRANT ALL ON TABLE credentials_sequence TO public;

CREATE SEQUENCE tokens_sequence
	START WITH 1
	INCREMENT BY 1
	NO MINVALUE
	NO MAXVALUE
	CACHE 1;
ALTER TABLE tokens_sequence
  OWNER TO postgres;
GRANT ALL ON TABLE tokens_sequence TO postgres;
GRANT ALL ON TABLE tokens_sequence TO public;

-- Create tables
CREATE TABLE users
(
  id bigint NOT NULL DEFAULT nextval('users_sequence'::regclass),
  email character varying(255) NOT NULL,
  fname character varying(255) NOT NULL,
  lname character varying(255) NOT NULL,
  heardhow integer NOT NULL,
  isdeleted boolean NOT NULL DEFAULT false,
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT users_email_key UNIQUE (email)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE credentials
(
  id bigint NOT NULL DEFAULT nextval('credentials_sequence'::regclass),
  userid bigint NOT NULL,
  passhash character varying(255) NOT NULL,
  verifycode character varying(255),
  isvalidated boolean NOT NULL DEFAULT false,
  istemp boolean NOT NULL DEFAULT true, 
  isdeleted boolean NOT NULL DEFAULT false,
  CONSTRAINT credentials_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE tokens
(
  id bigint NOT NULL DEFAULT nextval('tokens_sequence'::regclass),
  -- Store as a UUID?  Probably!
  -- base64 encode before sending?  Probably!
  token character varying(255) NOT NULL,
  expires timestamp with time zone
)
WITH (
  OIDS=FALSE
);  

-- Grants
ALTER TABLE users  OWNER TO cp_user;
GRANT ALL ON TABLE users TO cp_user;

ALTER TABLE credentials OWNER TO cp_user;
GRANT ALL ON TABLE credentials TO cp_user;

ALTER TABLE tokens OWNER TO cp_user;
GRANT ALL ON TABLE tokens TO cp_user;
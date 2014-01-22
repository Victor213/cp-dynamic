CREATE TYPE areaType AS ENUM ('client', 'server');
CREATE TABLE bugs
(
  id bigint NOT NULL DEFAULT nextval('bug_sequence'::regclass),
  version character varying(255) NOT NULL,
  build character varying(255) NOT NULL,
  area areaType NOT NULL,
  priority integer NOT NULL,
  howreproduce character varying(255) NOT NULL,
  expectedbehavior character varying(255) NOT NULL,
  observedbehavior character varying(255) NOT NULL,
  fixed boolean NOT NULL DEFAULT false,
  CONSTRAINT bugs_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
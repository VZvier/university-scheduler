DROP TABLE IF EXISTS COURSES CASCADE;

CREATE TABLE IF NOT EXISTS COURSES
(
ID INTEGER NOT NULL PRIMARY KEY,
Name character(18) COLLATE pg_catalog.default NOT NULL UNIQUE,
Description character(75) COLLATE pg_catalog.default NOT NULL
)
	
	TABLESPACE pg_default;

	ALTER TABLE IF EXISTS COURSES
    	OWNER to "Admin";
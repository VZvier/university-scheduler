DROP TABLE IF EXISTS STUDENTS CASCADE;

CREATE TABLE IF NOT EXISTS STUDENTS
(
	ID INTEGER NOT NULL,
    Group_ID INTEGER REFERENCES GROUPS(ID),
    First_Name character(10) NOT NULL,
    Last_Name character(10) NOT NULL,
    User_ID INTEGER,
    CONSTRAINT "STUDENT_pkey" PRIMARY KEY (ID)
)

	TABLESPACE pg_default;

	ALTER TABLE IF EXISTS STUDENTS
   		OWNER to "Admin";
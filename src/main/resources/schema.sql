DROP TABLE IF EXISTS teachers;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS classes;

CREATE TABLE IF NOT EXISTS classes
(
    id SERIAL8 PRIMARY KEY,
    study_year INT NOT NULL,
    mnemocode VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS students
(
    id SERIAL8 PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    surname VARCHAR(32) NOT NULL,
    patronymic VARCHAR(32) NOT NULL,
    birth_year INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    class_id BIGINT,

    CONSTRAINT fk_students_to_classes FOREIGN KEY (class_id) REFERENCES classes (id)
        ON DELETE SET NULL ON UPDATE SET NULL
);

CREATE TABLE IF NOT EXISTS teachers
(
    id SERIAL8 PRIMARY KEY,
    surname VARCHAR(32) NOT NULL,
    name VARCHAR(32) NOT NULL,
    patronymic VARCHAR(32) NOT NULL,
    birth_year INT NOT NULL,
    gender VARCHAR(10) NOT NULL,
    main_subject VARCHAR(32) NOT NULL,
    class_id BIGINT,

    CONSTRAINT fk_teachers_to_classes FOREIGN KEY (class_id) REFERENCES classes (id)
        ON DELETE SET NULL ON UPDATE SET NULL
);
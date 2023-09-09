-- liquibase formatted sql

-- changeset artem:2023-09-09-std_ind
CREATE INDEX student_name_index ON student (name);

-- changeset artem:2023-09-09-foc_ind
CREATE INDEX faculty_ind ON faculty (name, color);
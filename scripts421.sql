alter table student
    add constraint age_constraint check (age > 15)

alter table student
    add constaint name_unique unique (name);

alter table student
    alter column name set not null;

alter TABLE faculty
    add constraint name_color_unique unique (name, color);

alter TABLE student
    alter column age set default 20;
create table сars (
                   id serial PRIMARY KEY,
                   brand VARCHAR not null,
                   model VARCHAR not null,
                   price DECIMAL not null
                   )

create table Person (
                     person_id serial PRIMARY KEY,
                     name VARCHAR not null,
                     age INT not null,
                     has_license BOOLEAN not null,
                     car_id INT REFERENCES сars (id) not null
                     )
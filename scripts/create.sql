create table post(
    id serial primary key,
    name varchar(300),
    text text,
    author varchar,
    created timestamp,
    link varchar(300) unique
);
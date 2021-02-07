create table post(
    id serial primary key,
    name varchar(300),
    text varchar(60000),
    link varchar(300) unique,
    created date
);
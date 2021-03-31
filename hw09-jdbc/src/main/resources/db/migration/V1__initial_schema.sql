create table Client
(
    id   bigserial not null primary key,
    name varchar(50),
    age  int
);
create table Manager
(
    no    bigserial not null primary key,
    label varchar(50)
);
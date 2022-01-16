create table if not exists test
(
    id   int,
    name varchar(50)
);

drop table client;
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

drop table manager;
create table manager
(
    id   bigserial not null primary key,
    label varchar(50),
    param1 varchar(50)
);

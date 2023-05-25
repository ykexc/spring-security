create table _user
(
    id        int auto_increment
        primary key,
    firstname varchar(255) null,
    lastname  varchar(255) null,
    email     varchar(255) not null,
    password  varchar(255) null,
    role      varchar(255) null,
    constraint email
        unique (email)
);


create table usuarios(

    id bigint not null generated always as identity,
    login varchar(100) not null,
    senha varchar(255) not null,

    primary key(id)
);
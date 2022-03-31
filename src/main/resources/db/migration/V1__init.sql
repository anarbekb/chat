create table messages
(
    id      bigserial primary key,
    type    varchar(255) not null,
    content varchar(255),
    sender  varchar(10) not null,
    created_at timestamp not null
);
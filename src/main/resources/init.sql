drop table if exists task;

drop table if exists worker;

create table if not exists worker
(
    id       bigserial
        primary key,
    name     text not null,
    position text,
    avatar   text
);

create table if not exists task
(
    id          bigserial
        primary key,
    title       text                              not null,
    description text,
    status      text      default 'CREATED'::text not null,
    time        timestamp default now(),
    performer   bigint default null
        constraint task_worker_id_fk
            references worker(id)
            on update cascade on delete cascade
);

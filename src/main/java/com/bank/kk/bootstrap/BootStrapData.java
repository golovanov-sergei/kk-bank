package com.bank.kk.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {
    private final JdbcTemplate jdbcTemplate;

    public BootStrapData(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        jdbcTemplate.execute(
                "create table if not exists worker\n" +
                        "(\n" +
                        "    id       bigserial\n" +
                        "        primary key,\n" +
                        "    name     text not null,\n" +
                        "    position text,\n" +
                        "    avatar   bytea\n" +
                        ");\n" +
                        "\n" +
                        "create table if not exists task\n" +
                        "(\n" +
                        "    id          bigserial\n" +
                        "        primary key,\n" +
                        "    title       text                              not null,\n" +
                        "    description text,\n" +
                        "    status      text      default 'CREATED'::text not null,\n" +
                        "    time        timestamp default now(),\n" +
                        "    performer   bigint default null\n" +
                        "        constraint task_worker_id_fk\n" +
                        "            references worker(id)\n" +
                        "            on update cascade on delete cascade\n" +
                        ");");
    }
}

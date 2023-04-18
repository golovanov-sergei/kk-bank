package com.bank.kk.service.mapper;

import com.bank.kk.domain.Worker;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerRowMapper implements RowMapper<Worker> {
    @Override
    public Worker mapRow(ResultSet rs, int rowNum) throws SQLException {
        Worker worker = new Worker();
        worker.setId(rs.getLong("id"));
        worker.setName(rs.getString("name"));
        worker.setPosition(rs.getString("position"));
        worker.setAvatar(rs.getString("avatar"));
        return worker;
    }
}

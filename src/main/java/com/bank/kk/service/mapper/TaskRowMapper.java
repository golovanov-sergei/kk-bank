package com.bank.kk.domain.mapper;

import com.bank.kk.domain.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setDescription(rs.getString("description"));
        task.setTime(rs.getTimestamp("time").toLocalDateTime());
        task.setWorkerId(rs.getLong("performer"));
        return task;
    }
}

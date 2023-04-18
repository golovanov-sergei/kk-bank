package com.bank.kk.service.mapper;

import com.bank.kk.domain.Task;
import com.bank.kk.domain.enums.TaskStatus;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TaskRowBriefMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setTitle(rs.getString("title"));
        task.setTaskStatus(TaskStatus.valueOf(rs.getString("status")));
        return task;
    }
}

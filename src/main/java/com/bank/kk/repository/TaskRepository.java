package com.bank.kk.repository;

import com.bank.kk.domain.Task;
import com.bank.kk.domain.enums.TaskStatus;
import com.bank.kk.service.TaskQueueService;
import com.bank.kk.service.mapper.TaskRowBriefMapper;
import com.bank.kk.service.mapper.TaskRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;
    private final TaskQueueService taskQueueService;
    private static final int MAX_THREADS = 3;

    public TaskRepository(JdbcTemplate jdbcTemplate, TaskQueueService taskQueueService) {
        this.jdbcTemplate = jdbcTemplate;
        this.taskQueueService = taskQueueService;
    }

    public void createTask(Task task) {
        if (task.getTaskStatus() == null)
            task.setTaskStatus(TaskStatus.CREATED);
        if (task.getTime() == null)
            task.setTime(LocalDateTime.now());
        taskQueueService.add(task);
    }

    private Task save(Task task) {
        String sql = "INSERT INTO task (title, description, status, time) " +
                "VALUES (?, ?, ?, ?) RETURNING id, title, description, status, time, performer";

        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(),
                task.getTitle(),
                task.getDescription(),
                task.getTaskStatus().name(),
                task.getTime()
        );
    }

    public void saveTasksToDB() {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
        while (taskQueueService.size() > 0) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    save(taskQueueService.poll());
                }
            });
        }
        executorService.shutdown();
    }

    @Transactional
    public Task updateTask(Task task) {
        String sql = "UPDATE task SET title = COALESCE(?, title), description = ?, status = COALESCE(?, title), time = COALESCE(?, time) " +
                "WHERE id = ? RETURNING id, title, description, status, time, performer";

        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(),
                task.getTitle(),
                task.getDescription(),
                task.getTaskStatus().name(),
                task.getTime(),
                task.getId()
        );
    }

    @Transactional(readOnly = true)
    public Task getTaskById(Long id) {
        String sql = "SELECT * FROM task WHERE task.id = ?";
        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), id);
    }

    @Transactional(readOnly = true)
    public Task getTaskDescription(Long id) {
        String sql = "select t.id, t.status, t.title, t.description, t.time, w.name " +
                "from task t left join worker w on w.id = t.performer " +
                "where t.id=?";
        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), id);
    }

    @Transactional(readOnly = true)
    public List<Task> findAll() {
        String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    @Transactional
    public Task setPerformer(Task task, Long performerId) {
        Boolean performerExists = jdbcTemplate.queryForObject(
                "SELECT count(*)>0 FROM worker where id = ?;",
                Boolean.class,
                performerId
        );
        if (performerExists == null)
            throw new RuntimeException("SQL select returned null");

        if (performerExists) {
            String sql = "UPDATE task SET performer = ? " +
                    "WHERE id = ? RETURNING id, title, description, status, time, performer";

            return jdbcTemplate.queryForObject(sql, new TaskRowMapper(),
                    performerId,
                    task.getId()
            );
        } else
            throw new RuntimeException("Worker not found");
    }

    @Transactional(readOnly = true)
    public List<Task> findAllBrief() {
        String sql = "SELECT id, title, status FROM task";
        return jdbcTemplate.query(sql, new TaskRowBriefMapper());
    }

    @Transactional
    public Boolean deleteById(Long id) {
        String sql = "DELETE FROM task WHERE id=?";
        return jdbcTemplate.update(sql, id) == 1;
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByPerformer(Long id) {
        String sql = "SELECT  id, title, status  FROM task WHERE performer = ?";
        return jdbcTemplate.query(sql, new TaskRowBriefMapper(), id);
    }
}

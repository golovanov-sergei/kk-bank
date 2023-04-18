package com.bank.kk.repository;

import com.bank.kk.domain.Worker;
import com.bank.kk.service.mapper.WorkerRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class WorkerRepository {
    private final JdbcTemplate jdbcTemplate;

    public WorkerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Worker createWorker(Worker worker) {
        String sql = "INSERT INTO worker (name, position, avatar) " +
                "VALUES (?, ?, ?) RETURNING ID, NAME, POSITION, AVATAR";

        return jdbcTemplate.queryForObject(sql, new WorkerRowMapper(),
                worker.getName(),
                worker.getPosition(),
                worker.getAvatar()
        );
    }

    @Transactional
    public Worker updateWorker(Worker worker) {
        String sql = "UPDATE worker SET name = COALESCE(?, name), position = ?, avatar = ? " +
                "WHERE id = ? RETURNING id, name, position, avatar";

        return jdbcTemplate.queryForObject(sql, new WorkerRowMapper(),
                worker.getName(),
                worker.getPosition(),
                worker.getAvatar(),
                worker.getId()
        );
    }

    @Transactional(readOnly = true)
    public Worker getWorkerById(Long id) {
        String sql = "SELECT * FROM worker WHERE worker.id = ?";
        return jdbcTemplate.queryForObject(sql, new WorkerRowMapper(), id);
    }

    @Transactional(readOnly = true)
    public List<Worker> findAll() {
        String sql = "SELECT * FROM worker";
        return jdbcTemplate.query(sql, new WorkerRowMapper());
    }

    @Transactional
    public Boolean deleteById(Long id) {
        String sql = "DELETE FROM worker WHERE id=?";
        return (jdbcTemplate.update(sql, id) == 1);
    }
}

package com.bank.kk.domain;

import com.bank.kk.domain.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private long id;
    private String title;
    private String description;
    private TaskStatus taskStatus;
    private LocalDateTime time;
    private long workerId;

    public Task() {
    }

    public Task(long id, String title, String description, TaskStatus taskStatus, LocalDateTime time, long workerId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskStatus = taskStatus;
        this.time = time;
        this.workerId = workerId;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", workerId=" + workerId +
                '}';
    }
}

package com.bank.kk.service;

import com.bank.kk.domain.Task;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class TaskQueueService {
    private final Queue<Task> queue;
    private static final int MAX_SIZE = 5;

    public TaskQueueService() {
        this.queue = new ArrayBlockingQueue<>(MAX_SIZE);
    }

    public void add(Task task) {
        this.queue.add(task);

    }

    public Task poll(){
        return this.queue.poll();
    }

    public int size(){
        return queue.size();
    }
}

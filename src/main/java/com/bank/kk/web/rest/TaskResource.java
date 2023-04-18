package com.bank.kk.web.rest;

import com.bank.kk.domain.Task;
import com.bank.kk.repository.TaskRepository;
import com.bank.kk.service.TaskQueueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskResource {
    private final TaskRepository taskRepository;
    private final TaskQueueService taskQueueService;

    public TaskResource(TaskRepository taskRepository, TaskQueueService taskQueueService) {
        this.taskRepository = taskRepository;
        this.taskQueueService = taskQueueService;
    }

    @PostMapping("/createTask")
    public void createTask(@RequestBody Task task) {
        taskRepository.createTask(task);
    }

    @PutMapping("/updateTask")
    public Task updateTask(@RequestBody Task task) {
        return taskRepository.updateTask(task);
    }

    @GetMapping("/task")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/task/{id}")
    public Task getTask(@PathVariable(value = "id", required = true) Long id) {
        return taskRepository.getTaskById(id);
    }

    @GetMapping("/taskBrief")
    public List<Task> getAllTasksBrief() {
        return taskRepository.findAllBrief();
    }

    @PostMapping("/task/setPerformer/{id}")
    public ResponseEntity<?> setTaskPerformer(@RequestBody Task task, @PathVariable(value = "id") Long id) {
        try {
            return ResponseEntity.ok().body(taskRepository.setPerformer(task, id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error setting up performer");
        }
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(value = "id", required = true) Long id) {
        if (taskRepository.deleteById(id))
            return ResponseEntity.ok().body(String.format("Deleted task id = %s", id));
        return ResponseEntity.badRequest().body(String.format("Failed deleting task id = %s", id));
    }

    @GetMapping("/task/worker/{id}")
    public List<Task> getTasksByPerformer(@PathVariable(value = "id", required = true) Long id) {
        return taskRepository.findAllByPerformer(id);
    }

    @GetMapping(value = "/task/save")
    public void saveTasksToDB() {
        taskRepository.saveTasksToDB();
    }
}

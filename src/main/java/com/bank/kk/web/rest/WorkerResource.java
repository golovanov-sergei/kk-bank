package com.bank.kk.web.rest;

import com.bank.kk.domain.Worker;
import com.bank.kk.repository.WorkerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkerResource {
    private final WorkerRepository workerRepository;

    public WorkerResource(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @PostMapping("/createWorker")
    public Worker createWorker(@RequestBody Worker worker) {
        return workerRepository.createWorker(worker);
    }
    @PutMapping("/updateWorker")
    public Worker updateWorker(@RequestBody Worker worker) {
        return workerRepository.updateWorker(worker);
    }

    @GetMapping("/worker")
    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    @GetMapping("/worker/{id}")
    public Worker getWorker(@PathVariable(value = "id", required = true) Long id) {
        return workerRepository.getWorkerById(id);
    }

    @DeleteMapping("/worker/{id}")
    public ResponseEntity<String> deleteWorker(@PathVariable(value = "id", required = true) Long id) {
        if(workerRepository.deleteById(id))
            return ResponseEntity.ok().body(String.format("Deleted task id = %s", id));
        return ResponseEntity.badRequest().body(String.format("Failed deleting task id = %s", id));
    }
}

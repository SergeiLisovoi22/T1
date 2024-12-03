package com.example.web.controller;

import com.example.service.TaskService;
import com.example.web.controller.model.RequestTaskDTO;
import com.example.web.controller.model.ResponseTaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseTaskDTO> getTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.getTask(taskId));
    }

    @GetMapping
    public ResponseEntity<List<ResponseTaskDTO>> getAllTask() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping
    public ResponseEntity<ResponseTaskDTO> createTask(@RequestBody RequestTaskDTO dto) {
        return ResponseEntity.ok(taskService.createTask(dto));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ResponseTaskDTO> updateTask(@PathVariable Long taskId, @RequestBody RequestTaskDTO dto) {
        return ResponseEntity.ok(taskService.updateTask(taskId, dto));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }
}

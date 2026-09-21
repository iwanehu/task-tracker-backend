package task_tracker.demo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import task_tracker.demo.dto.TaskRequestDTO;
import task_tracker.demo.dto.TaskResponseDTO;
import task_tracker.demo.mapper.TaskMapper;
import task_tracker.demo.model.Task;
import task_tracker.demo.model.TaskPriority;
import task_tracker.demo.model.TaskStatus;
import task_tracker.demo.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    public List<TaskResponseDTO> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) String sort
    ) {

        return taskService
                .getTaskFiltered(status, priority, sort)
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(
            @PathVariable Long id
    ) {

        Task task = taskService.getTaskById(id);

        return ResponseEntity.ok(
                taskMapper.toDto(task)
        );
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @Valid @RequestBody TaskRequestDTO taskDTO
    ) {

        Task taskEntity = taskMapper.toEntity(taskDTO);

        Task createdTask = taskService.createTask(taskEntity);

        return new ResponseEntity<>(
                taskMapper.toDto(createdTask),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDTO taskDetails
    ) {

        Task taskChanges = taskMapper.toEntity(taskDetails);

        Task updatedTask =
                taskService.updateTask(id, taskChanges);

        return ResponseEntity.ok(
                taskMapper.toDto(updatedTask)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long id
    ) {

        taskService.deleteTaskByid(id);

        return ResponseEntity.noContent().build();
    }
}
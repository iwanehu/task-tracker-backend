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

@RestController  //indica que es un controldor rest
@RequestMapping("/api/tasks")  //Define la ruta base
@CrossOrigin(origins ="*")
public class TaskController {


    @Autowired  //inyectar dependencias automaticamente sin usar new
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    //-Get task

    @GetMapping  //Metodo controlador que responde a peticiones hhtpget
    public List<TaskResponseDTO> getAlTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) String sort) {

        // convertir la lista entidades a la lista de DTO antes de enviarla
        return taskService.getTaskFiltered(status, priority,sort).stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());    }

    //GET TASK BY ID
    @GetMapping("/{id}")   //ResponseEntity<T> es un envoltorio que Spring usa para devolver:un código HTTP,header,un body
    public ResponseEntity<TaskResponseDTO> getTaskByid(@PathVariable("id") String id){
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(taskMapper.toDto(task));
    }





    //Create Task
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@Valid  @RequestBody TaskRequestDTO taskDTO){//VALID ACTIVA @NOTBLANCK
        Task taskEntity = taskMapper.toEntity(taskDTO);
        Task createdTask = taskService.createTask(taskEntity);
        return  new ResponseEntity<>(taskMapper.toDto(createdTask),HttpStatus.CREATED);
    }

    //UPDATE TASK
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask( @PathVariable("id") String id,@Valid @RequestBody TaskRequestDTO taskDetails){

        Task taskChanges = taskMapper.toEntity(taskDetails);

        Task updatedTask = taskService.updateTask(id, taskChanges);
        return ResponseEntity.ok(taskMapper.toDto(updatedTask));
              }


     @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable("id") String id){

        taskService.deleteTaskByid(id);
        return ResponseEntity.ok().build();


     }









}

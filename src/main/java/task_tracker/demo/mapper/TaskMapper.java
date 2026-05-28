package task_tracker.demo.mapper;


import org.springframework.stereotype.Component;
import task_tracker.demo.dto.TaskRequestDTO;
import task_tracker.demo.dto.TaskResponseDTO;
import task_tracker.demo.model.Task;
import task_tracker.demo.model.TaskPriority;
import task_tracker.demo.model.TaskStatus;

@Component
public class TaskMapper {

    //Convertir DTO  a entidad
    public Task toEntity(TaskRequestDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        //si no viene status lo asignamos a pending
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : TaskStatus.PENDING);
        task.setPriority(dto.getPriority() !=null ? dto.getPriority() : TaskPriority.MEDIUM);
        return task;
    }

    //Entity → DTO  devolver al cliente en la API

    public TaskResponseDTO  toDto(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdateAt(task.getUpdateAt());
        dto.setPriority(task.getPriority());

        return dto;
    }


    //
}

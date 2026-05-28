package task_tracker.demo.repository;

import task_tracker.demo.model.Task;

import org.springframework.data.mongodb.repository.MongoRepository;
import task_tracker.demo.model.TaskPriority;
import task_tracker.demo.model.TaskStatus;

import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {

   //Filtros por usurio globales
    List<Task> findByUserIdOrderByCreatedAtDesc(String userId);
    List<Task> findByUserIdOrderByCreatedAtAsc(String userId);

    //Filtro por usuario + Estatus

    List<Task> findByUserIdAndStatusOrderByCreatedAtDesc(String userId, TaskStatus status);
    List<Task> findByUserIdAndStatusOrderByCreatedAtAsc(String userId, TaskStatus status);


    //Filtrar por usuario y prioridad
    List<Task> findByUserIdAndPriorityOrderByCreatedAtDesc(String userId, TaskPriority priority);
    List<Task> findByUserIdAndPriorityOrderByCreatedAtAsc(String userId, TaskPriority priority);


    //FIltro maestro :Usuario + Estatus + Prioridad

    List<Task> findByUserIdAndStatusAndPriorityOrderByCreatedAtDesc(String userId, TaskStatus status, TaskPriority priority);
    List<Task> findByUserIdAndStatusAndPriorityOrderByCreatedAtAsc(String userId, TaskStatus status, TaskPriority priority);


}

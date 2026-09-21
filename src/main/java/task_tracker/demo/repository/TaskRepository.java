package task_tracker.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task_tracker.demo.model.Task;
import task_tracker.demo.model.TaskPriority;
import task_tracker.demo.model.TaskStatus;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

 // Usuario
 List<Task> findByUserIdOrderByCreatedAtDesc(Long userId);
 List<Task> findByUserIdOrderByCreatedAtAsc(Long userId);

 // Usuario + status
 List<Task> findByUserIdAndStatusOrderByCreatedAtDesc(
         Long userId,
         TaskStatus status
 );

 List<Task> findByUserIdAndStatusOrderByCreatedAtAsc(
         Long userId,
         TaskStatus status
 );

 // Usuario + priority
 List<Task> findByUserIdAndPriorityOrderByCreatedAtDesc(
         Long userId,
         TaskPriority priority
 );

 List<Task> findByUserIdAndPriorityOrderByCreatedAtAsc(
         Long userId,
         TaskPriority priority
 );

 // Usuario + status + priority
 List<Task> findByUserIdAndStatusAndPriorityOrderByCreatedAtDesc(
         Long userId,
         TaskStatus status,
         TaskPriority priority
 );

 List<Task> findByUserIdAndStatusAndPriorityOrderByCreatedAtAsc(
         Long userId,
         TaskStatus status,
         TaskPriority priority
 );
}
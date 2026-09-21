package task_tracker.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import task_tracker.demo.model.Task;
import task_tracker.demo.model.TaskPriority;
import task_tracker.demo.model.TaskStatus;
import task_tracker.demo.model.User;
import task_tracker.demo.repository.TaskRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    private Long getAuthenticatedUserId() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return user.getId();
    }

    public Task createTask(Task task) {
        task.setUserId(getAuthenticatedUserId());
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Task not found by id: " + id)
                );

        if (!task.getUserId().equals(getAuthenticatedUserId())) {
            throw new SecurityException(
                    "User not authorized to access this task"
            );
        }

        return task;
    }

    public boolean deleteTaskByid(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Task not found by id: " + id)
                );

        if (!task.getUserId().equals(getAuthenticatedUserId())) {
            throw new SecurityException(
                    "User not authorized to delete this task"
            );
        }

        taskRepository.delete(task);
        return true;
    }

    public List<Task> getTaskFiltered(
            TaskStatus status,
            TaskPriority priority,
            String sortDirection
    ) {

        Long userId = getAuthenticatedUserId();

        boolean isAsc = "asc".equalsIgnoreCase(sortDirection);

        if (status != null && priority != null) {


            return isAsc
                    ? taskRepository
                      .findByUserIdAndStatusAndPriorityOrderByCreatedAtAsc(
                              userId,
                              status,
                              priority
                      )
                    : taskRepository
                      .findByUserIdAndStatusAndPriorityOrderByCreatedAtDesc(
                              userId,
                              status,
                              priority
                      );
        }

        if (status != null) {

            return isAsc
                    ? taskRepository
                      .findByUserIdAndStatusOrderByCreatedAtAsc(
                              userId,
                              status
                      )
                    : taskRepository
                      .findByUserIdAndStatusOrderByCreatedAtDesc(
                              userId,
                              status
                      );
        }

        if (priority != null) {

            return isAsc
                    ? taskRepository
                      .findByUserIdAndPriorityOrderByCreatedAtAsc(
                              userId,
                              priority
                      )
                    : taskRepository
                      .findByUserIdAndPriorityOrderByCreatedAtDesc(
                              userId,
                              priority
                      );
        }

        return isAsc
                ? taskRepository
                  .findByUserIdOrderByCreatedAtAsc(userId)
                : taskRepository
                  .findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Task updateTask(Long id, Task details) {

        Task existing = taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Task not found by id: " + id
                        )
                );

        if (!existing.getUserId().equals(getAuthenticatedUserId())) {
            throw new SecurityException(
                    "User not authorized to update task"
            );
        }

        existing.setTitle(details.getTitle());
        existing.setDescription(details.getDescription());

        if (details.getStatus() != null) {
            existing.setStatus(details.getStatus());
        }

        if (details.getPriority() != null) {
            existing.setPriority(details.getPriority());
        }

        return taskRepository.save(existing);
    }
}
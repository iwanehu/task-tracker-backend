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

    // Método helper para sacar el id del usuario autenticado en la sesión actual
    private String getAuthenticatedUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }

    // 1. CREAR (Asigna el dueño automáticamente)
    public Task createTask(Task task) {
        task.setUserId(getAuthenticatedUserId());
        return taskRepository.save(task);
    }

    // 2. OBTENER POR ID (Evita que veas IDs de otros usuarios)
    public Task getTaskById(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found by id: " + id));

        if (!task.getUserId().equals(getAuthenticatedUserId())) {
            throw new SecurityException("User not authorized to access this task");
        }
        return task;
    }

    // 3. ELIMINAR (Evita que borres tareas ajenas)
    public boolean deleteTaskByid(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found by id: " + id));

        if (!task.getUserId().equals(getAuthenticatedUserId())) {
            throw new SecurityException("User not authorized to delete this task");
        }

        taskRepository.delete(task);
        return true;
    }

    // 4. FILTRAR MULTIUSUARIO (Corregido el método descendente combinado)
    public List<Task> getTaskFiltered(TaskStatus status, TaskPriority priority, String sortDirection) {
        String userId = getAuthenticatedUserId();
        boolean isAsc = "asc".equalsIgnoreCase(sortDirection);

        if (status != null && priority != null) {
            return isAsc ? taskRepository.findByUserIdAndStatusAndPriorityOrderByCreatedAtAsc(userId, status, priority)
                    : taskRepository.findByUserIdAndStatusAndPriorityOrderByCreatedAtDesc(userId, status, priority); // <-- Corregido aquí
        }
        if (status != null) {
            return isAsc ? taskRepository.findByUserIdAndStatusOrderByCreatedAtAsc(userId, status)
                    : taskRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, status);
        }
        if (priority != null) {
            return isAsc ? taskRepository.findByUserIdAndPriorityOrderByCreatedAtAsc(userId, priority)
                    : taskRepository.findByUserIdAndPriorityOrderByCreatedAtDesc(userId, priority);
        }

        return isAsc ? taskRepository.findByUserIdOrderByCreatedAtAsc(userId)
                : taskRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    // 5. ACTUALIZAR (Corregido error de llaves y paréntesis)
    public Task updateTask(String id, Task details) {
        Task existing = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found by id: " + id)); // <-- Corregida sintaxis

        if (!existing.getUserId().equals(getAuthenticatedUserId())) {
            throw new SecurityException("User not authorized to update task");
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
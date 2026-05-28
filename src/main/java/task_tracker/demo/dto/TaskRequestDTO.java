package task_tracker.demo.dto;

import jakarta.validation.constraints.NotBlank;
import task_tracker.demo.model.TaskPriority;
import task_tracker.demo.model.TaskStatus;

public class TaskRequestDTO {


    @NotBlank(message = "The title can not be null")
    private String title;

    @NotBlank(message = "The description can not be null")
    private String description;

    private TaskStatus status;
    private TaskPriority priority;

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public TaskStatus getStatus() {return status;}
    public void setStatus(TaskStatus status) {this.status = status;}
    public TaskPriority getPriority() {return priority;}
    public void setPriority(TaskPriority priority) {this.priority = priority;}

}

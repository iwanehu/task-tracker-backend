package task_tracker.demo.model;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = "tt")
@Data //Lombook genera getter y setter,toString2
public class Task {

    @Id
    private String id;

    private String userId;
    private String title;
    private String description;
    private TaskStatus status = TaskStatus.PENDING;
    private TaskPriority priority = TaskPriority.MEDIUM;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updateAt;













}

package task_tracker.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import task_tracker.demo.service.TaskService;


@Component
public class ConsoleApp implements CommandLineRunner {

    private final TaskService taskService;

    public ConsoleApp(TaskService taskService) {
        this.taskService = taskService;
    }


    @Override
    public void run(String... args) {

    }
}

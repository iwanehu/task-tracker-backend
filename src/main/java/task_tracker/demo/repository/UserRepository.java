package task_tracker.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import task_tracker.demo.model.User;

import java.util.Optional;

public interface UserRepository  extends MongoRepository<User,String> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}

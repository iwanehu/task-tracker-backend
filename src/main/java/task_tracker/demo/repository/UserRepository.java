package task_tracker.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task_tracker.demo.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
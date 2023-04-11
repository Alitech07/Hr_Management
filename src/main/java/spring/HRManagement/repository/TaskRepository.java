package spring.HRManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.HRManagement.entity.Task;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}

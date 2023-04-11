package spring.HRManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.HRManagement.entity.TaskStatus;
import spring.HRManagement.entity.enums.TaskStatusName;

public interface TaskStatusRepository extends JpaRepository<TaskStatus,Integer> {
    TaskStatus getTaskStatusesByTaskStatusName(TaskStatusName taskStatusName);
}

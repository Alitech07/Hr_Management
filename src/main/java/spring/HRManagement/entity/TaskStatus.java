package spring.HRManagement.entity;

import lombok.Data;
import spring.HRManagement.entity.enums.TaskStatusName;

import javax.persistence.*;

@Data
@Entity
public class TaskStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TaskStatusName taskStatusName;
}

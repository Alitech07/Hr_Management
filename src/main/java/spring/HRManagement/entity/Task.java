package spring.HRManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue
    private UUID id;

    private String title;
    private String comment;
    private Date completedAt;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private TaskStatus taskStatus;
}

package net.unit8.rascaloid.entity;

import lombok.Data;
import org.seasar.doma.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tasks")
@Data
public class Task implements Serializable {
    @Id
    @Column(name = "task_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Identity<Task> id;

    @Column(name = "project_id")
    private Identity<Project> projectId;
    private String subject;
    private String description;
    @Column(name = "estimated_hours")
    private BigDecimal estimatedHours;

    @Column(name = "status_id")
    private Identity<TaskStatus> statusId;

    @Column(insertable = false, updatable = false)
    private String statusName;
}

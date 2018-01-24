package net.unit8.rascaloid.entity;

import lombok.Data;
import lombok.Value;
import org.seasar.doma.*;

import java.io.Serializable;

@Entity
@Table(name = "task_status")
@Data
public class TaskStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Identity<TaskStatus> id;
    private String name;
    private Long position;
}

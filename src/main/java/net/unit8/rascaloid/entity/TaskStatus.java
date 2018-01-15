package net.unit8.rascaloid.entity;

import lombok.Value;
import org.seasar.doma.Column;
import org.seasar.doma.Embeddable;

import java.io.Serializable;

@Embeddable
@Value
public class TaskStatus implements Serializable {
    @Column(name = "status_id")
    private final Identity<TaskStatus> id;
    private final String name;
    private final Long position;
}

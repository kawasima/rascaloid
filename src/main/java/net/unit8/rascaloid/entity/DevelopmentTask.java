package net.unit8.rascaloid.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.seasar.doma.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class DevelopmentTask extends Task {
    private Identity<Story> storyId;
    private TaskStatus status;
}

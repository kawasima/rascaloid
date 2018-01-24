package net.unit8.rascaloid.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.seasar.doma.Column;
import org.seasar.doma.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class DevelopmentTask extends Task {
    @Column(name = "story_id")
    private Identity<Story> storyId;
}

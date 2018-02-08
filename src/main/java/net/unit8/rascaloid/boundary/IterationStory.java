package net.unit8.rascaloid.boundary;

import lombok.Value;
import net.unit8.rascaloid.entity.DevelopmentTask;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;

@Value
public class IterationStory {
    private Long storyId;

    @NotBlank
    private String subject;

    @NotBlank
    private String description;

    private BigDecimal point;

    private List<DevelopmentTask> developmentTasks;
}

package net.unit8.rascaloid.boundary;

import lombok.Value;
import net.unit8.rascaloid.entity.DevelopmentTask;

import java.math.BigDecimal;
import java.util.List;

@Value
public class IterationStory {
    private String subject;
    private String description;
    private BigDecimal point;

    private List<DevelopmentTask> developmentTasks;
}

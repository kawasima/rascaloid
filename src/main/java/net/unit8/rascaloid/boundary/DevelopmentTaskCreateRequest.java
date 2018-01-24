package net.unit8.rascaloid.boundary;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper=false)
public class DevelopmentTaskCreateRequest extends BoundaryBase {
    @NotBlank
    private String subject;

    @NotBlank
    private String description;

    @DecimalMin("0.0")
    @DecimalMin("5.0")
    private BigDecimal estimatedHours;

    private TaskStatus status;

    @NotNull
    private Long storyId;

    @Data
    public static class TaskStatus extends BoundaryBase {
        private Long id;
        private String name;
        private Integer position;
    }
}

package net.unit8.rascaloid.boundary;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class DevelopmentTaskCreateRequest extends BoundaryBase {
    @NotBlank
    private String subject;

    @NotBlank
    private String description;

    @DecimalMin("0.0")
    @DecimalMin("5.0")
    private BigDecimal estimatedHour;

    @NotNull
    private Long storyId;
}

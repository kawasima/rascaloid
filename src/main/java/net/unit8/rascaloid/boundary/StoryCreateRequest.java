package net.unit8.rascaloid.boundary;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class StoryCreateRequest extends BoundaryBase {
    @NotBlank
    private String subject;

    @NotBlank
    private String description;

    @DecimalMin("0.5")
    @DecimalMax("13.0")
    private BigDecimal point;
}

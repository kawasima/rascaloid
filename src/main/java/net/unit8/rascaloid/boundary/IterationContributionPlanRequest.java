package net.unit8.rascaloid.boundary;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class IterationContributionPlanRequest extends BoundaryBase {
    private Long userId;
    private BigDecimal hours;
}

package net.unit8.rascaloid.entity;

import lombok.Data;
import org.seasar.doma.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "iteration_contribution_plans")
@Data
public class IterationContributionPlan implements Serializable {
    @Id
    private Long iterationId;

    @Id
    private Long userId;

    private BigDecimal hours;
}

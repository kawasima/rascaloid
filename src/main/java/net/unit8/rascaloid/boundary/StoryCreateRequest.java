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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }
}

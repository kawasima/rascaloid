package net.unit8.rascaloid.boundary;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper=false)
public class IterationCreateRequest extends BoundaryBase {
    @NotBlank
    private String subject;

    @NotBlank
    private String description;

    @NotBlank
    private String startOn;

    @NotBlank
    private String endOn;
}

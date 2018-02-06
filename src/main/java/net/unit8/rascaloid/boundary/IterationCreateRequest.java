package net.unit8.rascaloid.boundary;

import javax.validation.constraints.NotBlank;

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

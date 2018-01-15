package net.unit8.rascaloid.entity;

import lombok.Value;
import org.seasar.doma.Domain;

@Domain(valueType = Long.class)
@Value
public class Identity<T> {
    private final Long value;
}

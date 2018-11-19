package net.unit8.rascaloid.boundary;

import java.io.Serializable;

public class ProjectQuery implements Serializable {
    private String q;
    private Integer limit;
    private Integer offset;

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }
}

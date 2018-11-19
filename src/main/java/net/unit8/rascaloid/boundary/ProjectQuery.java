package net.unit8.rascaloid.boundary;

import java.io.Serializable;

public class ProjectQuery implements Serializable {
    private String q;
    private Integer limit = 10;
    private Integer offset = 0;

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}

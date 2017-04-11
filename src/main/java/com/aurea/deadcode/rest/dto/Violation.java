package com.aurea.deadcode.rest.dto;

import com.aurea.deadcode.util.ToString;

public class Violation {
    private final String entityName;
    private final ViolationType type;
    private final ViolationSource source;
    private final Integer startLine;
    private final Integer endLine;

    public Violation(final String entityName, final ViolationType type,
            final ViolationSource source, final Integer startLine, final Integer endLine) {

        this.entityName = entityName;
        this.type = type;
        this.source = source;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public String getEntityName() {
        return entityName;
    }

    public ViolationType getType() {
        return type;
    }

    public ViolationSource getSource() {
        return source;
    }

    public Integer getStartLine() {
        return startLine;
    }

    public Integer getEndLine() {
        return endLine;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}

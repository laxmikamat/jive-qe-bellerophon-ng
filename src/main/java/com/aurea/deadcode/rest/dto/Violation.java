package com.aurea.deadcode.rest.dto;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Violation {
    private final String entityName;
    private final ViolationType type;
    private final ViolationSource source;
    private final Integer startLine;
    private final Integer endLine;

    public Violation(@Nonnull final String entityName, @Nonnull final ViolationType type,
            @Nonnull final ViolationSource source, @Nullable final Integer startLine, @Nullable final Integer endLine) {

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
}

package com.aurea.deadcode.rest.dto;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SourceFileViolations {
    private final String fileName;
    private final int violationCount;
    private final List<Violation> violations;

    public SourceFileViolations(@Nonnull final String fileName, @Nullable final List<Violation> violations) {
        this.fileName = fileName;
        this.violations = violations;
        this.violationCount = violations != null ? violations.size() : null;
    }

    public String getFileName() {
        return fileName;
    }

    public List<Violation> getViolations() {
        return violations != null ? Collections.unmodifiableList(violations) : null;
    }

    public int getViolationCount() {
        return violationCount;
    }
}

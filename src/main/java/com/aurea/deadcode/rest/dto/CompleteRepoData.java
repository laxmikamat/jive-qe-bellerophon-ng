package com.aurea.deadcode.rest.dto;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CompleteRepoData extends BasicRepoData {
    private final Integer violatedFilesCount;
    private final List<SourceFileViolations> sourceFileViolations;

    protected CompleteRepoData(@Nonnull final BasicRepoData basicRepoData, @Nullable final List<SourceFileViolations> sourceFileViolations) {
        super(basicRepoData.getId(), basicRepoData.getUrl(), basicRepoData.getBranch(), basicRepoData.getStatus(),
                basicRepoData.getAdded(), basicRepoData.getProcessingStarted(), basicRepoData.getProcessingEnded(), 
                basicRepoData.getProcessingDuration(), basicRepoData.getError());
        this.sourceFileViolations = sourceFileViolations;
        this.violatedFilesCount = sourceFileViolations != null ? sourceFileViolations.size() : null;
    }

    public List<SourceFileViolations> getSourceFileViolations() {
        return sourceFileViolations != null ? Collections.unmodifiableList(sourceFileViolations) : null;
    }

    public Integer getViolatedFilesCount() {
        return violatedFilesCount;
    }
}

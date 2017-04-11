package com.aurea.deadcode.rest.dto;

import java.util.Collections;
import java.util.List;

import com.aurea.deadcode.util.ToString;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class FullRepoData extends BasicRepoData {
    private final Integer violatedFilesCount;
    private final List<SourceFileViolations> sourceFileViolations;

    FullRepoData(final BasicRepoData basicRepoData, final List<SourceFileViolations> sourceFileViolations) {
        super(basicRepoData.getUuid(), basicRepoData.getUrl(), basicRepoData.getBranch(), basicRepoData.getStatus(),
                basicRepoData.getAdded(), basicRepoData.getAnalysisStarted(), basicRepoData.getAnalysisEnded(), 
                basicRepoData.getAnalysisDuration(), basicRepoData.getError());

        this.sourceFileViolations = sourceFileViolations;
        this.violatedFilesCount = sourceFileViolations != null ? sourceFileViolations.size() : null;
    }

    public List<SourceFileViolations> getSourceFileViolations() {
        return sourceFileViolations != null ? Collections.unmodifiableList(sourceFileViolations) : null;
    }

    public Integer getViolatedFilesCount() {
        return violatedFilesCount;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}

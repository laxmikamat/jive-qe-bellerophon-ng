package com.aurea.deadcode.rest.dto;

import java.util.Date;

import com.aurea.deadcode.model.CompletionStatus;
import com.aurea.deadcode.util.ToString;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BasicRepoData extends NewRepoRequest {
    private final String uuid;
    private final CompletionStatus status;
    private final Date added;
    private final Date analysisStarted;
    private final Date analysisEnded;
    private final Long analysisDuration;
    private final String error;

    BasicRepoData(final String uuid, final String url, final String branch, 
            final CompletionStatus status, final Date added, final Date analysisStarted, 
            final Date analysisEnded, final Long analysisDuration, final String error) {

        super(url, branch);
        this.uuid = uuid;
        this.status = status;
        this.added = added;
        this.analysisStarted = analysisStarted;
        this.analysisEnded = analysisEnded;
        this.analysisDuration = analysisDuration;
        this.error = error;
    }

    public String getUuid() {
        return uuid;
    }

    public CompletionStatus getStatus() {
        return status;
    }

    public Date getAdded() {
        return added;
    }

    public Date getAnalysisStarted() {
        return analysisStarted;
    }

    public Date getAnalysisEnded() {
        return analysisEnded;
    }

    public Long getAnalysisDuration() {
        return analysisDuration;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}

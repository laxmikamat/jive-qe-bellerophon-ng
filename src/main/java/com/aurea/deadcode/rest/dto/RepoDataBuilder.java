package com.aurea.deadcode.rest.dto;

import java.util.Date;
import java.util.List;

import com.aurea.deadcode.model.CompletionStatus;

public class RepoDataBuilder {
    private String uuid;
    private String url;
    private String branch;
    private CompletionStatus status;
    private Date added;
    private Date analysisStarted;
    private Date analysisEnded;
    private Long analysisDuration;
    private List<SourceFileViolations> sourceFileViolations;
    private String error;

    public BasicRepoData buildBasicRepoData() {
        return new BasicRepoData(uuid, url, branch, 
                status, added, analysisStarted, 
                analysisEnded, analysisDuration, error);
    }

    public FullRepoData buildFullRepoData() {
        return new FullRepoData(new BasicRepoData(uuid, url, branch, 
                status, added, analysisStarted, 
                analysisEnded, analysisDuration, error),
                sourceFileViolations);
    }

    public RepoDataBuilder uuid(final String uuid) {
        this.uuid = uuid;
        return this;
    }

    public RepoDataBuilder url(final String url) {
        this.url = url;
        return this;
    }

    public RepoDataBuilder branch(final String branch) {
        this.branch = branch;
        return this;
    }

    public RepoDataBuilder status(final CompletionStatus status) {
        this.status = status;
        return this;
    }

    public RepoDataBuilder added(final Date added) {
        this.added = added;
        return this;
    }

    public RepoDataBuilder analysisStarted(final Date analysisStarted) {
        this.analysisStarted = analysisStarted;
        return this;
    }

    public RepoDataBuilder analysisEnded(final Date analysisEnded) {
        this.analysisEnded = analysisEnded;
        return this;
    }

    public RepoDataBuilder processingDuration(final Long analysisDuration) {
        this.analysisDuration = analysisDuration;
        return this;
    }

    public RepoDataBuilder error(final String error) {
        this.error = error;
        return this;
    }

    public RepoDataBuilder sourceFileViolations(final List<SourceFileViolations> sourceFileViolations) {
        this.sourceFileViolations = sourceFileViolations;
        return this;
    }
}
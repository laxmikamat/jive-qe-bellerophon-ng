package com.aurea.deadcode.rest.dto;

import java.util.Date;
import java.util.List;

public class RepoDataBuilder {
    private String id;
    private String url;
    private String branch;
    private CompletionStatus status;
    private Date added;
    private Date processingStarted;
    private Date processingEnded;
    private Long processingDuration;
    private List<SourceFileViolations> sourceFileViolations;
    private String error;

    public BasicRepoData basicRepoData() {
        return new BasicRepoData(id, url, branch, 
                status, added, processingStarted, 
                processingEnded, processingDuration, error);
    }

    public BasicRepoData completeRepoData() {
        return new CompleteRepoData(new BasicRepoData(id, url, branch, 
                status, added, processingStarted, 
                processingEnded, processingDuration, error),
                sourceFileViolations);
    }

    public RepoDataBuilder id(final String id) {
        this.id = id;
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

    public RepoDataBuilder processingStarted(final Date processingStarted) {
        this.processingStarted = processingStarted;
        return this;
    }

    public RepoDataBuilder processingEnded(final Date processingEnded) {
        this.processingEnded = processingEnded;
        return this;
    }

    public RepoDataBuilder processingDuration(final Long processingDuration) {
        this.processingDuration = processingDuration;
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
package com.aurea.deadcode.rest.dto;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BasicRepoData {
    private final String uuid;
    private final String url;
    private final String branch;
    private final CompletionStatus status;
    private final Date added;
    private final Date processingStarted;
    private final Date processingEnded;
    private final Long processingDuration;
    private final String error;

    protected BasicRepoData(@Nonnull final String uuid, @Nonnull final String url, @Nullable final String branch, 
            @Nonnull final CompletionStatus status, @Nonnull final Date added, @Nullable final Date processingStarted, 
            @Nullable final Date processingEnded, @Nullable final Long processingDuration, @Nullable final String error) {

        this.uuid = uuid;
        this.url = url;
        this.branch = branch;
        this.status = status;
        this.added = added;
        this.processingStarted = processingStarted;
        this.processingEnded = processingEnded;
        this.processingDuration = processingDuration;
        this.error = error;
    }

    public String getId() {
        return uuid;
    }

    public String getUrl() {
        return url;
    }

    public String getBranch() {
        return branch;
    }

    public CompletionStatus getStatus() {
        return status;
    }

    public Date getAdded() {
        return added;
    }

    public Date getProcessingStarted() {
        return processingStarted;
    }

    public Date getProcessingEnded() {
        return processingEnded;
    }

    public Long getProcessingDuration() {
        return processingDuration;
    }

    public String getError() {
        return error;
    }
}

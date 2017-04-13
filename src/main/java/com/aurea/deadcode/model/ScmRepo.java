package com.aurea.deadcode.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.aurea.deadcode.util.ToString;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "url", "branch" }) })
public class ScmRepo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, updatable = false)
    private String uuid;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false)
    private CompletionStatus completionStatus = CompletionStatus.ADDED;

    @Column(nullable = false, updatable = false)
    private Date added;

    @Column
    private String repoDir;

    @Column
    private Date analysisStarted;

    @Column
    private Date analysisEnded;

    @Column
    private String error;

    @Column(columnDefinition = "CLOB")
    @Lob
    private String violations;

    @PrePersist
    void prePersist() {
        added = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        if (uuid != null) {
            this.uuid = uuid.replace("-", "");
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(final String branch) {
        this.branch = branch;
    }

    public CompletionStatus getCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(final CompletionStatus completionStatus) {
        this.completionStatus = completionStatus;
    }

    public String getRepoDir() {
        return repoDir;
    }

    public void setRepoDir(final String repoDir) {
        this.repoDir = repoDir;
    }

    public Date getAdded() {
        return added;
    }

    public Date getAnalysisStarted() {
        return analysisStarted;
    }

    public void setAnalysisStarted(final Date analysisStarted) {
        this.analysisStarted = analysisStarted;
    }

    public Date getAnalysisEnded() {
        return analysisEnded;
    }

    public void setAnalysisEnded(final Date analysisEnded) {
        this.analysisEnded = analysisEnded;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public String getViolations() {
        return violations;
    }

    public void setViolations(final String violations) {
        this.violations = violations;
    }
    
    @Override
    public String toString() {
        return ToString.toString(this);
    }

}

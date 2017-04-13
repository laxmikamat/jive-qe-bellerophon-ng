package com.aurea.deadcode.rest.dto;

import java.util.Objects;

import com.aurea.deadcode.util.ToString;

public class NewRepoRequest {
    private String url;
    private String branch = "master";

    public NewRepoRequest() {
    }

    public NewRepoRequest(final String url) {
        this.url = url;
    }
    
    NewRepoRequest(final String url, final String branch) {
        this.url = url;
        this.branch = branch;
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
    
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof NewRepoRequest)) {
            return false;
        }
        
        final NewRepoRequest other = (NewRepoRequest) obj;
        return Objects.equals(url, other.url) 
                && Objects.equals(branch, other.branch);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(url, branch);
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}

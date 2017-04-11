package com.aurea.deadcode.rest.dto;

import com.aurea.deadcode.util.ToString;

public class NewRepoRequest {
    private String url;
    private String branch = "master";

    public NewRepoRequest() {
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
    public String toString() {
        return ToString.toString(this);
    }
}

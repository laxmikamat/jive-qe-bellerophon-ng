package com.aurea.deadcode.rest.dto;

import java.util.List;

public class RepoListData {
    private final List<BasicRepoData> repos;

    public RepoListData(final List<BasicRepoData> repos) {
        this.repos = repos;
    }

    public List<BasicRepoData> getRepos() {
        return repos;
    }
}

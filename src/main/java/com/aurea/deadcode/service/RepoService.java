package com.aurea.deadcode.service;

import com.aurea.deadcode.rest.dto.BasicRepoData;
import com.aurea.deadcode.rest.dto.FullRepoData;
import com.aurea.deadcode.rest.dto.RepoListData;

public interface RepoService {
    RepoListData getAllRepos();

    BasicRepoData create(String url, String branch);

    FullRepoData read(String uuid);

    BasicRepoData update(BasicRepoData repo);
}

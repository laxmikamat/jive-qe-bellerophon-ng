package com.aurea.deadcode.service;

import java.util.List;

import com.aurea.deadcode.model.ScmRepo;

public interface RepoService {
    List<ScmRepo> getAllRepos();

    ScmRepo create(ScmRepo repo);

    ScmRepo read(String uuid);
}

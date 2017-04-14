package com.aurea.deadcode.jpa;

import org.springframework.data.repository.CrudRepository;

import com.aurea.deadcode.model.ScmRepo;

public interface ScmRepoRepository extends CrudRepository<ScmRepo, Long> {
    ScmRepo findByUuid(String uuid);

    ScmRepo findByUrlAndBranch(String url, String branch);
}

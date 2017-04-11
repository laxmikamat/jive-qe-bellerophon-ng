package com.aurea.deadcode.jpa.repo;

import org.springframework.data.repository.CrudRepository;

import com.aurea.deadcode.model.ScmRepo;

public interface ScmRepoRepository extends CrudRepository<ScmRepo, String> {

    ScmRepo findByUuid(String uuid);

    ScmRepo findByUrlAndBranch(String url, String branch);
}

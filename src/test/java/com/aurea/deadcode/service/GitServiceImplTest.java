package com.aurea.deadcode.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.service.exception.ServiceException;

public class GitServiceImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(GitServiceImplTest.class);
    private final GitServiceImpl gitService = new GitServiceImpl();
    private String repoDir;
    private ScmRepo repo;
    
    @After
    public void cleanup() {
        if (repoDir != null) {
            try {
                LOG.info("Trying to delete '" + repoDir + "'");
                FileUtils.deleteDirectory(new File(repoDir));
                LOG.info("Repo dir '" + repoDir + "' deleted");
            } catch (final IOException e) {
                LOG.info("Could not delete repo directory '" + repoDir + "'", e);
            }
        }
    }
    
    @Before
    public void before() {
        gitService.cloneRootDir = "DOES_NOT_EXIST";
        repo = new ScmRepo();
        repo.setUrl("git@github.com:githubtraining/hellogitworld.git");
        repo.setUuid("test-uuid");
    }
    
    @Test
    public void shouldCloneNewRepoInTempDir() {
        gitService.cloneNewRepo(repo);
        repoDir = repo.getRepoDir();
    }

    @Test
    public void shouldCloneNewRepoInDefinedDir() {
        gitService.cloneRootDir = System.getProperty("user.home");
        gitService.cloneNewRepo(repo);
        repoDir = repo.getRepoDir();
    }
    
    @Test(expected = ServiceException.class)
    public void shouldBreakOnInvalidRepo() {
        repo.setUrl("git@github.com:doesnotexist/doesnotexist-hopefully.git");
        gitService.cloneNewRepo(repo);
    }
}

package com.aurea.deadcode.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.service.exception.ServiceException;

@Service
public class GitServiceImpl implements GitService {
    private static final Logger LOG = LoggerFactory.getLogger(GitServiceImpl.class);

    @Value("${git.clone.root.dir}")
    protected String cloneRootDir;

    @Override
    public void cloneNewRepo(final ScmRepo repo) throws ServiceException {
        File repoDir = new File(cloneRootDir + File.separator + repo.getUuid());
        if (repoDir.exists() || (!repoDir.exists() && !repoDir.mkdir())) {
            LOG.warn("'" + repoDir.getAbsolutePath() + "' alread exists or cannot be created.");
            try {
                repoDir = Files.createTempDirectory(repo.getUuid()).toFile();
            } catch (final IOException e) {
                throw new ServiceException("Unable to create temp directory for cloning Git repo", e);
            }
        }

        try {
            LOG.info("Cloning Git repo '" + repo.getUrl() + "' to '" + repoDir.getAbsolutePath() + "'");
            Git.cloneRepository()
                .setDirectory(repoDir)
                .setURI(repo.getUrl())
                .setBranch(repo.getBranch())
                .call();

            LOG.info("Git repo '" + repo.getUrl() + "' cloned in '" + repoDir.getAbsolutePath() + "'");
        } catch (final GitAPIException e) {
            repo.setError(e.toString());
            throw new ServiceException("Unable to clone Git repo '" + repo.getUrl() + "'", e);
        }

        repo.setRepoDir(repoDir.getAbsolutePath());
    }
}

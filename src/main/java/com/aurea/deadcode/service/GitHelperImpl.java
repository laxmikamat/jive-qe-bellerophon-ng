package com.aurea.deadcode.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.service.exception.ServiceException;

public class GitHelperImpl implements GitHelper {
    private static final Logger LOG = LoggerFactory.getLogger(GitHelperImpl.class);

    @Value("${git.clone.root.dir}")
    protected String cloneRootDir;

    @Override
    public void cloneNewRepo(final ScmRepo repo) throws ServiceException {
        File repoDir = new File(cloneRootDir + File.pathSeparator + repo.getUuid());
        if (!repoDir.isDirectory() || !repoDir.exists()) {
            LOG.warn("'" + cloneRootDir + "' does not exist or is not a directory.");
            try {
                repoDir = Files.createTempDirectory(repo.getUuid()).toFile();
            } catch (final IOException e) {
                throw new ServiceException("Unable to create temp directory for cloning Git repo", e);
            }
        }

        try {
            LOG.info("Cloning git repo '" + repo.getUrl() + "' to '" + repoDir.getAbsolutePath() + "'");
            Git.cloneRepository()
            .setDirectory(repoDir)
            .setURI(repo.getUrl())
            .setBranch(repo.getBranch())
            .call();
        } catch (final GitAPIException e) {
            repo.setError(e.toString());
            throw new ServiceException("Unable to clone Git repo '" + repo.getUrl() + "'", e);
        }

        repo.setRepoDir(repoDir.getAbsolutePath());
    }
}

package com.aurea.deadcode.service;

import java.util.List;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.aurea.deadcode.jpa.ScmRepoRepository;
import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.notif.EventType;
import com.aurea.deadcode.notif.Notification;
import com.aurea.deadcode.service.exception.ConflictException;
import com.aurea.deadcode.service.exception.ServiceException;
import com.google.common.collect.Lists;

@Service
public class RepoServiceImpl implements RepoService {
    private static final Logger LOG = LoggerFactory.getLogger(RepoServiceImpl.class);

    @Autowired
    @Named("newRepoAddedNotification")
    protected Notification<EventType, String> notifManager;

    @Autowired
    protected ScmRepoRepository scmRepoRepository;

    @Override
    public List<ScmRepo> getAllRepos() {
        try {
            return Lists.newArrayList(scmRepoRepository.findAll());
        } catch (final RuntimeException e) {
            LOG.error("Unable to retrieve repositories from DB", e);
            throw new ServiceException("Unable to retrieve repositories from DB", e);
        }
    }

    @Override
    public ScmRepo create(final ScmRepo repo) {
        try {
            final ScmRepo dbRepo = scmRepoRepository.save(repo);
            notifManager.notify(EventType.NEW_REPO_ADDED, dbRepo.getUuid());
            return dbRepo;
        } catch (final DataIntegrityViolationException e) {
            LOG.info("Repo " + repo + " (probably) already exists: " + e.getMessage());
            throw new ConflictException(repo, e);
        }
    }

    @Override
    public ScmRepo read(final String uuid) {
        return scmRepoRepository.findByUuid(uuid);
    }
}

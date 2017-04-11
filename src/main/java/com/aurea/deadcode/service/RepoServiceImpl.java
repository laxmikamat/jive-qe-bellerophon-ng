package com.aurea.deadcode.service;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.eclipse.jgit.transport.URIish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurea.deadcode.jpa.repo.ScmRepoRepository;
import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.notif.EventType;
import com.aurea.deadcode.notif.Notification;
import com.aurea.deadcode.rest.dto.NewRepoRequest;
import com.aurea.deadcode.rest.dto.BasicRepoData;
import com.aurea.deadcode.rest.dto.FullRepoData;
import com.aurea.deadcode.rest.dto.RepoDataBuilder;
import com.aurea.deadcode.rest.dto.RepoListData;
import com.aurea.deadcode.service.converter.DataConverter;
import com.aurea.deadcode.service.exception.BadRequestException;
import com.aurea.deadcode.service.exception.NotFoundException;
import com.aurea.deadcode.service.exception.ServiceException;
import com.google.common.collect.Lists;

@Service
public class RepoServiceImpl implements RepoService {
    private static final Logger LOG = LoggerFactory.getLogger(RepoServiceImpl.class);

    @Autowired
    @Named("newRepoAddedNotification")
    protected Notification<EventType, NewRepoRequest> notifManager;

    @Autowired
    protected ScmRepoRepository scmRepoRepository;

    @Autowired
    @Named("basicDataConverter")
    protected DataConverter<ScmRepo, BasicRepoData> basicConverter;

    @Autowired
    @Named("fullDataConverter")
    protected DataConverter<ScmRepo, FullRepoData> completeConverter;

    @Override
    public RepoListData getAllRepos() {
        try {
            final List<ScmRepo> repos = Lists.newArrayList(scmRepoRepository.findAll());
            return new RepoListData(repos
                    .stream()
                    .map(basicConverter::model2Dto)
                    .collect(Collectors.toList()));
        } catch (final RuntimeException e) {
            LOG.error("Unable to retrieve repositories from DB", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public BasicRepoData create(final String url, final String branch) {
        try {
            if (!new URIish(url).isRemote()) {
                throw new URISyntaxException(url, "Given URL is not a remote Git repo");
            }
        } catch (final URISyntaxException | RuntimeException e) {
            LOG.info("Repo creation error: " + e.getMessage());
            throw new BadRequestException(e.getMessage(), e);
        }

        final BasicRepoData result = basicConverter.model2Dto(scmRepoRepository.save(basicConverter.dto2Model(
                new RepoDataBuilder()
                .url(url)
                .branch(branch)
                .buildBasicRepoData())));

        notifManager.notify(EventType.NEW_REPO_ADDED, result);
        return result;
    }

    @Override
    public FullRepoData read(final String uuid) {
        final Optional<ScmRepo> repo = Optional.ofNullable(scmRepoRepository.findByUuid(uuid));
        return completeConverter.model2Dto(repo.orElseThrow(() -> new NotFoundException(uuid)));
    }

    @Override
    public BasicRepoData update(final BasicRepoData repo) {
        return null;
    }
}

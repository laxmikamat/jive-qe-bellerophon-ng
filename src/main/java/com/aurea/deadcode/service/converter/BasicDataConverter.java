package com.aurea.deadcode.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.rest.dto.BasicRepoData;
import com.aurea.deadcode.rest.dto.RepoDataBuilder;
import com.aurea.deadcode.util.UuidGenerator;

@Component
public class BasicDataConverter implements DataConverter<ScmRepo, BasicRepoData> {

    @Autowired
    protected UuidGenerator uuidGen;

    @Override
    public BasicRepoData model2Dto(final ScmRepo model) {
        if (model == null) {
            return null;
        }

        return new RepoDataBuilder()
                .uuid(model.getUuid())
                .url(model.getUrl())
                .branch(model.getBranch())
                .added(model.getAdded())
                .analysisStarted(model.getAnalysisStarted())
                .analysisEnded(model.getAnalysisEnded())
                .buildBasicRepoData();
    }

    @Override
    public ScmRepo dto2Model(final BasicRepoData dto) {
        final ScmRepo repo = new ScmRepo();
        repo.setUuid(uuidGen.generateUUID().toString());
        repo.setUrl(dto.getUrl());
        repo.setBranch(dto.getBranch());
        return repo;
    }

}

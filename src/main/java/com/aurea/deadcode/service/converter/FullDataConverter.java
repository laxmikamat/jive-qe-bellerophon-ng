package com.aurea.deadcode.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.rest.dto.FullRepoData;
import com.aurea.deadcode.rest.dto.RepoDataBuilder;
import com.aurea.deadcode.util.UuidGenerator;

@Component
public class FullDataConverter implements DataConverter<ScmRepo, FullRepoData> {
    @Autowired
    protected UuidGenerator uuidGen;

    @Override
    public FullRepoData model2Dto(final ScmRepo model) {
        return new RepoDataBuilder()
                .uuid(model.getUuid())
                .url(model.getUrl())
                .branch(model.getBranch())
                .added(model.getAdded())
                .analysisStarted(model.getAnalysisStarted())
                .analysisEnded(model.getAnalysisEnded())
                .status(model.getCompletionStatus())
                .buildFullRepoData();
    }

    @Override
    public ScmRepo dto2Model(final FullRepoData dto) {
        throw new UnsupportedOperationException("Unused");
    }
}

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
        return new RepoDataBuilder()
                .uuid(model.getUuid())
                .url(model.getUrl())
                .branch(model.getBranch())
                .added(model.getAdded())
                .analysisStarted(model.getAnalysisStarted())
                .analysisEnded(model.getAnalysisEnded())
                .status(model.getCompletionStatus())
                .buildBasicRepoData();
    }

    @Override
    public ScmRepo dto2Model(final BasicRepoData dto) {
        throw new UnsupportedOperationException("Unused");
    }
}

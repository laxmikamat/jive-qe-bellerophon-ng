package com.aurea.deadcode.service.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.rest.dto.NewRepoRequest;
import com.aurea.deadcode.util.UuidGenerator;

@Component
public class NewRepoConverter implements DataConverter<ScmRepo, NewRepoRequest> {
    @Autowired
    protected UuidGenerator uuidGen;

    @Override
    public NewRepoRequest model2Dto(final ScmRepo model) {
        throw new UnsupportedOperationException("Unused");
    }

    @Override
    public ScmRepo dto2Model(final NewRepoRequest dto) {
        final ScmRepo repo = new ScmRepo();
        repo.setUuid(uuidGen.generateUUID().toString());
        repo.setUrl(dto.getUrl());
        repo.setBranch(dto.getBranch());
        return repo;
    }
}

package com.aurea.deadcode.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.service.exception.ServiceException;

@Service
public class UnderstandServiceImpl implements UnderstandService {

    @Value("${understand.exec}")
    protected String understand;

    @Value("${understand.db.root.dir}")
    protected String udbRootDir;

    @Override
    public void buildUnderstandDb(final ScmRepo repo) throws ServiceException {
    }
}

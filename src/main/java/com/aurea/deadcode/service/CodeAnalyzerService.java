package com.aurea.deadcode.service;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.service.exception.ServiceException;

public interface CodeAnalyzerService {
    void analyzeRepo(ScmRepo repo) throws ServiceException;
}

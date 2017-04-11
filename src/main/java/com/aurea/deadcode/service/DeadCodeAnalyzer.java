package com.aurea.deadcode.service;

import org.springframework.stereotype.Service;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.service.exception.ServiceException;

@Service
public class DeadCodeAnalyzer implements CodeAnalyzer {
    @Override
    public void analyzeRepo(final ScmRepo repo) throws ServiceException {
    }
}

package com.aurea.deadcode.service;

import com.aurea.deadcode.service.exception.ServiceException;

public interface RepoOrchestratorService {
    void newRepoAdded(String uuid) throws ServiceException;
}

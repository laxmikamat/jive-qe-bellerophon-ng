package com.aurea.deadcode.service;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.service.exception.ServiceException;

public interface GitService {
    void cloneNewRepo(ScmRepo repo) throws ServiceException;
}

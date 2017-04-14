package com.aurea.deadcode.service;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.service.exception.ServiceException;

public interface UnderstandService {
    void buildUnderstandDb(ScmRepo repo) throws ServiceException;
}

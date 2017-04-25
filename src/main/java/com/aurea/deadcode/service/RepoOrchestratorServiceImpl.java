package com.aurea.deadcode.service;

import java.util.Date;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurea.deadcode.jpa.ScmRepoRepository;
import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.service.exception.ServiceException;

@Service
public class RepoOrchestratorServiceImpl implements RepoOrchestratorService {
    private static final Logger LOG = LoggerFactory.getLogger(RepoOrchestratorServiceImpl.class);
    
    @Autowired
    protected CodeAnalyzerService codeAnalyzer;

    @Autowired
    protected GitService gitHelper;

    @Autowired
    protected UnderstandService understandHelper;

    @Autowired
    protected ScmRepoRepository scmRepository;
    
    @Override
    public void newRepoAdded(final String uuid) {
        final ScmRepo dbRepo = scmRepository.findByUuid(uuid);
        final StopWatch watch = StopWatch.createStarted();
        try {
            gitHelper.cloneNewRepo(dbRepo);
            understandHelper.buildUnderstandDb(dbRepo);
            codeAnalyzer.analyzeRepo(dbRepo);
            watch.stop();
            dbRepo.setAnalysisEnded(new Date());
            dbRepo.setAnalysisStarted(new Date(watch.getStartTime()));
            
            LOG.info("Updating repo data in DB: " + dbRepo);
            scmRepository.save(dbRepo);
            
        } catch (final ServiceException e) {
            // log error
        }
    }

}

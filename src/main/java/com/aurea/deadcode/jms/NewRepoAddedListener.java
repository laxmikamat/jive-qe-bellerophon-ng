package com.aurea.deadcode.jms;

import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.aurea.deadcode.jpa.repo.ScmRepoRepository;
import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.rest.dto.NewRepoRequest;
import com.aurea.deadcode.rest.dto.BasicRepoData;
import com.aurea.deadcode.service.CodeAnalyzer;
import com.aurea.deadcode.service.GitHelper;
import com.aurea.deadcode.service.UnderstandHelper;
import com.aurea.deadcode.service.exception.ServiceException;
import com.google.gson.Gson;

@Service
public class NewRepoAddedListener {
    private static final Logger LOG = LoggerFactory.getLogger(NewRepoAddedListener.class);

    @Autowired
    protected CodeAnalyzer codeAnalyzer;

    @Autowired
    protected GitHelper gitHelper;

    @Autowired
    protected UnderstandHelper understandHelper;

    @Autowired
    protected ScmRepoRepository scmRepository;

    @JmsListener(destination = "queue.repos", selector = "EventType = 'NEW_REPO_ADDED'")
    public void onMessage(final TextMessage msg, final Session session) throws JMSException {
        LOG.info("New repo added: " + msg.getText());
        final NewRepoRequest request = new Gson().fromJson(msg.getText(), BasicRepoData.class);
        final ScmRepo repo = scmRepository.findByUrlAndBranch(request.getUrl(), request.getBranch());
        final StopWatch watch = StopWatch.createStarted();
        try {
            gitHelper.cloneNewRepo(repo);
            understandHelper.buildUnderstandDb(repo);
            codeAnalyzer.analyzeRepo(repo);
            watch.stop();
            repo.setAnalysisStarted(new Date(watch.getStartTime()));
            repo.setAnalysisEnded(new Date(watch.getTime()));
            scmRepository.save(repo);
        } catch (final ServiceException e) {
            // log error
        }
    }
}

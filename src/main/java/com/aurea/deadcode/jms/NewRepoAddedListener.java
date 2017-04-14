package com.aurea.deadcode.jms;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.aurea.deadcode.service.RepoOrchestratorService;
import com.google.gson.Gson;

@Service
@Profile("jms")
public class NewRepoAddedListener {
    private static final Logger LOG = LoggerFactory.getLogger(NewRepoAddedListener.class);

    @Autowired
    protected RepoOrchestratorService repoOrchestratorService;
    
    @JmsListener(destination = "queue.repos", selector = "EventType = 'NEW_REPO_ADDED'")
    public void onMessage(final TextMessage msg, final Session session) throws JMSException {
        LOG.info("New repo added with UUID: " + msg.getText());
        repoOrchestratorService.newRepoAdded(new Gson().fromJson(msg.getText(), String.class));
    }
}

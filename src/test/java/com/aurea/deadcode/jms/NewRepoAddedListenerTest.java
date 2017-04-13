package com.aurea.deadcode.jms;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.aurea.deadcode.BaseMvcTest;
import com.aurea.deadcode.rest.dto.NewRepoRequest;
import com.google.gson.Gson;

@Service
public class NewRepoAddedListenerTest extends BaseMvcTest {
    @Test
    public void shouldCallJmsListener() throws Exception {
        final NewRepoRequest repoRq = new NewRepoRequest();
        repoRq.setUrl("git@git.com:/repo");
        mockMvc.perform(post("/rest/repos")
                .content(new Gson().toJson(repoRq))
                .contentType(APPLICATION_JSON_UTF8));
    }
    
    @JmsListener(destination = "queue.repos", selector = "EventType = 'NEW_REPO_ADDED'")
    public void onMessage(final TextMessage msg, final Session session) throws JMSException {
        System.err.println("!!! message received !!!");
    }
}

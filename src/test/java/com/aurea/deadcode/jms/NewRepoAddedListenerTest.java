package com.aurea.deadcode.jms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import com.aurea.deadcode.BaseMvcTest;
import com.aurea.deadcode.rest.dto.NewRepoRequest;
import com.google.gson.Gson;

@Service
@ActiveProfiles("testJms")
public class NewRepoAddedListenerTest extends BaseMvcTest {
    private static final BlockingQueue<NewRepoRequest> queue = new ArrayBlockingQueue<>(1);
    
    @Test
    public void shouldCallJmsListener() throws Exception {
        for (int i = 0; i < 1000; i++) {
            final NewRepoRequest repoRq = new NewRepoRequest("git@git.com:/repo" + i);
            mockMvc.perform(post("/rest/repos")
                    .content(new Gson().toJson(repoRq))
                    .contentType(APPLICATION_JSON_UTF8));
            
            final NewRepoRequest jmsRequest = queue.poll(1, TimeUnit.SECONDS);
            assertThat(jmsRequest, notNullValue());
            assertThat(jmsRequest, equalTo(repoRq));
        }
    }
    
    @Profile("testJms")
    @JmsListener(destination = "queue.repos", selector = "EventType = 'NEW_REPO_ADDED'")
    public void onMessage(final TextMessage msg, final Session session) throws JMSException {
        queue.offer(new Gson().fromJson(msg.getText(), NewRepoRequest.class));
    }
}

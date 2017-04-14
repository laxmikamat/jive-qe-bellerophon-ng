package com.aurea.deadcode.jms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.ResultActions;

import com.aurea.deadcode.BaseMvcTest;
import com.aurea.deadcode.rest.dto.BasicRepoData;
import com.aurea.deadcode.rest.dto.NewRepoRequest;
import com.aurea.deadcode.service.RepoOrchestratorService;
import com.google.gson.Gson;

@Service
@ActiveProfiles("testJms")
public class NewRepoAddedListenerTest extends BaseMvcTest {

    private static final BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();
    
    @Mock
    private RepoOrchestratorService orchestratorService;
    
    @Mock
    private TextMessage jmsMsg;
    
    @Mock
    private Session jmsSess;
    
    @InjectMocks
    private NewRepoAddedListener listener;
    
    @Test
    public void shouldCallRepoOrchestatorService() throws Exception {
        final String uuid = UUID.randomUUID().toString();
        when(jmsMsg.getText()).thenReturn(new Gson().toJson(uuid));
        listener.onMessage(jmsMsg, jmsSess);
        verify(orchestratorService).newRepoAdded(uuid);
    }
    
    @Test
    public void shouldCallJmsListener() throws Exception {
        final Gson gson = new Gson();
        for (int i = 0; i < 1000; i++) {
            final ResultActions result = mockMvc.perform(post("/rest/repos")
                    .content(gson.toJson(new NewRepoRequest("git@git.com:/repo" + i)))
                    .contentType(APPLICATION_JSON_UTF8));
            
            final BasicRepoData repoRs = gson.fromJson(result
                    .andReturn()
                    .getResponse()
                    .getContentAsString(), BasicRepoData.class);
            
            final String receivedUuid = queue.poll(1, TimeUnit.SECONDS);
            assertThat(receivedUuid, notNullValue());
            assertThat(receivedUuid, equalTo(repoRs.getUuid()));
        }
    }
    
    @Profile("testJms")
    @JmsListener(destination = "queue.repos", selector = "EventType = 'NEW_REPO_ADDED'")
    public void onMessage(final TextMessage msg, final Session session) throws JMSException {
        queue.offer(new Gson().fromJson(msg.getText(), String.class));
    }

    /**
     * Auxiliary DTO class.
     */
    public static class TestRepoData {
        private String uuid;
        
        public String getUuid() {
            return uuid;
        }
        
        public void setUuid(final String uuid) {
            this.uuid = uuid;
        }
    }
}

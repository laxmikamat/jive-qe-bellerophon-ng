package com.aurea.deadcode.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.apache.http.HttpStatus;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import com.aurea.deadcode.BaseMvcTest;
import com.aurea.deadcode.jpa.ScmRepoRepository;
import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.rest.dto.NewRepoRequest;
import com.aurea.deadcode.util.UuidGenerator;
import com.google.gson.Gson;

public class ReposControllerTest extends BaseMvcTest {
    private static final int REPEAT_COUNT = 100;
    private static final String URL_BASE = "git@github.com:/";
    
    @Autowired
    private ScmRepoRepository scmRepository;
    
    @Autowired
    private UuidGenerator uuidGen;
    
    @After
    public void cleanUp() {
        scmRepository.deleteAll();
    }

    @Test
    public void shouldGetAllRepos() throws Exception {
        final List<ScmRepo> repos = generateAndStoreRandomRepos(REPEAT_COUNT);
        final ResultActions test = mockMvc.perform(get("/rest/repos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
        
        for (int i = 0; i < repos.size(); i++) {
            test.andExpect(jsonPath("$.repos.[" + i + "].uuid", is(repos.get(i).getUuid())))
                .andExpect(jsonPath("$.repos.[" + i + "].url", is(repos.get(i).getUrl())));
        }
    }
    
    @Test
    public void shouldGetSingleRepo() throws Exception {
        final List<ScmRepo> repos = generateAndStoreRandomRepos(1000);
        for (final ScmRepo repo : repos) {
            assertValidRepo(mockMvc.perform(get("/rest/repos/" + repo.getUuid())), repo);
        }
    }

    @Test
    public void shouldFailOnRepoNotFound() throws Exception {
        mockMvc.perform(get("/rest/repos/1234"))
                .andExpect(status().is(HttpStatus.SC_NOT_FOUND))
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }
    
    @Test
    public void shouldAddUniqueRepos() throws Exception {
        final List<ScmRepo> repos = generateRandomRepos(1000);
        for (final ScmRepo repo : repos) {
            final NewRepoRequest repoRq = new NewRepoRequest(repo.getUrl());
            final ResultActions test = mockMvc.perform(post("/rest/repos")
                    .content(new Gson().toJson(repoRq))
                    .contentType(APPLICATION_JSON_UTF8));
            
            final ScmRepo dbRepo = scmRepository.findByUrlAndBranch(repoRq.getUrl(), repoRq.getBranch());
            assertValidRepo(test, dbRepo);
        }
    }
    
    @Test
    public void shouldFailOnAddingDuplicateRepo() throws Exception {
        final NewRepoRequest repoRq = new NewRepoRequest(URL_BASE + "repo");
        final Gson gson = new Gson();
        mockMvc.perform(post("/rest/repos")
                .content(gson.toJson(repoRq))
                .contentType(APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk());
        
        mockMvc.perform(post("/rest/repos")
                .content(gson.toJson(repoRq))
                .contentType(APPLICATION_JSON_UTF8))
                    .andExpect(status().is(HttpStatus.SC_CONFLICT));
    }

    @Test
    public void shouldFailOnAddingInvalidRepo() throws Exception {
        final NewRepoRequest repoRq = new NewRepoRequest("invalid uri");
        mockMvc.perform(post("/rest/repos")
                .content(new Gson().toJson(repoRq))
                .contentType(APPLICATION_JSON_UTF8))
                    .andExpect(status().is(HttpStatus.SC_BAD_REQUEST));
    }

    private void assertValidRepo(final ResultActions result, final ScmRepo repo) throws Exception {
        result.andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.uuid", is(repo.getUuid())))
            .andExpect(jsonPath("$.url", is(repo.getUrl())))
            .andExpect(jsonPath("$.status", is(repo.getCompletionStatus().name())));
    }
    
    private List<ScmRepo> generateAndStoreRandomRepos(final int count) throws Exception {
        final List<ScmRepo> repos = generateRandomRepos(count);
        for (final ScmRepo repo : repos) {
            scmRepository.save(repo);
        }
        
        return repos;
    }
    
    private List<ScmRepo> generateRandomRepos(final int count) throws Exception {
        final List<ScmRepo> repos = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            final String uuid = uuidGen.generateUUID().toString();
            final ScmRepo repo = new ScmRepo();
            repo.setUuid(uuid);
            repo.setBranch("master");
            repo.setUrl(URL_BASE + uuid);
            repos.add(repo);
        }
        
        return repos;
    }
}

package com.aurea.deadcode.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aurea.deadcode.rest.RepoResource;
import com.aurea.deadcode.rest.dto.BasicRepoData;
import com.aurea.deadcode.rest.dto.CompleteRepoData;
import com.aurea.deadcode.rest.dto.RepoListData;
import com.aurea.deadcode.rest.dto.RepoRequest;

import io.swagger.annotations.Api;

@RestController
@Api(value = "Dead Code Scanner")
public class RepoController implements RepoResource {
    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/repos", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<RepoListData> getAllRepos() {
        return new ResponseEntity<RepoListData>(HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/repos", 
    consumes = MediaType.APPLICATION_JSON, 
    produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<BasicRepoData> addRepo(final RepoRequest request) {
        return new ResponseEntity<BasicRepoData>(HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/repos/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<CompleteRepoData> getRepo(@PathVariable("id") final String id) {
        return new ResponseEntity<CompleteRepoData>(HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT, value = "/repos/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<BasicRepoData> forceRepoAnalysis(@PathVariable("id") final String id) {
        return new ResponseEntity<BasicRepoData>(HttpStatus.OK);
    }

}
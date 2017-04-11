package com.aurea.deadcode.controller;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aurea.deadcode.rest.RepoResource;
import com.aurea.deadcode.rest.dto.NewRepoRequest;
import com.aurea.deadcode.rest.dto.BasicRepoData;
import com.aurea.deadcode.rest.dto.FullRepoData;
import com.aurea.deadcode.rest.dto.RepoListData;
import com.aurea.deadcode.service.RepoService;

@RestController
public class RepoController implements RepoResource {
    private static final Logger LOG = LoggerFactory.getLogger(RepoController.class);

    @Autowired
    protected RepoService repoService;

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/repos", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<RepoListData> getAllRepos() {
        LOG.info("GET getAllRepos");
        return new ResponseEntity<>(repoService.getAllRepos(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/repos", 
    consumes = MediaType.APPLICATION_JSON, 
    produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<BasicRepoData> addRepo(@RequestBody final NewRepoRequest request) {
        LOG.info("POST addRepo " + request);
        return new ResponseEntity<>(repoService.create(request.getUrl(), request.getBranch()), HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/repos/{uuid}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<FullRepoData> getRepo(@PathVariable("uuid") final String uuid) {
        LOG.info("GET getRepo " + uuid);
        return new ResponseEntity<>(repoService.read(uuid), HttpStatus.OK);
    }
}
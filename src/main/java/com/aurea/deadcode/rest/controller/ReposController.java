package com.aurea.deadcode.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.WILDCARD;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurea.deadcode.rest.ReposResource;
import com.aurea.deadcode.rest.dto.BasicRepoData;
import com.aurea.deadcode.rest.dto.FullRepoData;
import com.aurea.deadcode.rest.dto.NewRepoRequest;
import com.aurea.deadcode.rest.dto.RepoListData;
import com.aurea.deadcode.service.RepoService;

@RestController
@RequestMapping(value = "/rest/repos", 
    consumes = WILDCARD, 
    produces = APPLICATION_JSON)
public class ReposController implements ReposResource {
    private static final Logger LOG = LoggerFactory.getLogger(ReposController.class);

    @Autowired
    protected RepoService repoService;

    @Override
    @RequestMapping(method = GET)
    public ResponseEntity<RepoListData> getAllRepos() {
        LOG.info("GET getAllRepos");
        return new ResponseEntity<>(repoService.getAllRepos(), HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = POST, consumes = APPLICATION_JSON)
    public ResponseEntity<BasicRepoData> addRepo(@RequestBody final NewRepoRequest request) {
        LOG.info("POST addRepo " + request);
        return new ResponseEntity<>(repoService.create(request.getUrl(), request.getBranch()), HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = GET, value = "/{uuid}")
    public ResponseEntity<FullRepoData> getRepo(@PathVariable("uuid") final String uuid) {
        LOG.info("GET getRepo " + uuid);
        return new ResponseEntity<>(repoService.read(uuid), HttpStatus.OK);
    }
}
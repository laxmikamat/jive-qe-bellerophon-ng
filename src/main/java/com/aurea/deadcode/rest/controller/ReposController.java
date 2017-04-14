package com.aurea.deadcode.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.WILDCARD;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.eclipse.jgit.transport.URIish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurea.deadcode.model.ScmRepo;
import com.aurea.deadcode.rest.ReposResource;
import com.aurea.deadcode.rest.dto.BasicRepoData;
import com.aurea.deadcode.rest.dto.FullRepoData;
import com.aurea.deadcode.rest.dto.NewRepoRequest;
import com.aurea.deadcode.rest.dto.RepoListData;
import com.aurea.deadcode.service.RepoService;
import com.aurea.deadcode.service.converter.DataConverter;
import com.aurea.deadcode.service.exception.BadRequestException;
import com.aurea.deadcode.service.exception.NotFoundException;

@RestController
@RequestMapping(value = "/rest/repos", 
    consumes = WILDCARD, 
    produces = APPLICATION_JSON)
public class ReposController implements ReposResource {
    private static final Logger LOG = LoggerFactory.getLogger(ReposController.class);

    @Autowired
    protected RepoService repoService;

    @Autowired
    @Named("newRepoConverter")
    protected DataConverter<ScmRepo, NewRepoRequest> newRepoConverter;
    
    @Autowired
    @Named("basicDataConverter")
    protected DataConverter<ScmRepo, BasicRepoData> basicConverter;

    @Autowired
    @Named("fullDataConverter")
    protected DataConverter<ScmRepo, FullRepoData> fullConverter;

    @Override
    @RequestMapping(method = GET)
    public ResponseEntity<RepoListData> getAllRepos() {
        LOG.info("GET getAllRepos");
        final List<ScmRepo> allRepos = repoService.getAllRepos();
        final RepoListData response = new RepoListData(allRepos
                .stream()
                .map(basicConverter::model2Dto)
                .collect(Collectors.toList()));
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = POST, consumes = APPLICATION_JSON)
    public ResponseEntity<BasicRepoData> addRepo(@RequestBody final NewRepoRequest request) {
        LOG.info("POST addRepo " + request);
        try {
            if (!new URIish(request.getUrl()).isRemote()) {
                throw new URISyntaxException(request.getUrl(), "Given URL is not a remote Git repo");
            }
        } catch (final URISyntaxException | RuntimeException e) {
            LOG.info("Repo creation error: " + e.getMessage());
            throw new BadRequestException(e.getMessage(), e);
        }
        
        final ScmRepo repo = newRepoConverter.dto2Model(request);
        final BasicRepoData response = basicConverter.model2Dto(repoService.create(repo));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = GET, value = "/{uuid}")
    public ResponseEntity<FullRepoData> getRepo(@PathVariable("uuid") final String uuid) {
        LOG.info("GET getRepo " + uuid);
        final Optional<ScmRepo> repo = Optional.ofNullable(repoService.read(uuid));
        final FullRepoData response = fullConverter.model2Dto(repo.orElseThrow(() -> new NotFoundException(uuid)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
package com.aurea.deadcode.rest;

import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;

import com.aurea.deadcode.rest.dto.BasicRepoData;
import com.aurea.deadcode.rest.dto.CompleteRepoData;
import com.aurea.deadcode.rest.dto.RepoListData;
import com.aurea.deadcode.rest.dto.RepoRequest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface RepoResource {
    @ApiOperation(value = "getAllRepos", produces = MediaType.APPLICATION_JSON,
            notes = "Gets the list of all repositories that have already been added along with their status.")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = RepoListData.class),
            @ApiResponse(code = 500, message = "Failure")
    }) 
    ResponseEntity<RepoListData> getAllRepos();

    @ApiOperation(value = "addRepo",  produces = MediaType.APPLICATION_JSON,  consumes = MediaType.APPLICATION_JSON,
            notes = "Adds GitHub repository to be analyzed agains Dead Code.")
    @ApiParam(value = "Repository request i.e. URL and branch", name = "id", required = true)
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = BasicRepoData.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 409, message = "Conflict - repository already exists", response = BasicRepoData.class),
            @ApiResponse(code = 500, message = "Failure")
    })
    ResponseEntity<BasicRepoData> addRepo(final RepoRequest request);

    @ApiOperation(value = "getRepo", produces = MediaType.APPLICATION_JSON,
            notes = "Gets the Dead Code occurrences from a given github repository.")
    @ApiParam(value = "Repository ID", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CompleteRepoData.class),
            @ApiResponse(code = 404, message = "Repository Not Found"),
            @ApiResponse(code = 500, message = "Failure")
    })
    ResponseEntity<CompleteRepoData> getRepo(final String id);

    @ApiOperation(value = "forceRepoAnalysis", produces = MediaType.APPLICATION_JSON,
            notes = "Forces the Dead Code analysis on given repsitory.")
    @ApiParam(value = "Repository ID", name = "id", required = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BasicRepoData.class),
            @ApiResponse(code = 404, message = "Repository Not Found"),
            @ApiResponse(code = 500, message = "Failure")
    })
    ResponseEntity<BasicRepoData> forceRepoAnalysis(final String id);
}

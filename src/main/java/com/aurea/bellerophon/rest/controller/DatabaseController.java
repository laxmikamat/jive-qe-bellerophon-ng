package com.aurea.bellerophon.rest.controller;

import com.aurea.bellerophon.rest.DatabaseResource;
import com.aurea.bellerophon.service.DatabaseService;
import com.aurea.bellerophon.service.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.WILDCARD;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/database",
    consumes = WILDCARD, 
    produces = APPLICATION_JSON)
public class DatabaseController implements DatabaseResource {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseController.class);

    @Autowired
    protected Map<String, DatabaseService> databaseServiceMap;

    @Override
    @RequestMapping(method = GET, path = "/{path}/{action}/{databaseName}")
    public ResponseEntity<String> get(@PathVariable("path") final String path,
                    @PathVariable("action") final String action,
                    @PathVariable("databaseName") final String databaseName) {

        DatabaseService databaseService = getDatabaseService(path);
        if (action == null) {
            return new ResponseEntity<>("Action is null", HttpStatus.BAD_REQUEST);
        }

        if (databaseName == null) {
            return new ResponseEntity<>("Database name is null", HttpStatus.BAD_REQUEST);
        }

        switch(action.toLowerCase()) {
            case "create":
                databaseService.create(databaseName);
                break;

            case "drop":
                databaseService.drop(databaseName);
                break;

            default:
                return new ResponseEntity<>("Unknown action: " + action, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    @RequestMapping(method = GET, path = "/{path}/list")
    public ResponseEntity<List<String>> list(@PathVariable("path") final String path) {
        return new ResponseEntity<>(getDatabaseService(path).list(), HttpStatus.OK);
    }

    private DatabaseService getDatabaseService(String path) {
        DatabaseService databaseService = databaseServiceMap.get(path);
        if (databaseService == null) {
            throw new ServiceException("Unable to locate service for path: " + path, HttpStatus.NOT_FOUND.value());
        }

        return databaseService;
    }
}
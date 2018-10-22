package com.aurea.bellerophon.rest;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DatabaseResource {
    ResponseEntity<String> get(final String path, final String action, final String databaseName);

    ResponseEntity<List<String>> list(final String path);
}

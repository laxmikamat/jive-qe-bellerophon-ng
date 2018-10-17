package com.aurea.bellerophon.rest;

import org.springframework.http.ResponseEntity;

public interface DatabaseResource {
    ResponseEntity<String> get(final String path, final String action, final String databaseName);
}

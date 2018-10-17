package com.aurea.bellerophon.service;

public interface DatabaseService {
    void drop(String databaseName);
    void create(String databaseName);
}

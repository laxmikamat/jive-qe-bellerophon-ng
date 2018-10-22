package com.aurea.bellerophon.service;

import java.util.List;

public interface DatabaseService {
    void drop(String databaseName);
    void create(String databaseName);
    List<String> list();
}

package com.aurea.deadcode.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class UuidGeneratorImpl implements UuidGenerator {

    @Override
    public UUID generateUUID() {
        return UUID.randomUUID();
    }
}
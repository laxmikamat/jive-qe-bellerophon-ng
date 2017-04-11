package com.aurea.deadcode.notif;

import org.springframework.stereotype.Service;

import com.aurea.deadcode.rest.dto.NewRepoRequest;

@Service
public class NewRepoAddedNotification extends AbstractNotification<EventType, NewRepoRequest> {
}

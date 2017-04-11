package com.aurea.deadcode.notif;

public interface Notification<E extends Enum<E>, T> {
    void notify(Enum<E> type, T content);
}

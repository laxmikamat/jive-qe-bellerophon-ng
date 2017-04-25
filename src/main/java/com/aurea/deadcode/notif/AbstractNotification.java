package com.aurea.deadcode.notif;

import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

import com.google.gson.Gson;

public abstract class AbstractNotification<E extends Enum<E>, T> implements Notification<E, T> {
    private JmsTemplate jmsTemplate;

    @Autowired
    @Named("reposQueue")
    protected Queue queue;

    @Autowired
    public void setConnectionFactory(final ConnectionFactory cf) {
        jmsTemplate = new JmsTemplate(cf);
    }

    @Override
    public void notify(final Enum<E> eventType, final T event) {
        jmsTemplate.send(queue, session -> {
            final TextMessage msg = session.createTextMessage(convertEventToText(event));
            msg.setStringProperty(eventType.getClass().getSimpleName(), eventType.toString());
            return msg;
        });
    }

    protected String convertEventToText(final T event) {
        return new Gson().toJson(event);
    }
}
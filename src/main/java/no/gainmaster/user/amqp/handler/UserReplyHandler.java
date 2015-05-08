package no.gainmaster.user.amqp.handler;

import org.springframework.stereotype.Component;

@Component
public class UserReplyHandler {

    public void handleMessage(String text) {
        //TODO: Process replies from services
        System.out.println("RABBITMQ: Got reply: \n" + text);
    }
}

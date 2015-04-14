package no.gainmaster.user.amqp.handler;

/**
 * Created by lorre on 4/13/15.
 */

public class UserReplyHandler {

    public void handleMessage(String text) {

        //TODO: Process replies
        System.out.println("RABBITMQ: Got reply: " + text);
    }
}

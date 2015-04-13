package gainmaster.service.user.web.amqp.handler;

/**
 * Created by lorre on 4/13/15.
 */

public class UserReplyHandler {

    public void handleMessage(String text) {
        //TODO: Process replies
        System.out.println("Reply: " + text);
    }
}

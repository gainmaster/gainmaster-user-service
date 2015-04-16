package no.gainmaster.user.amqp.handler;

import no.gainmaster.user.service.User;
import no.gainmaster.user.service.UserNotFoundException;
import no.gainmaster.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by lorre on 4/16/15.
 */

@Component
public class GetCredentialsHandler {

    @Autowired
    private UserService userService;

    public String handleMessage(String text) {

        //TODO: Get user
        System.out.println("RABBITMQ - Got request " + text + ". Sending reply...");

        User user;
        try {
            user = userService.getUserFromUsername(text);
        } catch (UserNotFoundException e){
            System.out.println(e);
            return "";
        }

        return user.getPassword();
    }
}

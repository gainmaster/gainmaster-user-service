package no.gainmaster.user.amqp.handler;

import no.gainmaster.user.service.User;
import no.gainmaster.user.service.UserNotFoundException;
import no.gainmaster.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticateUserHandler {

    @Autowired
    private UserService userService;

    public String handleMessage(String request) {
        System.out.println("RABBITMQ: Got request " + request);
        User user;

        String requestUsername = request.split(":")[0];
        String requestPassword = request.split(":")[1];

        try {
            user = userService.getUserFromUsername(requestUsername);
        } catch (UserNotFoundException e){
            return "false";
        }

        if(user.getPassword().equals(requestPassword)) return "true";

        return "false";
    }
}

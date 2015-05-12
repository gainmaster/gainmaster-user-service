package no.gainmaster.user.service;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO: NOF_FOUND
@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class UserNotFoundException extends RuntimeException {

}

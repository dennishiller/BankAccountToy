package de.dennishiller.cofa_challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class AccountNotCreatedException extends RuntimeException {

    public AccountNotCreatedException() {
        super("Account could not be created");
    }
}

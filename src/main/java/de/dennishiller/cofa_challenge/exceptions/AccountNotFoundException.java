package de.dennishiller.cofa_challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO iban mit übergeben (alle exceptions)
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super("Given Account not found");
    }
}

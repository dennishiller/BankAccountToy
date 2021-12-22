package de.dennishiller.cofa_challenge.exceptions;

public class InvalidReferenceAccountException extends InvalidAccountTypeException {

    public InvalidReferenceAccountException() {
        super("Reference account has to be a checking account");
    }
}

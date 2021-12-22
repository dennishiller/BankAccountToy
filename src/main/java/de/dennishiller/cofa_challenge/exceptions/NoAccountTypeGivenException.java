package de.dennishiller.cofa_challenge.exceptions;

public class NoAccountTypeGivenException extends InvalidAccountTypeException {

    public NoAccountTypeGivenException() {
        super("Please provide an accounttype");
    }
}

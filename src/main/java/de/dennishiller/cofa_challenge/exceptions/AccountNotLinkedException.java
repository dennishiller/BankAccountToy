package de.dennishiller.cofa_challenge.exceptions;

public class AccountNotLinkedException extends InvalidAccountTypeException {

    public AccountNotLinkedException() {
        super("Savings account is not linked to this checking account");
    }
}

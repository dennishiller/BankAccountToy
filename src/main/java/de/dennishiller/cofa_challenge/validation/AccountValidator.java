package de.dennishiller.cofa_challenge.validation;

import de.dennishiller.cofa_challenge.enums.AccountType;
import de.dennishiller.cofa_challenge.exceptions.AccountNotLinkedException;
import de.dennishiller.cofa_challenge.exceptions.InvalidInputException;
import de.dennishiller.cofa_challenge.exceptions.NoAccountTypeGivenException;
import de.dennishiller.cofa_challenge.model.Account;

public class AccountValidator {

    public static void validateIban(String iban){
        if (iban == null || iban.isEmpty() || iban.isBlank())
            throw new InvalidInputException("Iban is not given");
    }

    public static void validateAccount(Account account){
        validateIban(account.getIban());
        validateAccountType(account.getType());
    }

    public static void validateAccountReference(String iban){
        try{
            validateIban(iban);
        } catch (InvalidInputException e){
            throw new InvalidInputException("Reference Iban is not given");
        }
    }

    public static boolean validateReferenceAccount(Account saving, Account checking){
        if (saving.getCheckingAccount().isBlank() || saving.getCheckingAccount().isEmpty()) {
            throw new AccountNotLinkedException();
        }
        
        if(!saving.getType().equals(AccountType.SAVING)
                || !checking.getType().equals(AccountType.CHECKING)){
            throw new InvalidInputException("Account types does not match the requirements");
        }

        return saving.getCheckingAccount().equals(checking.getIban());
    }

    public static void validateAmount(double amount){
        if (amount <= 0) {
            throw new InvalidInputException("Amount to withdraw must be greater 0");
        }
    }

    private static void validateAccountType(AccountType type){
        if (type == null)
            throw new NoAccountTypeGivenException();
    }
}

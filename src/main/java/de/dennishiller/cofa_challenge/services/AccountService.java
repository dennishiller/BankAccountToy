package de.dennishiller.cofa_challenge.services;

import de.dennishiller.cofa_challenge.exceptions.*;
import de.dennishiller.cofa_challenge.model.Account;
import de.dennishiller.cofa_challenge.dto.DepositMoneyDTO;
import de.dennishiller.cofa_challenge.model.TransactionHistoryEntry;
import de.dennishiller.cofa_challenge.dto.TransferMoneyDTO;
import de.dennishiller.cofa_challenge.enums.AccountType;
import de.dennishiller.cofa_challenge.enums.TransactionType;
import de.dennishiller.cofa_challenge.repository.AccountRepository;
import de.dennishiller.cofa_challenge.interfaces.IAccountService;
import de.dennishiller.cofa_challenge.validation.AccountValidator;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService implements IAccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionHistoryService transactionHistoryService;

    // to discuss
    public boolean transferMoney(@NonNull TransferMoneyDTO transferMoneyDTO) {
        Account accountWithdraw = getAccountFromIban(transferMoneyDTO.getIbanWithdraw());
        Account accountDeposit = getAccountFromIban(transferMoneyDTO.getIbanDeposit());
        double amount = transferMoneyDTO.getAmount();

        return doTransfer(accountWithdraw, accountDeposit, amount);
    }

    public List<Account> getAccountsByType(@NonNull AccountType type) {
        return accountRepository.findAccountsByType(type);
    }

    public Account depositMoney(@NonNull DepositMoneyDTO depositMoneyDTO) {
        AccountValidator.validateIban(depositMoneyDTO.getIban());
        Account toDeposit = getAccountFromIban(depositMoneyDTO.getIban());

        return deposit(toDeposit, depositMoneyDTO.getAmount());
    }

    public double getAccountBalance(@NonNull String iban) {
        AccountValidator.validateIban(iban);
        Account account = getAccountFromIban(iban);

        return account.getBalance();
    }

    public Account createAccount(@NonNull Account account) {
        AccountValidator.validateAccount(account);
        if (accountRepository.findAccountByIban(account.getIban()).isPresent())
            throw new InvalidInputException("Given iban already in use");

        if (account.getType().equals(AccountType.SAVING)) {

            AccountValidator.validateAccountReference(account.getCheckingAccount());
            Account referenceAccount = accountRepository.findAccountByIban(account.getCheckingAccount())
                    .orElseThrow(AccountNotFoundException::new);

            if (!referenceAccount.getType().equals(AccountType.CHECKING))
                throw new InvalidReferenceAccountException();

            if (account.getCheckingAccount().length() > 0
                    && !account.getCheckingAccount().isBlank()) {
                return accountRepository.save(account);
            }
        } else {
            return accountRepository.save(account);
        }
        throw new AccountNotCreatedException();
    }

    private Account getAccountFromIban(String iban) {
        AccountValidator.validateIban(iban);

        return accountRepository.findAccountByIban(iban).orElseThrow(AccountNotFoundException::new);
    }

    private Account deposit(Account toDeposit, double amount) {
        AccountValidator.validateAmount(amount);

        toDeposit.setBalance(toDeposit.getBalance() + amount);
        transactionHistoryService.addTransaction(new TransactionHistoryEntry(toDeposit.getIban(), TransactionType.DEPOSIT, amount));

        return accountRepository.save(toDeposit);
    }

    private Account withdraw(Account toWithdraw, double amount) {
        AccountValidator.validateAmount(amount);

        toWithdraw.setBalance(toWithdraw.getBalance() - amount);
        transactionHistoryService.addTransaction(new TransactionHistoryEntry(toWithdraw.getIban(), TransactionType.WITHDRAW, amount));

        return accountRepository.save(toWithdraw);
    }

    private boolean canTransferMoney(AccountType type) {
        return canTransferTo(type).contains(type);
    }

    private void transferMoney(Account withdraw, Account deposit, double amount) {
        withdraw(withdraw, amount);
        deposit(deposit, amount);
    }

    private boolean doTransfer(Account accountWithdraw, Account accountDeposit, double amount) {
        if (canTransferMoney(accountWithdraw.getType())) {
            if (accountWithdraw.getType().equals(AccountType.SAVING)) {
                if (AccountValidator.validateReferenceAccount(accountWithdraw, accountDeposit)) {
                    transferMoney(accountWithdraw, accountDeposit, amount);
                    return true;
                } else {
                    return false;
                }
            }
            transferMoney(accountWithdraw, accountDeposit, amount);
            return true;
        }
        return false;
    }

    private static EnumSet<AccountType> canTransferTo(AccountType type) {
        switch (type) {
            case CHECKING:
                return EnumSet.of(AccountType.CHECKING, AccountType.SAVING, AccountType.LOAN);
            case SAVING:
                return EnumSet.of(AccountType.CHECKING, AccountType.SAVING);
            default:
                return EnumSet.noneOf(AccountType.class);
        }
    }
}

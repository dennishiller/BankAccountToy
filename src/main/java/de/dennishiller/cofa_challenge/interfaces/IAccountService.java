package de.dennishiller.cofa_challenge.interfaces;

import de.dennishiller.cofa_challenge.model.Account;
import de.dennishiller.cofa_challenge.dto.DepositMoneyDTO;
import de.dennishiller.cofa_challenge.dto.TransferMoneyDTO;
import de.dennishiller.cofa_challenge.enums.AccountType;

import java.util.List;

public interface IAccountService {

    boolean transferMoney(TransferMoneyDTO transferMoneyDTO);

    List<Account> getAccountsByType(AccountType type);

    Account depositMoney(DepositMoneyDTO depositMoneyDTO);

    double getAccountBalance(String iban);

    Account createAccount(Account account);
}

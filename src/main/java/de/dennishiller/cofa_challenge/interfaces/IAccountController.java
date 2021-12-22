package de.dennishiller.cofa_challenge.interfaces;

import de.dennishiller.cofa_challenge.dto.DepositMoneyDTO;
import de.dennishiller.cofa_challenge.dto.TransferMoneyDTO;
import de.dennishiller.cofa_challenge.model.*;
import de.dennishiller.cofa_challenge.enums.AccountType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface IAccountController {

    @PutMapping("/deposit")
    Account depositMoney(@RequestBody DepositMoneyDTO depositMoneyDTO);

    @PutMapping("/transfer")
    boolean transferMoney(@RequestBody TransferMoneyDTO transferMoneyDTO);

    @GetMapping("/balance/{iban}")
    double getAccountBalance(@PathVariable String iban);

    @GetMapping("/types/{type}")
    List<Account> getAccountsByType(@PathVariable AccountType type);

    @GetMapping("/transaction/{iban}")
    List<TransactionHistoryEntry> getTransactionHistory(@PathVariable String iban);

    @PostMapping("/create")
    Account createAccount(@RequestBody Account account);
}

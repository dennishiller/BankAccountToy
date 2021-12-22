package de.dennishiller.cofa_challenge.controller;

import de.dennishiller.cofa_challenge.dto.DepositMoneyDTO;
import de.dennishiller.cofa_challenge.dto.TransferMoneyDTO;
import de.dennishiller.cofa_challenge.interfaces.IAccountController;
import de.dennishiller.cofa_challenge.model.*;
import de.dennishiller.cofa_challenge.enums.AccountType;
import de.dennishiller.cofa_challenge.exceptions.AccountNotFoundException;
import de.dennishiller.cofa_challenge.exceptions.InvalidAccountTypeException;
import de.dennishiller.cofa_challenge.exceptions.InvalidInputException;
import de.dennishiller.cofa_challenge.services.AccountService;
import de.dennishiller.cofa_challenge.services.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController implements IAccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionHistoryService transactionHistoryServiceImpl;

    @PutMapping("/deposit")
    public Account depositMoney(@RequestBody DepositMoneyDTO depositMoneyDTO) {
        return accountService.depositMoney(depositMoneyDTO);
    }

    @PutMapping("/transfer")
    public boolean transferMoney(@RequestBody TransferMoneyDTO transferMoneyDTO) {
        return accountService.transferMoney(transferMoneyDTO);
    }

    @GetMapping("/balance/{iban}")
    public double getAccountBalance(@PathVariable String iban) {
        return accountService.getAccountBalance(iban);
    }

    @GetMapping("/types/{type}")
    public List<Account> getAccountsByType(@PathVariable AccountType type) {
        return accountService.getAccountsByType(type);
    }

    @GetMapping("/transaction/{iban}")
    public List<TransactionHistoryEntry> getTransactionHistory(@PathVariable String iban) {
        return transactionHistoryServiceImpl.getTransactionHistory(iban);
    }

    @PostMapping("/create")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidAccountTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidAccountTypeException(InvalidAccountTypeException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(exception.getMessage());
    }
}

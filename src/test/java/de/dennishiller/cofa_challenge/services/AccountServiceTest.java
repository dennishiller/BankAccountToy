package de.dennishiller.cofa_challenge.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.dennishiller.cofa_challenge.dto.DepositMoneyDTO;
import de.dennishiller.cofa_challenge.dto.TransferMoneyDTO;
import de.dennishiller.cofa_challenge.enums.AccountType;
import de.dennishiller.cofa_challenge.exceptions.InvalidAccountTypeException;
import de.dennishiller.cofa_challenge.exceptions.InvalidInputException;
import de.dennishiller.cofa_challenge.model.Account;
import de.dennishiller.cofa_challenge.repository.AccountRepository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountService.class})
@ExtendWith(SpringExtension.class)
class AccountServiceTest {
    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @MockBean
    private TransactionHistoryService transactionHistoryService;

    @Test
    void accountServiceShouldBeInitialized() {
        assertThat(accountService);
    }

    @Test
    void createAccountShouldReturnAccount() {
        Account account = new Account("DE01",30.5,AccountType.CHECKING);

        when(accountRepository.save(account)).thenReturn(account);
        Account capturedAccount = accountService.createAccount(account);

        assertThat(account.equals(capturedAccount));
    }

    @Test
    void depositMoneyShouldWithdrawMoney(){
        Account savingAccount = new Account("DE01",4333.0,AccountType.SAVING);
        Account checkingAccount = new Account("DE02",3333.0,AccountType.CHECKING);
        TransferMoneyDTO tmDTO = new TransferMoneyDTO("DE01","DE02",333);
        savingAccount.setCheckingAccount(savingAccount.getIban());

        when(accountRepository.findAccountByIban("DE01")).thenReturn(Optional.of(savingAccount));
        when(accountRepository.findAccountByIban("DE02")).thenReturn(Optional.of(checkingAccount));

        accountService.transferMoney(tmDTO);

        assertThat(savingAccount.getBalance() == 4000.0);
        assertThat(checkingAccount.getBalance() == 3666.0);
    }
}


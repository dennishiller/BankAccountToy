package de.dennishiller.cofa_challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.dennishiller.cofa_challenge.dto.DepositMoneyDTO;
import de.dennishiller.cofa_challenge.enums.AccountType;
import de.dennishiller.cofa_challenge.model.Account;
import de.dennishiller.cofa_challenge.services.AccountService;
import de.dennishiller.cofa_challenge.services.TransactionHistoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(value = AccountController.class)
class AccountControllerTest {

    @Autowired
    MockMvc mvc;
    @MockBean
    AccountService accountService;
    @MockBean
    TransactionHistoryService transactionHistoryService;

    @Test
    void tryDepositMoney_shouldReturn200() throws Exception {
        String uri = "http://localhost:8080/account/deposit";

        DepositMoneyDTO dmDto = new DepositMoneyDTO("DE01",300);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(dmDto);

        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders
                                .put(uri)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = result.getResponse().getStatus();

        assertEquals(200, status);
    }

    @Test
    void getAccountsByTypeWithValidType_shouldReturn200() throws Exception {
        String uri = "http://localhost:8080/account/types/CHECKING";

        List<Account> accounts = Collections.emptyList();
        Mockito.when(accountService.getAccountsByType(AccountType.CHECKING)).thenReturn(accounts);

        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders
                                .get(uri)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = result.getResponse().getStatus();

        assertEquals(200, status);
    }

    @Test
    void getAccountsByTypeNoValidTypeGiven_shouldReturn400() throws Exception {
        String uri = "http://localhost:8080/account/types/NotExisting";

        List<Account> accounts = Collections.emptyList();
        Mockito.when(accountService.getAccountsByType(AccountType.CHECKING)).thenReturn(accounts);

        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders
                                .get(uri)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = result.getResponse().getStatus();

        assertEquals(400, status);
    }

    @Test
    void tryCreateAccount_shouldReturn200() throws Exception {
        String uri = "http://localhost:8080/account/create";

        Account account = new Account("DE33", 33.50, AccountType.CHECKING);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(account);

        MvcResult result = mvc.perform(
                        MockMvcRequestBuilders
                                .post(uri)
                                .content(json)
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = result.getResponse().getStatus();

        assertEquals(200, status);
    }
}
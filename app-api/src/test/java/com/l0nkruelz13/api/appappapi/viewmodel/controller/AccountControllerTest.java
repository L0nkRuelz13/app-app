package com.l0nkruelz13.api.appappapi.viewmodel.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.l0nkruelz13.api.appappapi.model.model.Account;
import com.l0nkruelz13.api.appappapi.model.model.AccountException;
import com.l0nkruelz13.api.appappapi.viewmodel.service.AccountService;

@Tag("controller-tier")
@SpringBootTest
public class AccountControllerTest {

    private AccountService mockAccountService;
    
    @BeforeEach
    void testSetup() {
        mockAccountService = mock(AccountService.class);
    }

    @Test
    void testCreateAccount() throws IOException, AccountException {
        //setup account varibales
        Account accountSuccess = new Account(1, "test", "test");
        Account accountFail = new Account(2, "test2", "test2");
        //setup mock account service behavior
        when(mockAccountService.createAccount(accountSuccess)).thenReturn(accountSuccess);
        when(mockAccountService.createAccount(accountFail)).thenThrow(new AccountException("test"));
        when(mockAccountService.createAccount(null)).thenThrow(new IOException("test"));
        //run tests
        assertEquals(mockAccountService.createAccount(accountSuccess), accountSuccess);
        assertThrows(AccountException.class, () -> {
            mockAccountService.createAccount(accountFail);
        });
        assertThrows(IOException.class, () -> {
            mockAccountService.createAccount(null);
        });
    }
}

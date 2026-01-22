package com.l0nkruelz13.api.appappapi.viewmodel.service;

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
import com.l0nkruelz13.api.appappapi.model.persistence.AccountFileDAO;

@Tag("service-tier")
@SpringBootTest
public class AccountServiceTest {
    
    private AccountFileDAO mockAccountFileDAO;

    @BeforeEach
    void testSetup() {
        mockAccountFileDAO = mock(AccountFileDAO.class);
    }

    @Test
    void testCreateAccount() throws IOException, AccountException {
        //setup test variables
        Account success = new Account(1, "test1", "test1");
        Account failed = new Account(2, "test2", "test2");
        //setup mock behavior 
        when(mockAccountFileDAO.createAccount(success)).thenReturn(success);
        when(mockAccountFileDAO.createAccount(failed)).thenThrow(new AccountException(""));
        when(mockAccountFileDAO.createAccount(null)).thenThrow(new IOException(""));
        //run tests
        assertEquals(success, mockAccountFileDAO.createAccount(success));
        assertThrows(AccountException.class, () -> {
            mockAccountFileDAO.createAccount(failed);
        });
        assertThrows(IOException.class, () -> {
            mockAccountFileDAO.createAccount(null);
        });
    }
}

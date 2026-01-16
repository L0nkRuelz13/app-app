package com.l0nkruelz13.api.appappapi.model.persistence;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.l0nkruelz13.api.appappapi.model.model.Account;
import com.l0nkruelz13.api.appappapi.model.model.AccountException;

@Tag("Persistance-tier")
@SpringBootTest
public class AccountFileDAOTest {
    
    //define vars
    private ObjectMapper mockObjectMapper;
    private Account[] testAccounts;
    private AccountFileDAO accountFileDAO;

    @BeforeEach
    void testSetup() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        testAccounts = new Account[3];
        testAccounts[0] = new Account(1, "user1", "pass1");
        testAccounts[1] = new Account(2, "user2", "pass2");
        testAccounts[2] = new Account(3, "user3", "pass3");

        when(mockObjectMapper.readValue(new File("test"), Account[].class)).thenReturn(testAccounts);

        accountFileDAO = new AccountFileDAO("test", mockObjectMapper);
    }

    @Test
    void testCreateAccount() throws IOException, AccountException {
        //test failure due to account already existing
        assertThrows(AccountException.class, () -> {
            accountFileDAO.createAccount(new Account(1, "user1", "pass1"));
        });

        //test failure due to username too short
        assertThrows(AccountException.class, () -> {
            accountFileDAO.createAccount(new Account(1, "", "pass"));
        });

        //test failure due to password too short
        assertThrows(AccountException.class, () -> {
            accountFileDAO.createAccount(new Account(1, "a", ""));
        });

        //test a successful account creation
        Account newAccount = new Account(4, "user4", "user4");
        assertEquals(newAccount, accountFileDAO.createAccount(newAccount));
    }

    @Test
    void testDeleteAccount() throws IOException {
        //test failure due to account not existing
        assertFalse(accountFileDAO.deleteAccount(new Account(4, "test", "test")));

        //test sucessful delete
        assertTrue(accountFileDAO.deleteAccount(new Account(1, "user1", "pass1")));
        assertNull(accountFileDAO.getAccount("user1"));
    }

    @Test
    void testGetAccount() throws IOException {
        //test sucessful "get"
        Account account = new Account(1, "user1", "pass1");
        assertEquals(account, accountFileDAO.getAccount("user1"));

        //test failure
        assertEquals(null, accountFileDAO.getAccount("fake account"));
    }
}

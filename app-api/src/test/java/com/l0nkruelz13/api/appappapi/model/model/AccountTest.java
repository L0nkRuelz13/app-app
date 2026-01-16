package com.l0nkruelz13.api.appappapi.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * JUnit test suite for the Account class
 * 
 * @author L0nkRuelz13
 */
@Tag("Model-tier")
@SpringBootTest
public class AccountTest {
    
    //define variables
    int id;
    String username;
    String password;
    Account account;

    /**
     * setup the tests
     */
    @BeforeEach
    void testSetup() {
        id = 2;
        username = "username";
        password = "password";
        account = new Account(id, username, password);
    }

    /**
     * Test the "getter" functions
     */
    @Test
    void testGetters() {
        assertEquals(id, account.getId());
        assertEquals(password, account.getPassword());
        assertEquals(username, account.getUsername());
    }
}

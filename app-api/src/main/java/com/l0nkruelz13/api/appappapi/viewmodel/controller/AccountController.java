package com.l0nkruelz13.api.appappapi.viewmodel.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.l0nkruelz13.api.appappapi.model.model.Account;
import com.l0nkruelz13.api.appappapi.model.model.AccountException;
import com.l0nkruelz13.api.appappapi.viewmodel.service.AccountService;

/**
 * HTTP Controller for interacting with the Account class
 * 
 * @author L0nkRuelz13
 */
@RestController
@RequestMapping("accounts")
public class AccountController {
    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());
    private final AccountService accountService;

    /**
     * Create the instance of the Account Controller\
     * 
     * @param accountService account service to perform the operations
     */
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    /**
     * Responds to POST request by creating account
     * 
     * @param account the account to create
     * 
     * @return Response Entity with OK if account created successfully;
     * Response Entity with CONFLICT if issue creating account;
     * Response Entity with INTERNAL SERVER ERROR otherwise
     * 
     * @throws IOException if issue with underlying storage
     * @throws AccountException if issue creating account, e.g. username taken, blank password, etc.
     */
    @PostMapping("")
    public ResponseEntity<Object> createAccount(@RequestBody Account account) throws IOException, AccountException {
        LOG.log(Level.INFO, "POST /accounts/{0}", account);

        try {
            Account newAccount = this.accountService.createAccount(account);
            return new ResponseEntity<>(newAccount, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (AccountException e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.CONFLICT);
        }
    }
}

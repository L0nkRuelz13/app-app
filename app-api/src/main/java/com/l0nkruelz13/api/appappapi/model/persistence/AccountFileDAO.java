package com.l0nkruelz13.api.appappapi.model.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.l0nkruelz13.api.appappapi.model.model.Account;
import com.l0nkruelz13.api.appappapi.model.model.AccountException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implements the functionality for the json file based persistence for {@linkplain Account Accounts}
 * 
 * @author L0nkRuelz13
 */
@Component
public class AccountFileDAO {
    
    Map<String,Account> accounts; //local cache of accounts
                                  //key is account's uername, value is the account object

    private final ObjectMapper objectMapper;

    private static int nextId; //next user ID in line
    private final String filename;

    //static strings for account error messages
    private static final String USERNAME_EXISTS = "Username is already in use by another account";
    private static final String SHORT_NAME = "Username is too short";
    private static final String SHORT_PASSWORD = "Password is too short";

    /**
     * Initialize the instacne of the Account File DAO
     * @param filename the filename containing the account data
     * @param objectMapper objectMapper to interface with the file
     * @throws IOException if issue with underlying storage
     */
    public AccountFileDAO(@Value("${accounts.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Return the next account id and increase the static value
     * @return the next account id
     */
    private static synchronized int nextId() {
        int id = nextId;
        nextId++;
        return id;
    }

    /**
     * set the value of the next account id
     * @param id the value to set for nextId
     */
    private static synchronized void setNextId(int id) {
        nextId = id;
    }

    /**
     * loads the account data into local cache
     * @return true if data loaded successfully
     * @throws IOException if issue with underlying storage
     */
    private boolean load() throws IOException {
        this.accounts = new TreeMap<>();

        Account[] accountArray = objectMapper.readValue(new File(this.filename), Account[].class);

        setNextId(2);
        for (Account account: accountArray) {
            accounts.put(account.getUsername(), account);
            if (account.getId() > nextId) {setNextId(account.getId());}
        }
        nextId();
        return true;
    }

    /**
     * Get an array of all the accounts in the cache
     * @return the array containing the accounts
     */
    private Account[] getAccountArray() {
        ArrayList<Account> accountArrayList = new ArrayList<>();

        for (Account account : accounts.values()) {
            accountArrayList.add(account);
        }

        Account[] accountArray = new Account[accountArrayList.size()];
        accountArrayList.toArray(accountArray);

        return accountArray;
    }

    /**
     * Save the currently cached data into the account file
     * @return true if file saved successfully
     * @throws IOException if issue with underlying storage
     */
    private boolean save() throws IOException {
        Account[] accountArray = getAccountArray();

        objectMapper.writeValue(new File(this.filename), accountArray);
        
        return true;
    }

    /**
     * Create a new account and add it to the file
     * @param account the account to create
     * @return the created account
     * @throws IOException if issue with underlying storage
     * @throws AccountException if invalid username or password
     */
    public Account createAccount(Account account) throws IOException, AccountException {
        synchronized(accounts) {
            //check if account already exists
            if (accounts.containsKey(account.getUsername())) {
                throw new AccountException(USERNAME_EXISTS);
            }
            //check if username or password are too short
            if (account.getUsername().isEmpty()) {
                throw new AccountException(SHORT_NAME);
            }
            if (account.getPassword().isEmpty()) {
                throw new AccountException(SHORT_PASSWORD);
            }

            //create the new account
            Account newAccount = new Account(nextId(), account.getUsername(), account.getPassword());
            accounts.put(newAccount.getUsername(), newAccount);
            save();
            return newAccount;
        }
    }

    /**
     * delete an account from the file
     * @param account the account to delete
     * @return true if deleted successfully, false otherwise
     * @throws IOException if issue with underlying storage
     */
    public boolean deleteAccount(Account account) throws IOException {
        synchronized(accounts) {
            if(accounts.containsKey(account.getUsername())) {
                accounts.remove(account.getUsername());
                save();
                return true;
            }
            return false;
        }
    }

    /**
     * Retrieves an account from the file using it's username 
     * @param username the username of the account to get
     * @return the account associated with that username, otherwise null
     * @throws IOException if issue with underlying storage
     */
    public Account getAccount(String username) throws IOException {
        synchronized(accounts) {
            if (accounts.containsKey(username)) {
                return accounts.get(username);
            }
            return null;
        }
    }
}

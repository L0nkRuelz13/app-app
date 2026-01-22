package com.l0nkruelz13.api.appappapi.viewmodel.service;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.l0nkruelz13.api.appappapi.model.model.Account;
import com.l0nkruelz13.api.appappapi.model.model.AccountException;
import com.l0nkruelz13.api.appappapi.model.persistence.AccountFileDAO;

@Component
public class AccountService {
    private final AccountFileDAO accountDao;
    
    public AccountService(AccountFileDAO accountDao) {
        this.accountDao = accountDao;
    }

    public Account createAccount(Account account) throws IOException, AccountException {
        Account created = this.accountDao.createAccount(account);
        return created;
    }
}

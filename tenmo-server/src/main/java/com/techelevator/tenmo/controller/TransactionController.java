package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransactionController {

    private AccountDao accountDao;




    public TransactionController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping(path = "/account/{userId}")
    public Account getAccountBalance(@PathVariable int userId) {
        return accountDao.getBalanceByUserId(userId);
    }



}

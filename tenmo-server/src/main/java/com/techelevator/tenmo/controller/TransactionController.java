package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransactionController {

    private AccountDao accountDao;
    private UserDao userDao;

    public TransactionController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @GetMapping(path = "/account")
    public Account getAccountBalance(Principal principal) {
        return accountDao.getBalanceByUserName(principal.getName());
    }

    @PreAuthorize("permitAll")
    @GetMapping(path = "/users")
    public List<User> userList() {
        return userDao.findAll();
    }
}
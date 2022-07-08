package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TEnmoController {

    private AccountDao accountDao;
    private UserDao userDao;
    private TransferDao transferDao;

    public TEnmoController(AccountDao accountDao, UserDao userDao, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferDao = transferDao;
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


    @PostMapping(path = "/transfer")
    public void transfer(@Valid @RequestBody TransferDTO newTransfer, Principal principal) {
        int verifyAmount = (newTransfer.getAmount().compareTo(accountDao.getBalanceAmountByUserName(principal.getName())));
        int verifyGreaterThanZero = newTransfer.getAmount().compareTo(BigDecimal.ZERO);

        if (verifyAmount == -1 && verifyGreaterThanZero >= 0) {
            transferDao.createTransfer(newTransfer.getToUserId(), newTransfer.getAmount(), userDao.findIdByUsername(principal.getName()));
            accountDao.withdraw(userDao.findIdByUsername(principal.getName()), newTransfer.getAmount());
            accountDao.deposit(newTransfer.getToUserId(), newTransfer.getAmount());
        } else {
            System.out.println("Transfer amount must be greater than 0 and not exceed your current balance");
        }
    }

    @GetMapping(path = "/transfer")
    public List<Transfer> transferList(Principal principal) {
        return transferDao.findYourTransfers(userDao.findIdByUsername(principal.getName()));
    }

    @GetMapping(path = "/transfer/{transferId}")
    public Transfer transferById(@PathVariable Integer transferId) {
        return transferDao.findTransferByTransferId(transferId);
    }

}
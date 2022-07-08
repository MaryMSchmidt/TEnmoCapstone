package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.security.UserNotActivatedException;

import java.math.BigDecimal;

public interface AccountDao {

    Account getBalanceByUserName(String username);

    BigDecimal withdraw(int userId, BigDecimal amount);

    BigDecimal deposit(int userId, BigDecimal amount);

    BigDecimal getBalanceAmountByUserName(String username);

}

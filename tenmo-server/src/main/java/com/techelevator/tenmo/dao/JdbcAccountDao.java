package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.security.UserNotActivatedException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getBalanceByUserName(String username) throws UserNotActivatedException {
        String sql = "SELECT account_id, account.user_id, balance FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE username = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        try {
            if (rowSet.next()) {
                return mapRowToAccount(rowSet);
            }
        } catch (UserNotActivatedException e) {
            System.err.println("didn't work" + e.getMessage());
        }
        return null;
    }
    @Override
    public BigDecimal getBalanceAmountByUserName(String username) throws UserNotActivatedException {
        String sql = "SELECT balance FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE username = ?";
        BigDecimal amount = (jdbcTemplate.queryForObject(sql, BigDecimal.class,username));

        return amount;
    }


    @Override
    public BigDecimal withdraw(int userId, BigDecimal amount){
        Account account = new Account();
        String sql = "UPDATE account SET balance = (balance - ?) WHERE user_id = ?";
        jdbcTemplate.update(sql, amount, userId);

        return null;
    }

    @Override
    public BigDecimal deposit(int userId, BigDecimal amount) {
        Account account = new Account();
        String sql = "UPDATE account SET balance = (balance + ?) WHERE user_id = ?";
        jdbcTemplate.update(sql, amount, userId);

        return null;
    }

    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }

}

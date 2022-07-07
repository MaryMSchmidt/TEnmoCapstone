package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public int createTransfer(int toUserId, BigDecimal amount, int fromUserId) {
        String sql = "INSERT INTO transfer(to_user_id, amount, from_user_id)" +
                " VALUES (?, ?, ?) RETURNING transfer_id";
        Integer newTransferId;
        newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, toUserId, amount, fromUserId);
       return newTransferId;

    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setToUserId(rs.getInt("to_user_id"));
        transfer.setAmount(rs.getBigDecimal("amount"));
        transfer.setFromUserId(rs.getInt("from_user_id"));
        transfer.setApprovalStatus(rs.getBoolean("approval_status"));
        return transfer;
    }


}

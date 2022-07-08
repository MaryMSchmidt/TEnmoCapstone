package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Transfer> findYourTransfers(int userId) {
        List<Transfer> transferList = new ArrayList<>();
        String sql = "SELECT transfer_id, to_user_id, amount, from_user_id, " +
                "approval_status FROM transfer WHERE to_user_id = ? OR from_user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transferList.add(transfer);
        }
        return transferList;
    }

    @Override
    public Transfer findTransferByTransferId(Integer transferId) {
        Transfer transferById = new Transfer();
        String sql = "SELECT transfer_id, to_user_id, amount, from_user_id, " +
                "approval_status FROM transfer WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        if (results.next()) {
            transferById = mapRowToTransfer(results);
        }
        return transferById;
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

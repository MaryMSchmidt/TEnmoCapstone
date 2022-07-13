package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.TransferNotFoundException;
import com.techelevator.tenmo.exception.UserNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

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
    public int createTransfer(int toUserId, BigDecimal amount, int fromUserId) throws UserNotFoundException {
        String sql = "INSERT INTO transfer(to_user_id, amount, from_user_id)" +
                " VALUES (?, ?, ?) RETURNING transfer_id";
        Integer newTransferId;
        newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, toUserId, amount, fromUserId);

        return newTransferId;


    }

    @Override
    public List<Transfer> findYourTransfers(int userId) throws UserNotFoundException {
        List<Transfer> transferList = new ArrayList<>();
        String sql = "SELECT transfer_id, to_user_id, amount, from_user_id, " +
                "approval_status FROM transfer WHERE to_user_id = ? OR from_user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
        boolean userIdExists = false;
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transferList.add(transfer);
        }
        for (Transfer transfer : transferList) {
            if (transfer.getToUserId() == userId || transfer.getFromUserId() == userId) {
                userIdExists = true;
                break;
            }
        }
        if (!userIdExists) {
            throw new UserNotFoundException();
        }
        return transferList;
    }

    @Override
    public Transfer findTransferByTransferId(Integer transferId) throws TransferNotFoundException {
        Transfer transferById = new Transfer();
        String sql = "SELECT transfer_id, to_user_id, amount, from_user_id, " +
                "approval_status FROM transfer WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
        boolean transferExists = false;
        if (results.next()) {
            transferById = mapRowToTransfer(results);
            transferExists = true;
        } else {
            throw new TransferNotFoundException();
        }
        return transferById;
    }

    @Override
    public int findFromUserIdByTransferId(int transferId) {
        String sql = "SELECT from_user_id FROM transfer WHERE transfer_id = ?;";
        Integer id= 0;
        try {
            id = jdbcTemplate.queryForObject(sql, Integer.class, transferId);
            if (id != null) {
                return id;
            } else {
                return -1;
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            System.out.println("Please put in a valid transfer ID");
        }
        return id;
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

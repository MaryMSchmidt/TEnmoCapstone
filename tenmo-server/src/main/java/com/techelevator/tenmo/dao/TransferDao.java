package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    int createTransfer(int toUserId, BigDecimal amount, int fromUserId);

    List<Transfer> findYourTransfers(int userId);

    Transfer findTransferByTransferId(Integer transferId);
}

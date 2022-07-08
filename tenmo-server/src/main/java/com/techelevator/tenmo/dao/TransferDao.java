package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.TransferInvalidCreationException;
import com.techelevator.tenmo.exception.TransferNotFoundException;
import com.techelevator.tenmo.exception.UserNotFoundException;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    int createTransfer(int toUserId, BigDecimal amount, int fromUserId);

    List<Transfer> findYourTransfers(int userId) throws UserNotFoundException;

    Transfer findTransferByTransferId(Integer transferId) throws TransferNotFoundException;
}

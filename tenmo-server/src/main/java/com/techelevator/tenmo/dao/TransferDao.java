package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.TransferInvalidCreationException;
import com.techelevator.tenmo.exception.TransferNotFoundException;
import com.techelevator.tenmo.exception.UserNotFoundException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    int createTransfer(int toUserId, BigDecimal amount, int fromUserId) throws UserNotFoundException;

    List<Transfer> findYourTransfers(int userId) throws UserNotFoundException;

    Transfer findTransferByTransferId(Integer transferId) throws TransferNotFoundException;

    int findFromUserIdByTransferId(int fromUserId);
}

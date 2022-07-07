package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int toUserId;
    private BigDecimal amount;
    private int fromUserId;
    private boolean approvalStatus;

    public Transfer() {

    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Transfer(int transferId, int toUserId, BigDecimal amount, int fromUserId, boolean approvalStatus) {
        this.transferId = transferId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.fromUserId = fromUserId;
        this.approvalStatus = approvalStatus;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }



    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public boolean isApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(boolean approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}

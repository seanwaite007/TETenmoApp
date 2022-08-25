package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private int accountId;
    private BigDecimal transferAmount;
    private int account_To;
    private int account_from;
    private String transferStatus = "Approved";
    private String accountToUser;

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getAccountToUser() {
        return accountToUser;
    }

    public void setAccountToUser(String accountToUser) {
        this.accountToUser = accountToUser;
    }

    public Transfer() {

    }

    public Transfer(int transferId, int accountId,
                    BigDecimal transferAmount,
                    int account_To, int account_from, String transferStatus
    ) {
        this.transferId = transferId;
        this.accountId = accountId;
        this.transferAmount = transferAmount;
        this.account_To = account_To;
        this.account_from = account_from;
        this.transferStatus = transferStatus;
    }

    public int getAccount_To() {
        return account_To;
    }

    public void setAccount_To(int account_To) {
        this.account_To = account_To;
    }

    public int getAccount_from() {
        return account_from;
    }

    public void setAccount_from(int account_from) {
        this.account_from = account_from;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }
}

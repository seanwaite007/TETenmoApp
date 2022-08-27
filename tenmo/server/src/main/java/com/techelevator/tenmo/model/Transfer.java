package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private int transferId;
    private BigDecimal transferAmount;
    private int account_To;
    private int account_from;
    private String transferStatus = "Approved";
    private String accountToUsername;
    private  String accountFromUsername;

    public Transfer() {
    }

    public Transfer(int transferId, BigDecimal transferAmount,
                    int account_To, int account_from, String transferStatus, String accountToUsername, String accountFromUsername
    ) {
        this.transferId = transferId;
        this.transferAmount = transferAmount;
        this.account_To = account_To;
        this.account_from = account_from;
        this.transferStatus = transferStatus;
        this.accountToUsername = accountToUsername;
        this.accountFromUsername = accountFromUsername;

    }

    public String getAccountToUsername() {
        return accountToUsername;
    }

    public void setAccountToUsername(String accountToUserName) {
        this.accountToUsername = accountToUserName;
    }

    public String getAccountFromUsername() {
        return accountFromUsername;
    }

    public void setAccountFromUsername(String accountFromUsername) {
        this.accountFromUsername = accountFromUsername;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
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

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public boolean equals(Object obj) {
            return this.transferId == ((Transfer)obj).transferId;

    }
}

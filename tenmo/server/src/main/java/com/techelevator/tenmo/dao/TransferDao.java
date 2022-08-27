package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer createTransfer(Transfer transfer);

    List<Transfer> getTransfersByAccount (String userName);

    Transfer getTransferById(int transferId);
}

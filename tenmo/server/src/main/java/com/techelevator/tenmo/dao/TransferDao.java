package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer sendMoney(Transfer transferAmount);

    List<Transfer> getAllTransfers();

    Transfer getTransferById (int transferId);

}

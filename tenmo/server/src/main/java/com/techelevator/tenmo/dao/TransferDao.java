package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {
    //TODO: CRUD operations for transfer


    Transfer createTransfer(Transfer transfer);

    List<Transfer> getAllTransfers();

    List<Transfer> getTransfersByAccount (String userName);

    Transfer getTransferById(int transferId);

}
